package zookeeper.apache.lesson;



import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 查看效果步骤:
 * 
 * 1.首先执行一次(打印子节点并且注册监听器)
 * 
 * 2.在父目录下创建一个子节点(这个时候就会发现自动打印出来最新的子节点列表)
 * 
 * @author sdwhy
 *
 */
public class GetChildrenSync implements Watcher{
	
	
    private static ZooKeeper zooKeeper;
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
				
		zooKeeper = new ZooKeeper("192.168.1.105:2181",5000,new GetChildrenSync());
		System.out.println(zooKeeper.getState().toString());
			
		Thread.sleep(Integer.MAX_VALUE);
		

	}
	
	private void doSomething(ZooKeeper zooKeeper){
		
		try {
			//第一个参数是查看哪个目录下的子节点
			//第二个参数是是否关注子节点的变化(比如增加子节点,删除子节点)
			List<String> children =  zooKeeper.getChildren("/", true);
			System.out.println(children);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		
		if (event.getState()==KeeperState.SyncConnected){
			//确保doSomething()只会执行一次
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething(zooKeeper);
			}else{//当子节点发生变化的时候,触发,(打印变更后的子节点内容+重新注册一个watcher,zk中所有watcher只可以使用一次)
				if (event.getType()==EventType.NodeChildrenChanged){
					try {
						System.out.println(zooKeeper.getChildren(event.getPath(), true));
					} catch (KeeperException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}		
		}
	}

}
