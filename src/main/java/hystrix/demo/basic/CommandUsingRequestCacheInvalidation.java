package hystrix.demo.basic;

import static org.junit.Assert.*;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * 
 * Example {@link HystrixCommand} implementation for handling the get-set-get use case within
 * a single request context so that the "set" can invalidate the cached "get".
 * 
 * @author yuezh2   2017年8月30日 下午3:12:25
 *
 */
public class CommandUsingRequestCacheInvalidation {

	/** represents a remote data store */
	private static volatile String prefixStoreOnRemoteDataStore = "ValueBeforeSet_";
	
	
	
	public static class GetterCommand extends HystrixCommand<String>{
		
		
		private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("GetterCommand");
		private final int id;
		

		protected GetterCommand(int id) {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet"))
					.andCommandKey(GETTER_KEY));
			this.id = id;
		}

		@Override
		protected String run() throws Exception {
			return prefixStoreOnRemoteDataStore+id;
		}

		@Override
		protected String getCacheKey() {
			return String.valueOf(id);
		}
		
		
		/**
         * Allow the cache to be flushed for this object.
         * 
         * @param id
         *            argument that would normally be passed to the command
         */
		public static void flushCache(int id){
			HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance())
						.clear(String.valueOf(id));
		}
		
		
	}
	
	
	
	
	public static class SetterCommand extends HystrixCommand<Void>{
		
		private final int id;
		
		private final String prefix;
		
		
		
		public SetterCommand(int id , String prefix){
			super(HystrixCommandGroupKey.Factory.asKey("GetSetGet"));
			this.id = id;
			this.prefix = prefix;
		}



		@Override
		protected Void run() throws Exception {
			
			//persist the value against the datastore
			prefixStoreOnRemoteDataStore = prefix;
			
			//flush the cache
			GetterCommand.flushCache(id);
			
			//no return value
			return null;
		}
		
		
		
		
		
	}
	
	
	
	
	public static class UnitTest{
		
		@Test
		public void getGetSetGet(){
			HystrixRequestContext context = HystrixRequestContext.initializeContext();
			
			try{
				assertEquals("ValueBeforeSet_1" , new GetterCommand(1).execute());
				GetterCommand commonAgainstCache = new GetterCommand(1);
				//
				assertEquals("ValueBeforeSet_1" , commonAgainstCache.execute());
				//confirm it executed against cache the second time
				assertTrue(commonAgainstCache.isResponseFromCache());
				
				//set the new value
				new SetterCommand(1, "ValueAfterSet_").execute();
				//fetch it again
				GetterCommand commondAfterSet = new GetterCommand(1);
				// the getter should return with the new prefix, not the value from cache
                assertFalse(commondAfterSet.isResponseFromCache());
                assertEquals("ValueAfterSet_1", commondAfterSet.execute());
			}finally{
				context.shutdown();
			}
		}
		
	}
	
	
	
	
}
