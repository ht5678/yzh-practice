package rpc.demo.main;

import java.util.concurrent.CountDownLatch;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author sdwhy
 *
 */
public class DemoServerTest {

	
	public static void main(String[] args) throws InterruptedException {
		new ClassPathXmlApplicationContext("classpath*:rpc/demo/applicationContext-server.xml");
		CountDownLatch latch = new CountDownLatch(1);
		latch.await();
	}
	
	
}
