package rpc.demo.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author sdwhy
 *
 */
public class DemoClientTest extends TestCase{

	
	public DemoClientTest(String testName){
		super(testName);
	}
	
	
	public static Test suite(){
		return new TestSuite(DemoClientTest.class);
	}
	
	
	@org.junit.Test
	public void testApp(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:rpc/demo/applicationContext-client.xml");
		DemoService demoService = (DemoService)context.getBean("demoService");
		System.out.println(demoService.sayHi("张三"));
	}
	
}
