package zookeeper.zkclient.balance.client;


/**
 * 
 * @author yuezh2   2016年4月29日 下午8:16:55
 *
 */
public interface Client {

	public void connect()throws Exception;
	
	public void disConnect()throws Exception;
	
	
}
