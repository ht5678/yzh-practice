package zookeeper.curator.prac;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

public class CreateSession {

	public static void main(String[] args) throws InterruptedException {
		//以下是几种系统内置的重试策略
		
		//第一个参数是重试基本时间，第二个参数是重试次数
		//每次重试的间隔时间会比 基本时间  要长，总体不会超过3次
		//RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		//每次隔间固定1s，总体重试不超过5次
		//RetryPolicy retryPolicy = new RetryNTimes(5, 1000);
		//第一个参数是  ： 总的重试时间不能超过5s
		//第二个参数是:   两次重试的时间间隔为1s
		RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
		//
//		CuratorFramework client = CuratorFrameworkFactory
//				.newClient("192.168.1.105:2181",5000,5000, retryPolicy);
		
		//fluent风格，工厂模式
		//retry policy:重试策略
		//客户端运行过程中可能会出现中断，有了重试策略保证平稳运行
		CuratorFramework client = CuratorFrameworkFactory
				.builder()
				.connectString("192.168.1.105:2181")
				.sessionTimeoutMs(5000)
				.connectionTimeoutMs(5000)
				.retryPolicy(retryPolicy)
				.build();
		
		client.start();
		
		Thread.sleep(Integer.MAX_VALUE);
		
		
	}
	
}
