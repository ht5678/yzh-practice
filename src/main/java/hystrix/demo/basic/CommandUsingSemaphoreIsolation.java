package hystrix.demo.basic;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * 
 * @author yuezh2   2017年8月31日 上午11:32:31
 *
 */
public class CommandUsingSemaphoreIsolation extends HystrixCommand<String>{

	
	private final int id;
	
	
	public CommandUsingSemaphoreIsolation(int id){
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
				// since we're doing work in the run() method that doesn't involve network traffic
                // and executes very fast with low risk we choose SEMAPHORE isolation
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE))
				);
		this.id = id;
	}
	
	
	
	@Override
	protected String run() throws Exception {
		// a real implementation would retrieve data from in memory data structure
        // or some other similar non-network involved work
        return "ValueFromHashMap_" + id;
	}

	
	
	
}
