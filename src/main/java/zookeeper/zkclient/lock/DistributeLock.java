package zookeeper.zkclient.lock;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author yuezh2   2016年5月2日 下午10:09:42
 *
 */
public interface DistributeLock {

	
	/**
	 * 获取锁,如果没有就等待
	 * @throws Exception
	 */
	public void acquire()throws Exception;
	
	
	/**
	 * 获取锁,直到超时
	 * @param time
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	public boolean acquire(long time , TimeUnit unit) throws Exception;
	
	
	
	/**
	 * 释放锁
	 * @throws Exception
	 */
	public void release()throws Exception;
	
}
