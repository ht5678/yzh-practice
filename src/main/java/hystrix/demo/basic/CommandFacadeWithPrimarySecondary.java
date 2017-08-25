package hystrix.demo.basic;

import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * Sample {@link HystrixCommand} pattern using a semaphore-isolated command
 * that conditionally invokes thread-isolated commands.
 * 
 * 
 * @author yuezh2   2017年8月25日 下午4:20:35
 *
 */
public class CommandFacadeWithPrimarySecondary extends HystrixCommand<String>{

	private final static DynamicBooleanProperty usePrimary = DynamicPropertyFactory.getInstance().getBooleanProperty("primarySecondary.usePrimary", true);
	
	private final Integer id;
	
	public CommandFacadeWithPrimarySecondary(int id) {
		super(Setter
					.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
					.andCommandKey(HystrixCommandKey.Factory.asKey("PrimarySecondaryCommand"))
					.andCommandPropertiesDefaults(
							// we want to default to semaphore-isolation since this wraps
	                        // 2 others commands that are already thread isolated
							HystrixCommandProperties.Setter()
									.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
							));
		
		this.id = id;
	}

	@Override
	protected String run() throws Exception {
		if(usePrimary.get()){
			return new prima
		}
		return null;
	}

	
	
	
	
	private static class PrimaryCommand extends HystrixCommand<String>{

		private final int id;
		
		
		/**
		 * @param id
		 */
		private PrimaryCommand(int id){
			super(Setter
						.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("PrimaryCommand"))
						.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("PrimaryCommand"))
						.andCommandPropertiesDefaults(
								//we default to a 600ms timeout for primary
								HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(600)
								));
			this.id = id;
		}
		
		
		
		@Override
		protected String run() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		
		
	}
	
	
	
}
