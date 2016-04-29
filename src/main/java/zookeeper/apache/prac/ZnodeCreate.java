package zookeeper.apache.prac;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * 出现错误是因为没有watcher，并不影响结果
 * 
 * @author yuezh2   2016年1月15日 下午5:38:12
 *
 */
public class ZnodeCreate {
	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper("10.99.205.17:2281", 1000*10, null);
		
		if(zk != null){
			String result = zk.create("/test", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println(result);
		}
	}
	

}
