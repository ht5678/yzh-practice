package zookeeper.prac;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 出现错误是因为没有watcher，并不影响结果
 * @author yuezh2   2016年1月15日 下午6:17:59
 *
 */
public class ZnodeGetData {
	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		String znode = "/consumers/grouptest/offsets/test/2";
//		String znode = "/test";
//		String znode = "/consumers/logstash/offsets";
		
		ZooKeeper zk = new ZooKeeper("10.99.205.17:2281", 1000*10, null);
		
		if(zk!=null){
			//获取状态
			Stat stat = zk.exists(znode, false);
			//获取znode的数据
			if(stat != null){
				byte[] bytes = zk.getData(znode, false, stat);
				System.out.println(new String(bytes));
			}
		}
	}

}
