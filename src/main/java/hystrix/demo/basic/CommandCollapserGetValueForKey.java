package hystrix.demo.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import org.junit.Test;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * 
 * Sample {@link HystrixCollapser} that automatically batches multiple requests to execute()/queue()
 * into a single {@link HystrixCommand} execution for all requests within the defined batch (time or size).
 * 
 * @author yuezh2   2017年8月24日 下午5:20:06
 *
 */
public class CommandCollapserGetValueForKey extends HystrixCollapser<List<String>, String, Integer>{
	
	private final Integer key;
	
	
	public CommandCollapserGetValueForKey(Integer key) {
		this.key = key;
	}
	
	
	
	

	@Override
	protected HystrixCommand<List<String>> createCommand(
			Collection<com.netflix.hystrix.HystrixCollapser.CollapsedRequest<String, Integer>> requests) {
		return new BatchCommand(requests);
	}

	@Override
	public Integer getRequestArgument() {
		return key;
	}

	@Override
	protected void mapResponseToRequests(List<String> batchResponse,
			Collection<com.netflix.hystrix.HystrixCollapser.CollapsedRequest<String, Integer>> requests) {
		int count = 0;
		for(CollapsedRequest<String, Integer> request : requests){
			request.setResponse(batchResponse.get(count++));
		}
	}

	
	
	/**
	 * 
	 * @author yuezh2   2017年8月24日 下午5:45:27
	 *
	 */
	private static final class BatchCommand extends HystrixCommand<List<String>>{
		
		private final Collection<CollapsedRequest<String, Integer>> requests;
		
		
		private BatchCommand(Collection<CollapsedRequest<String, Integer>> requests) {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
					.andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
			this.requests = requests;
		}
		
		

		@Override
		protected List<String> run() throws Exception {
			List<String> response = new ArrayList<>();
			for(CollapsedRequest<String, Integer> request : requests){
				// artificial response for each argument received in the batch
				response.add("ValueForKey: "+request.getArgument());
			}
			return response;
		}
		
	}
	
	
	
	
	
	
	/**
	 * 测试类
	 * @author yuezh2   2017年8月24日 下午5:45:54
	 *
	 */
	public static class UnitTest{
		
		
		@Test
		public void testCollapser()throws Exception{
			HystrixRequestContext context = HystrixRequestContext.initializeContext();
			
			try{
				Future<String> f1 = new CommandCollapserGetValueForKey(1).queue();
				Future<String> f2 = new CommandCollapserGetValueForKey(2).queue();
				Future<String> f3 = new CommandCollapserGetValueForKey(3).queue();
				Future<String> f4 = new CommandCollapserGetValueForKey(4).queue();
				
				assertEquals("ValueForKey: 1", f1.get());
				assertEquals("ValueForKey: 2", f2.get());
				assertEquals("ValueForKey: 3", f3.get());
				assertEquals("ValueForKey: 4", f4.get());

				
				int numExecuted = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size();
				System.err.println("num executed : "+numExecuted);
				
				// assert that the batch command 'GetValueForKey' was in fact executed and that it executed only 
                // once or twice (due to non-determinism of scheduler since this example uses the real timer)
				if(numExecuted>2){
					fail("some of the commands should have been collapsed");
				}
				
				System.err.println("HystrixRequestLog.getCurrentRequest().getAllExecutedCommands : "+HystrixRequestLog.getCurrentRequest().getAllExecutedCommands());
				
				int numLogs = 0;
				for(HystrixInvokableInfo<?> command : HystrixRequestLog.getCurrentRequest().getAllExecutedCommands()){
					numLogs ++;
					
					//assert the command is the one we're expecting
					assertEquals("GetValueForKey", command.getCommandKey().name());
					
					//confirm that it was a command execution
					assertTrue(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
					assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				context.shutdown();
			}
		}
		
	}
	
	
}
