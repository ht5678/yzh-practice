package zookeeper.zkclient.lock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;



/**
 * 
 * @author sdwhy
 *
 */
public class SimpleDistributedLockMutex extends BaseDistributeLock implements DistributeLock {

	//锁名称前缀
	private static final String LOCK_NAME="lock-";
	
	private final String basePath;
	
	private String ourLockPath;
	
	
	private boolean internalLock(long time , TimeUnit unit)throws Exception{
		ourLockPath = attemptLock(time, unit);
		return ourLockPath != null;
	}
	
	
	
	
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
