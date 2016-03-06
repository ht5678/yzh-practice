package zookeeper.lesson;



import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.AsyncCallback;
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
public class GetChildrenASync implements Watcher{
	
    private static ZooKeeper zooKeeper;
    
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		
		
		zooKeeper = new ZooKeeper("192.168.1.105:2181",5000,new GetChildrenASync());
		System.out.println(zooKeeper.getState().toString());
				
		Thread.sleep(Integer.MAX_VALUE);
		

	}
	
	private void doSomething(ZooKeeper zookeeper){

		
		try {
			//第一个参数是查看哪个目录下的子节点
			//第二个参数是是否关注子节点的变化(比如增加子节点,删除子节点)
			zooKeeper.getChildren("/", true, new IChildren2Callback(), null);		
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		
	}

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		//确保doSomething()只会执行一次
		if (event.getState()==KeeperState.SyncConnected){
			if (event.getType()==EventType.None && null==event.getPath()){
				doSomething(zooKeeper);
			}else{//当子节点发生变化的时候,触发,(打印变更后的子节点内容+重新注册一个watcher,zk中所有watcher只可以使用一次)			
				if (event.getType()==EventType.NodeChildrenChanged){
					zooKeeper.getChildren(event.getPath(), true, new IChildren2Callback(), null);
				}			
			}
		}
	}
	
	static class IChildren2Callback implements AsyncCallback.Children2Callback{

		@Override
		public void processResult(int rc, String path, Object ctx,
				List<String> children, Stat stat) {
			
			StringBuilder sb = new StringBuilder();
			//操作返回码:0表示成功
			sb.append("rc="+rc).append("\n");
			//参数传过来的获取子节点的父路径
			sb.append("path="+path).append("\n");
			//上下文,zooKeeper.getChildren("/", true, new IChildren2Callback(), null);中的null
			sb.append("ctx="+ctx).append("\n");
			//父目录下的子节点列表
			sb.append("children="+children).append("\n");
			//该节点的状态信息
			sb.append("stat="+stat).append("\n");
			System.out.println(sb.toString());
			
		}
		
		
	}

}
