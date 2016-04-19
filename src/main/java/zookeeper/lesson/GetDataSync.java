package zookeeper.lesson;



import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;



/**
 * 
 * @author sdwhy
 *
 */
public class GetDataSync implements Watcher{
	
	
    private static ZooKeeper zooKeeper;
    private static Stat stat = new Stat();
    
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		
		zooKeeper = new ZooKeeper("192.168.10.160:2181",5000,new GetDataSync());
		System.out.println(zooKeeper.getState().toString());
	
		Thread.sleep(Integer.MAX_VALUE);
		

	}
	
	private void doSomething(ZooKeeper zookeeper){
				
		zookeeper.addAuthInfo("digest", "jike:123456".getBytes());
			try {
				//zooKeeper.getData("/node_4", true, stat))
				//第一个参数:要获取内容的节点绝对路径
				//第二个参数:是否注册一个监听器:如果注册的话,就会在process()方法中多一个事件:EventType.NodeDataChanged
				//第三个参数:将会获取这哥节点的所有状态信息
				System.out.println(new String(zooKeeper.getData("/node_4", true, stat)));
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub

		if (event.getState()==KeeperState.SyncConnected){
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething(zooKeeper);
			}else{				
				if (event.getType()==EventType.NodeDataChanged){
					try {
						System.out.println(new String(zooKeeper.getData(event.getPath(), true, stat)));
						System.out.println("stat:"+stat);
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
