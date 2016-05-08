package zookeeper.zkclient.lock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;



/**
 * 
 * @author sdwhy
 *
 */
public class SimpleDistributedLockMutex extends BaseDistributeLock implements DistributeLock {

	//锁名称前缀，成功创建的顺序节点名称为：lock-000000 ， lock-000001
	private static final String LOCK_NAME="lock-";
	//zk中locker节点的路径，如 ： /locker
	private final String basePath;
	//获取锁以后自己创建的那个顺序节点的名称
	private String ourLockPath;
	
	
	
	private boolean internalLock(long time , TimeUnit unit)throws Exception{
		ourLockPath = attemptLock(time, unit);
		return ourLockPath != null;
	}
	
	
	
	/**
	 * 构造函数
	 * 
	 * @param client：操作zk
	 * @param basePath：zk的锁的根路径，也就是ppt架构中的locker节点
	 */
	public SimpleDistributedLockMutex(ZkClientExt client, String basePath) {
		super(client, basePath, LOCK_NAME);
		this.basePath = basePath;
	}
	
	

	@Override
	public void acquire() throws Exception {
		// TODO Auto-generated method stub
		if(!internalLock(-1, null)){
			throw new IOException("连接丢失！在路径："+basePath+" 下不能获取锁");
		}
		
	}

	@Override
	public boolean acquire(long time, TimeUnit unit) throws Exception {
		// TODO Auto-generated method stub
		return internalLock(time, unit);
	}

	@Override
	public void release() throws Exception {
		// TODO Auto-generated method stub
		releaseLock(ourLockPath);
	}

}
