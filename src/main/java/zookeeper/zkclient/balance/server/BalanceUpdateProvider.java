package zookeeper.zkclient.balance.server;


/**
 * 
 * @author yuezh2   2016年4月29日 下午7:53:29
 *
 */
public interface BalanceUpdateProvider {

	public boolean addBalance(Integer step);
	
	public boolean reduceBalance(Integer step);
	
}
