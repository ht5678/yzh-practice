package zookeeper.zkclient.lock;

import java.util.concurrent.Callable;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;



/**
 * 
 * @author yuezh2   2016年5月2日 下午10:20:13
 *
 */
public class ZkClientExt extends ZkClient {
	
	
	
	public ZkClientExt(String zkServers , int sessionTimeout,int connectTimeout , ZkSerializer zkSerializer){
		super(zkServers, sessionTimeout, connectTimeout, zkSerializer);
	}

	
	
	@Override
	public void watchForData(final String path) {
		// TODO Auto-generated method stub
		retryUntilConnected(new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				Stat stat = new Stat();
				_connection.readData(path, stat, true);
				return null;
			}
			
		});
	}
	

}
