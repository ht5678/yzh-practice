package hystrix.demo.basic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * 
 * The obligatory "Hello World!" showing a simple implementation of a {@link HystrixCommand}.
 * 
 * @author yuezh2   2017年8月24日 上午10:37:10
 *
 */
public class CommandHelloWorld extends HystrixCommand<String>{
	
	private final String name;
	
	
	public CommandHelloWorld(String name){
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name=name;
	}
	
	
	

	@Override
	protected String run() throws Exception {
		return "Hello "+name + "!";
	}

	
	
	
	public static class UnitTest{
		
		@Test
		public void testSynchronous(){
			assertEquals("Hello World!", new CommandHelloWorld("World").execute());
			assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
		}
		
		
		@Test
		public void testAsynchronous1()throws Exception{
			assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
			assertEquals("Hello Bob!", new CommandHelloWorld("Bob").queue().get());
		}
		
		
		@Test
		public void testObservable(){
			Observable<String> fWorld = new CommandHelloWorld("World").observe();
			Observable<String> fBob = new CommandHelloWorld("Bob").observe();
			
			//blocking
			assertEquals("Hello World!", fWorld.toBlocking().single());
			assertEquals("Hello Bob!", fBob.toBlocking().single());
			
			
			// non-blocking 
            // - this is a verbose anonymous inner-class approach and doesn't do assertions
			fWorld.subscribe(new Observer<String>() {

				@Override
				public void onCompleted() {
					
				}

				@Override
				public void onError(Throwable arg0) {
					arg0.printStackTrace();
				}

				@Override
				public void onNext(String v) {
					System.out.println("on Next : "+v);
				}
				
			});
			
			
			// non-blocking
            // - also verbose anonymous inner-class
            // - ignore errors and onCompleted signal
			fBob.subscribe(new Action1<String>() {

				@Override
				public void call(String v) {
					System.out.println("onNext: " + v);
				}
				
			});
			
			
		}
		
		
	}
	
	
	
}
