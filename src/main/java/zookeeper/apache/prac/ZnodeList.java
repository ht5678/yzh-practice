
package zookeeper.apache.prac;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * 出现错误是因为没有watcher，并不影响结果
 * 
 * @author yuezh2   2016年1月15日 下午5:44:55
 *
 */
public class ZnodeList {

	
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper("10.99.205.17:2281", 1000*10, null);
		
		if(zk != null){
			printZnode("/", zk);
		}
	}
	
	
	/**
	 * 
	 * @param path
	 * @param zk
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public static void printZnode(String path,ZooKeeper zk) throws KeeperException, InterruptedException{
		List<String> datas = zk.getChildren(path, false);
		for(String data :datas){
			System.out.println(" +"+data);
		}
	}
	
	
	/**
	 * 
	 * @param path
	 * @param zk
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public static void printZnode2(String path,ZooKeeper zk) throws KeeperException, InterruptedException{
		List<String> datas = zk.getChildren(path, false);
		for(String data :datas){
//			System.out.println("================"+data+"=================");
			System.out.println(" +"+data);
			Stat stat = zk.exists(path+data, false);
			//如果节点存在，打印子节点
			if(stat != null){
				int numChildren = stat.getNumChildren();
				//如果子节点数量>0
				if(numChildren > 0){
					List<String> childs = zk.getChildren(path+data, false);
					//循环打印子节点
					if(childs!=null){
						printZnode(path+data, zk);
					}
				}
			}
//			System.out.println("================"+data+"=================");
		}
	}
	
	
	
	
}
