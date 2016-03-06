package zookeeper.lesson;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @author sdwhy
 *
 */
public class CreateSession implements Watcher{
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ZooKeeper zk = new ZooKeeper("192.168.10.160:2181", 1000*10, new CreateSession());
		System.out.println(zk.getState());
		
		
		Thread.sleep(1000000);
	}
	
	
	private void doSomething(){
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
