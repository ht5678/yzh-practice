package zookeeper.zkclient.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;

/**
 * 
 * @author yuezh2   2016年4月29日 下午8:00:55
 *
 */
public class DefaultBalanceUpdateProvider implements BalanceUpdateProvider {
	//服务器详细配置信息的zk位置
	private String serverPath;
	//zk连接
	private ZkClient zc;
	
	
	/**
	 * 构造函数
	 * @param serverPath
	 * @param zc
	 */
	public DefaultBalanceUpdateProvider(String serverPath,ZkClient zc){
		this.serverPath = serverPath;
		this.zc = zc;
	}
	
	
	/**
	 * 负载+step
	 */
	@Override
	public boolean addBalance(Integer step) {
		Stat stat = new Stat();
		ServerData sd ;
		while(true){
			try{
				sd = zc.readData(this.serverPath,stat);
				sd.setBalance(sd.getBalance()+step);
				zc.writeData(this.serverPath, sd , stat.getVersion());
				return true;
			}catch(ZkBadVersionException e){
				//ignore
			}catch(Exception e){
				return false;
			}
		}
	}

	/**
	 * 负载-step
	 */
	@Override
	public boolean reduceBalance(Integer step) {
		Stat stat = new Stat();
		ServerData sd ;
		while(true){
			try{
				sd = zc.readData(this.serverPath,stat);
				final Integer currBalance = sd.getBalance();
				sd.setBalance(currBalance>step?(currBalance-step):0);
				zc.writeData(this.serverPath, sd , stat.getVersion());
				return true;
			}catch(ZkBadVersionException e){
				//ignore
			}catch(Exception e){
				return false;
			}
		}
	}

}
