package zookeeper.prac;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * @author yuezh2   2016年1月15日 下午6:23:10
 *
 */
public class ZnodeWatcher implements Watcher{

	
	
	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		String znode = "/test";
		
		ZooKeeper zk = new ZooKeeper("10.99.205.17:2281", 1000*10, new ZnodeWatcher());
		
		if(zk!=null){
			//获取状态
			Stat stat = zk.exists(znode, false);
			//设置znode的数据
			if(stat != null){
				System.out.println("znode修改之前的版本号"+stat.getVersion());
				Stat result = zk.setData(znode, new String("我爱中国").getBytes(), stat.getVersion());
				System.out.println("znode修改之后的版本号"+result.getVersion());
			}
		}
	}
	
	
	
	
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println(event.getType());
	}

}
