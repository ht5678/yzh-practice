package zookeeper.curator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.data.Stat;

public class checkexists {

	public static void main(String[] args) throws Exception {
		//*异步状态下使用线程池
		ExecutorService es = Executors.newFixedThreadPool(5);
		
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
		
		
		//同步方式检查节点是否存在
		Stat s = client.checkExists().forPath("/jike");
		
		
		
		//异步方式检查节点是否存在
		client.checkExists().inBackground(new BackgroundCallback() {
			
			/**
			 * arg0:curator对象
			 * arg1:事件类型（CREATE,UPDATE,.....）
			 */
			public void processResult(CuratorFramework arg0, CuratorEvent arg1)
					throws Exception {
				// TODO Auto-generated method stub
				
				Stat stat = arg1.getStat();
				//状态
				System.out.println(stat);
				//返回码，0代表成功
				int code = arg1.getResultCode();
				//事件类型
				arg1.getType();
				//这个输出等于下面的"123"
				System.out.println(arg1.getContext());
				
				
				arg1.getPath();
				arg1.getChildren();
				arg1.getData();
				// .......更多
			}
		},"123",es).forPath("/jike");		//*es代表线程池
		
		Thread.sleep(Integer.MAX_VALUE);
		
		
		
	}
	
}
