package zookeeper.prac;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * 出现错误是因为没有watcher，并不影响结果
 * 
 * @author yuezh2   2016年1月15日 下午6:13:10
 *
 */
public class ZnodeSetData {
	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
//		String znode = "/test";
		String znode = "/consumers/grouptest/offsets/test/0";
		
		ZooKeeper zk = new ZooKeeper("10.99.205.17:2281", 1000*10, null);
		
		if(zk!=null){
			//获取状态
			Stat stat = zk.exists(znode, false);
			//设置znode的数据
			if(stat != null){
				System.out.println("znode修改之前的版本号"+stat.getVersion());
				Stat result = zk.setData(znode, new String("130").getBytes(), stat.getVersion());
				//测试版本号冲突
//				Stat result = zk.setData(znode, new String("我爱中国").getBytes(), stat.getVersion()-1);
				System.out.println("znode修改之后的版本号"+result.getVersion());
			}
		}
	}

}
