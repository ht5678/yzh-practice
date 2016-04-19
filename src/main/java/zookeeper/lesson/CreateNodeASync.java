package zookeeper.lesson;

import java.io.IOException;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
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
public class CreateNodeASync implements Watcher{
	
	private static ZooKeeper zk ;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		zk = new ZooKeeper("192.168.10.160:2181", 1000*10, new CreateNodeASync());
		System.out.println(zk.getState());
		
		
		Thread.sleep(10000);
	}
	
	
	private void doSomething(){
		//Ids.OPEN_ACL_UNSAFE:开放所有权限
		//CreateMode.PERSISTENT:持久节点
		//PERSISTENT_SEQUENTIAL:持久顺序节点
		//EPHEMERAL:临时节点
		//EPHEMERAL_SEQUENTIAL:临时顺序节点
		zk.create("/node_5", "234".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT,new IStringCallback(),"创建");
		
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


class IStringCallback implements AsyncCallback.StringCallback{

	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		StringBuilder sb = new StringBuilder();
		//返回码:0表示成功
		sb.append("rc="+rc).append("\n");
		//要创建节点的路径
		sb.append("path="+path).append("\n");
		//上下文,就是上面传过来的 "创建"
		sb.append("ctx="+ctx).append("\n");
		//已经创建节点的真实路径,*****如果创建的是一个顺序节点,那么path!=name
		sb.append("name="+name).append("\n");
		
		System.out.println(sb.toString());
		
	}
	
}
