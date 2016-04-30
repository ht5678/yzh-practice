package zookeeper.zkclient.balance.client;

/**
 * 
 * @author yuezh2   2016年4月29日 下午8:17:37
 *
 */
public interface BalanceProvider<T> {

	
	public T getBalanceItem();
	
}
