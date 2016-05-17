package zookeeper.zkclient.queue;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * 
 * @author yuezh2   2016年5月17日 下午8:23:17
 *
 */
public class TestDistributedSimpleQueue {
	
	
	public static void main(String[] args) {
		
		ZkClient zkClient = new ZkClient("10.99.205.17:2281", 5000, 5000 , new SerializableSerializer());
		DistributedSimpleQueue<User> queue = new DistributedSimpleQueue<>(zkClient, "/Queue");
		
		User user1 = new User();
		user1.setId("1");
		user1.setName("xiao wang");
		
		User user2 = new User();
		user2.setId("2");
		user2.setName("xiao wang2");
		
		try{
			queue.offer(user1);
			queue.offer(user2);
			
			User u1 = (User)queue.poll();
			User u2 = (User)queue.poll();
			
			System.out.println(u1);
			System.out.println(u2);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
