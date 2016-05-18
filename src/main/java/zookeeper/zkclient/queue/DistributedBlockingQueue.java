package zookeeper.zkclient.queue;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * 
 * @author sdwhy
 *
 */
public class DistributedBlockingQueue<T> extends DistributedSimpleQueue<T> {

	
	
	/**
	 * 构造函数初始化
	 * @param zkClient
	 * @param root
	 */
	public DistributedBlockingQueue(ZkClient zkClient, String root) {
		super(zkClient, root);
	}

	
	
	
	/**
	 * 消费消息
	 * 
	 * 
	 * 模拟的是一种消息队列为空,消费者等待消费的情况
	 * 
	 * 消费者会用CountDownLatch进行阻塞,并且注册一个子节点列表监听器,当子节点列表变化的时候,取消阻塞,
	 * 继续进行消费,防止浪费性能
	 * 
	 */
	@Override
	public T poll() throws Exception {
		while(true){
			final CountDownLatch latch = new CountDownLatch(1);
			final IZkChildListener childListener = new IZkChildListener() {
				
				@Override
				public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
					latch.countDown();
				}
			};
			
			zkClient.subscribeChildChanges(root, childListener);
			
			try{
				T node = super.poll();
				if(node != null){
					return node;
				}else{
					latch.await();
				}
			}finally{
				zkClient.unsubscribeChildChanges(root, childListener);
			}
		}
	}

	
	
}
