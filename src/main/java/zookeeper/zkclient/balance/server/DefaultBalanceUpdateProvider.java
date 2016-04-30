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
	
	private String serverPath;
	
	private ZkClient zc;
	
	
	public DefaultBalanceUpdateProvider(String serverPath,ZkClient zc){
		this.serverPath = serverPath;
		this.zc = zc;
	}
	
	
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
