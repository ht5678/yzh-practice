package zookeeper.zkclient.queue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * 
 * @author sdwhy
 *
 */
public class TestDistributedBlockingQueue {
	
	
	
	public static void main(String[] args) {
		ScheduledExecutorService delayExcutor = Executors.newScheduledThreadPool(1);
		int delayTime = 5;
		
		ZkClient zkClient = new ZkClient("10.99.205.22:12181", 5000, 5000,new SerializableSerializer());
		final DistributedBlockingQueue<User> queue = new DistributedBlockingQueue<>(zkClient, "/Queue");
		
		final User user1 = new User();
		user1.setId("1");
		user1.setName("xiao wang");
		
		final User user2 = new User();
		user2.setId("2");
		user2.setName("xiao wang");
		
		
		try{
		
			delayExcutor.schedule(new Runnable() {
				
				@Override
				public void run() {
					try{
						queue.offer(user1);
						queue.offer(user2);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}, delayTime, TimeUnit.SECONDS);
			
			
			System.out.println("ready poll!");
	
			User u1 = (User)queue.poll();
			User u2 = (User)queue.poll();
			
			System.out.println(u1);
			System.out.println(u2);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			delayExcutor.shutdown();
			try{
				delayExcutor.awaitTermination(2, TimeUnit.SECONDS);
			}catch(InterruptedException e){
				
			}
		}
		
	}

}
