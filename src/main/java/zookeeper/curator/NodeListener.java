package zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryUntilElapsed;



/**
 * 节点监听（节点创建，数据内容被修改等）不需要每次自己添加事件（zk中事件之恩呢个用一次）
 * @author sdwhy
 *
 */
public class NodeListener {

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
		
		final NodeCache cache = new NodeCache(client,"/jike");
		cache.start();
		cache.getListenable().addListener(new NodeCacheListener() {
			
			public void nodeChanged() throws Exception {
				// TODO Auto-generated method stub
				byte[] ret = cache.getCurrentData().getData();
				System.out.println("new data:"+new String(ret));
			}
		});
		
		Thread.sleep(Integer.MAX_VALUE);
		
	}
	
}
