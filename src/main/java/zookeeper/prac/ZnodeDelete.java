package zookeeper.prac;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * 出现错误是因为没有watcher，并不影响结果
 * 
 * @author yuezh2   2016年1月15日 下午6:06:08
 *
 */
public class ZnodeDelete {
	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		String deleteZnode = "/test";
		
		ZooKeeper zk = new ZooKeeper("10.99.205.17:2281", 1000*10, null);
		
		if(zk!=null){
			//获取状态
			Stat stat = zk.exists(deleteZnode, false);
			//删除znode，如果版本号设置成-1，代表任何版本的znode都会被删除
			if(stat != null){
				zk.delete(deleteZnode, stat.getVersion());
			}
		}
	}

}
