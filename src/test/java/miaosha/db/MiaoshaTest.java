package miaosha.db;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import miaosha.db.MiaoShaService;

/**
 * 
 * @author yuezh2   2017年5月19日 下午3:15:30
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:springmvc/springmvc.xml","classpath:springmvc/template.xml"})
public class MiaoshaTest {

	@Autowired
	private MiaoShaService service;
	
	
	@Before
	public void init()throws Exception{
		
	}
	
	
	@Test
	public void test1()throws Exception{
		for(int i =0 ; i < 100 ; i++){
			Thread t = new Thread(new ExecuteThread(3L));
			t.start();
			latch.countDown();
		}
		
		Thread.currentThread().sleep(3000);
		System.out.println("total : "+total);
	}
	
	
	
	
	private CountDownLatch latch = new CountDownLatch(100);
	//总共卖出去多少
	private Long total = 0L;
	
	private class ExecuteThread implements Runnable{
		
		private Long amount;
		
		public ExecuteThread(Long amount){
			this.amount = amount;
		}
		
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			boolean result = service.modifyAmount("demo", new BigDecimal(amount));
			if(result){
				synchronized (total) {
					total += amount;
				}
			}
		}
	}
	
}
