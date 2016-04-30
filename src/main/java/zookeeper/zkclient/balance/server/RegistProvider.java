package zookeeper.zkclient.balance.server;


/**
 * 
 * @author yuezh2   2016年4月29日 下午7:52:19
 *
 */
public interface RegistProvider {
	
	
	public void regist(Object context) throws Exception;
	
	
	
	public void unRegist(Object context) throws Exception;
	

}
