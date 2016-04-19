package zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

public class CreateNode {

	public static void main(String[] args) throws Exception {
		
		//RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		//RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
//		CuratorFramework client = CuratorFrameworkFactory
//				.newClient("192.168.1.105:2181",5000,5000, retryPolicy);
		
		CuratorFramework client = CuratorFrameworkFactory
				.builder()
				.connectString("192.168.1.105:2181")
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(5000)
				.retryPolicy(retryPolicy)
				.build();
		
		client.start();
		
		
		String path = client.create()
				.creatingParentsIfNeeded()					//如果父节点不存在，会先创建父节点
				.withMode(CreateMode.EPHEMERAL)		//临时节点或者持久节点
//				.withACL(arg0)										//acl集合
				.forPath("/jike/1","123".getBytes());		//节点的绝对路径和内容
		
		System.out.println(path);
		
		Thread.sleep(Integer.MAX_VALUE);
		
		
	}
	
}
