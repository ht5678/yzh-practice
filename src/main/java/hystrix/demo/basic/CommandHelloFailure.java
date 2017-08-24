package hystrix.demo.basic;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import static org.junit.Assert.assertEquals;

/**
 * 
 * @author yuezh2   2017年8月24日 下午3:39:33
 *
 */
public class CommandHelloFailure extends HystrixCommand<String>{

	private final String name;
	
	
	public CommandHelloFailure(String name){
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}
	
	
	@Override
	protected String run() throws Exception {
		throw new Exception("this command always fails");
	}


	@Override
	protected String getFallback() {
		return "Hello Failure " + name + "!";
	}

	
	
	
	public static class UnitTest{
		
		@Test
		public void testSynchronous(){
			assertEquals("Hello Failure World!", new CommandHelloFailure("World").execute());
			assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").execute());
		}
		
		
		@Test
		public void testAsynchronous1()throws Exception{
			assertEquals("Hello Failure World!", new CommandHelloFailure("World").queue().get());
			assertEquals("Hello Failure Bob!", new CommandHelloFailure("Bob").queue().get());
		}
		
		
		
		
	}
	
	
}
