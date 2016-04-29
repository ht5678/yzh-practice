package zookeeper.apache.simple;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;

/**
 * 
 * @author yuezh2   2016年1月15日 下午2:16:08
 *
 */
public class DataMonitor implements Watcher,StatCallback{
	
	private ZooKeeper zk;
	
	private String znode;
	
	private Watcher chainedWatcher;
	
	public boolean dead;
	
	private DataMonitorListener listener;
	
	private byte[] prevData;
	
	
	
	
	/**
	 * 构造函数
	 * @param zk
	 * @param znode
	 * @param chainedWatcher
	 * @param listener
	 */
	public DataMonitor(ZooKeeper zk , String znode , Watcher chainedWatcher,DataMonitorListener listener) {
		this.zk = zk; 
		this.znode = znode;
		this.chainedWatcher = chainedWatcher;
		this.listener = listener;
		//如果znode启动就启动zk。完全的事件驱动
		zk.exists(znode, true, this, null);
	}
	
	
	
	/**
	 * 
	 * @author yuezh2   2016年1月15日 上午11:58:41
	 *
	 */
	public interface DataMonitorListener {
	    /**
	    * The existence status of the node has changed.
	    */
	    void exists(byte data[]);

	    /**
	    * The ZooKeeper session is no longer valid.
	    * 
	    * @param rc
	    * the ZooKeeper reason code
	    */
	    void closing(int rc);
	}
	
	
	

	@Override
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		boolean exists ;
		switch (rc) {
		case Code.Ok:
			exists = true;
			break;
		case Code.NoNode:
			exists = false;
			break;
		case Code.SessionExpired:
		case Code.NoAuth:
			dead = true;
			listener.closing(rc);
			return;
		default:
			//错误重试
			zk.exists(znode, true, this, null);
			return;
		}
		
		byte[] b = null;
		
		if(exists){
			try {
				b = zk.getData(znode, false, null);
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		if((b == null && b!=prevData) || (b!=null && !Arrays.equals(prevData, b))){
			listener.exists(b);
			prevData = b;
		}
		
	}

	@Override
	public void process(WatchedEvent event) {
		String path = event.getPath();
		if(event.getType() == Event.EventType.None){
			//连接状态改变
			switch (event.getState()) {
			case SyncConnected:
				//在这里特别的例子里边我们不需要在这里做逻辑处理
				//watcher会自动重注册 并且 任何watcher都会在client失去连接的时候被触发
				break;
			case Expired:
				//结束
				dead = true;
				listener.closing(KeeperException.Code.SessionExpired);
				break;
			}
		}else{
			if(path!=null && path.equals(znode)){
				//找出有改变的节点node
				zk.exists(znode, true, this, null);
			}
		}
		if(chainedWatcher != null){
			chainedWatcher.process(event);
		}
		
	}

}
