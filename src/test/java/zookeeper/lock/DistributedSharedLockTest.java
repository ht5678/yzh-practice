package zookeeper.lock;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;

/**
 * 
 * @author yuezh2   2016年1月4日 下午8:07:58
 *
 */
public class DistributedSharedLockTest {

	
	
	@Test
	public void testLock(){
		for(int i = 0 ; i < 10 ; i++){
			Thread t = new Thread(new Runnable() {
				public void run() {
					DistributedSharedLock lock = new DistributedSharedLock("/_locknode_");
					
					try {
						
						lock.acquire();
						Thread.sleep(1000);
						System.out.println("========获得锁后进行相应的操作=======");
						lock.release();
						System.out.println("===================================");
						
					} catch (KeeperException | InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			});
			
			t.start();
		}
		
		try {
			Thread.sleep(1000 * 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
