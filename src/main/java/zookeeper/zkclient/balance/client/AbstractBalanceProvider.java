package zookeeper.zkclient.balance.client;

import java.util.List;

/**
 * 
 * @author yuezh2   2016年4月29日 下午8:18:12
 *
 */
public abstract class AbstractBalanceProvider<T> implements BalanceProvider<T> {

	
	
	protected abstract T balanceAlgorithm(List<T> items);
	
	
	protected abstract List<T> getBalanceItems();
	
	
	
	public T getBalanceItem(){
		return balanceAlgorithm(getBalanceItems());
	}
	
}
