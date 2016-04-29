package zookeeper.apache.lesson;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @author sdwhy
 *
 */
public class CreateNodeSync implements Watcher{
	
	private static ZooKeeper zk ;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		zk = new ZooKeeper("192.168.10.160:2181", 1000*10, new CreateNodeSync());
		System.out.println(zk.getState());
		
		
		Thread.sleep(10000);
	}
	
	
	private void doSomething(){
		//Ids.OPEN_ACL_UNSAFE:开放所有权限
		//CreateMode.PERSISTENT:持久节点
		//PERSISTENT_SEQUENTIAL:持久顺序节点
		//EPHEMERAL:临时节点
		//EPHEMERAL_SEQUENTIAL:临时顺序节点
		String path;
		try {
			path = zk.create("/node_4", "234".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println(path);
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("do something");
	}
	

	@Override
	public void process(WatchedEvent event) {
		System.out.println("收到事件:"+event);
		
		if(event.getState() == KeeperState.SyncConnected){
			//do something
			doSomething();
		}
	}

}
