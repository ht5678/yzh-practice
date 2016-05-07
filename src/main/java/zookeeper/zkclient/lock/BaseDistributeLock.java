package zookeeper.zkclient.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * 
 * @author yuezh2   2016年5月2日 下午10:19:08
 *
 */
public class BaseDistributeLock {

	private final ZkClientExt client;
	
	private final String path;
	
	private final String basePath;
	
	private final String lockName;
	
	private static final Integer MAX_RETRY_COUNT = 10;
	
	
	public BaseDistributeLock(ZkClientExt client , String path , String lockName){
		this.client = client;
		this.basePath = path;
		this.path = path.concat("/").concat(lockName);
		this.lockName = lockName;
	}
	
	
	
	private void deleteOurPath(String ourPath)throws Exception{
		client.delete(ourPath);
	}
	
	
	private String createLockNode(ZkClient client , String path)throws Exception{
		return client.createEphemeralSequential(path, null);
	}
	
	
	private boolean waitToLock(long startMills , Long millsToWait , String ourPath)throws Exception{
		boolean haveTheLock = false;
		boolean doDelete = false;
		
		try {
			while(!haveTheLock){
				List<String> children = new ArrayList<>();
				String sequenceNodeName = ourPath.substring(basePath.length()+1);
				
				int ourIndex = children.indexOf(sequenceNodeName);
				if(ourIndex<0){
					throw new ZkNoNodeException("节点没有找到"+sequenceNodeName);
				}
				
				boolean isGetTheLock = ourIndex ==0;
				String pathToWatch = isGetTheLock?null : children.get(ourIndex-1);
				
				if(isGetTheLock){
					haveTheLock = true;
				}else{
					String previousSequencePath = basePath.concat("/").concat(pathToWatch);
					final CountDownLatch latch = new CountDownLatch(1);
					final IZkDataListener previousListener = new IZkDataListener() {
						
						@Override
						public void handleDataDeleted(String dataPath) throws Exception {
							// TODO Auto-generated method stub
							latch.countDown();
						}
						
						@Override
						public void handleDataChange(String dataPath, Object data) throws Exception {
							// TODO Auto-generated method stub
							//ignore
						}
					};
					
					try {
						//如果节点不存在会出现异常
						client.unsubscribeDataChanges(previousSequencePath, previousListener);
						
						if(millsToWait!=null){
							millsToWait = millsToWait-(System.currentTimeMillis()-startMills);
							startMills = System.currentTimeMillis();
							if(millsToWait<=0){
								doDelete = true; 	//timed out  - delete our node
								break;
							}
							latch.await(millsToWait, TimeUnit.MICROSECONDS);
						}else{
							latch.await();
						}
						
						
					}catch(ZkNoNodeException e){
						//ignore
					} finally {
						client.unsubscribeDataChanges(previousSequencePath, previousListener);
					}
					
				}
				
			}
		} catch (Exception e) {
			//发生异常需要删除节点
			doDelete = true;
			throw e;
		}finally {
			//如果需要删除节点
			if(doDelete){
				deleteOurPath(ourPath);
			}
		}
		return haveTheLock;
		
	}
	
	
	
	
	private String getLockNodeNumber(String str , String lockName){
		int index = str.lastIndexOf(lockName);
		if(index>=0){
			index = index + lockName.length();
			return index<=str.length()?str.substring(index):"";
		}
		return str;
	}
	
	
	
	List<String> getSortedChildren()throws Exception{
		try{
			List<String> children = client.getChildren(basePath);
			Collections.sort(
					children,
					new Comparator<String>() {

						@Override
						public int compare(String lhs, String rhs) {
							return getLockNodeNumber(lhs, lockName).compareTo(getLockNodeNumber(rhs, lockName));
						}
						
					}
				);
			return children;
		}catch(ZkNoNodeException e){
			client.createPersistent(basePath,true);
			return getSortedChildren();
		}
	}
	
	
	
	protected void releaseLock(String lockPath)throws Exception {
		deleteOurPath(lockPath);
	}
	
	
	
	protected String attemptLock(long time,TimeUnit unit)throws Exception {
		final long startMills = System.currentTimeMillis();
		final Long millsToWait = (unit!=null)?unit.toMillis(time):null;
		
		String ourPath = null;
		boolean hasTheLock = false;
		boolean isDone = false;
		int retryCount = 0;
		
		//网络闪断需要重试
		while(!isDone){
			isDone = true;
			try {
				ourPath = createLockNode(client, path);
				hasTheLock = waitToLock(startMills, millsToWait, ourPath);
			} catch (ZkNoNodeException e) {
				if(retryCount++<MAX_RETRY_COUNT){
					isDone = false;
				}else{
					throw e;
				}
			}
		}
		if(hasTheLock){
			return ourPath;
		}
		return null;
	}
	
	
}
