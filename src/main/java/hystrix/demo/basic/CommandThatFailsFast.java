package hystrix.demo.basic;

import static org.junit.Assert.*;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixRuntimeException;

/**
 * Sample {@link HystrixCommand} that does not have a fallback implemented
 * so will "fail fast" when failures, rejections, short-circuiting etc occur.
 * 
 * 
 * @author yuezh2   2017年8月30日 上午10:50:59
 *
 */
public class CommandThatFailsFast extends HystrixCommand<String>{
	
	
	private final boolean throwException;
	
	
	public CommandThatFailsFast(boolean throwException){
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.throwException = throwException;
	}
	
	

	@Override
	protected String run() throws Exception {
		if(throwException){
			throw new RuntimeException("failure from commandthatfailsFast");
		}else{
			return "success";
		}
	}
	

	
	public static class UnitTest{
		
		@Test
		public void testSuccess(){
			assertEquals("success" , new CommandThatFailsFast(false).execute());
		}
		
		
		@Test
		public void testFailure(){
			try{
				new CommandThatFailsFast(true).execute();
				fail("we should have throw an exception");
			}catch(HystrixRuntimeException e){
				assertEquals("failure from commandthatfailsFast" , e.getCause().getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	
	

}
