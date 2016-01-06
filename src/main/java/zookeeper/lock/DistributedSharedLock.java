package zookeeper.lock;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * zk分布式锁
 * @author yuezh2   2016年1月4日 下午7:40:09
 *
 */
public class DistributedSharedLock implements Watcher{
	
	/* zk地址：端口 */
	private static final String ADDR = "10.99.205.17:2281";
	/* zk目录 */
	private static final String LOCK_NODE = "guid-lock-";
	/* 锁目录 */
	private String rootLockNode;
	
	private ZooKeeper zk = null;
	
	private Integer mutex;
	
	private Integer currentLock;
	
	
	
	/**
	 * 构造函数
	 * @param rootLockNode
	 */
	public DistributedSharedLock(String rootLockNode) {
		this.rootLockNode = rootLockNode;
		//连接zk服务器
		try{
			zk = new ZooKeeper(ADDR, 10*10000, this);
		}catch(IOException e){
			e.printStackTrace();
		}
		mutex = new Integer(-1);
		//create zk node name
		if(zk != null){
			//建立根目录节点
			try {
				Stat s = zk.exists(rootLockNode, false);
				if(s == null){
					zk.create(rootLockNode, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	
	
	public void acquire() throws KeeperException, InterruptedException{
		ByteBuffer b = ByteBuffer.allocate(4);
		byte[] value;
		//add child with value i
		b.putInt(ThreadLocalRandom.current().nextInt(10));
		value = b.array();
		//创建锁节点
		String lockName = zk.create(rootLockNode + "/"+LOCK_NODE, value, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		synchronized (mutex) {
			while(true){
				//获取当前锁节点的number，和所有的锁节点比较
				Integer acquireLock = new Integer(lockName.lastIndexOf('-')+1);
				List<String> childLockNode = zk.getChildren(rootLockNode, true);
				
				SortedSet<Integer> sortedLock = new TreeSet<>();
				for(String temp : childLockNode){
					Integer tempLockNumber = new Integer(temp.substring(temp.lastIndexOf('-')+1));
					sortedLock.add(tempLockNumber);
				}
				currentLock = sortedLock.first();
				
				//如果当前创建的锁的序号是最小的那么认为这个客户端获得了锁
				if(currentLock >= acquireLock){
					System.err.println("thread_name="+Thread.currentThread().getName()+"|attend lcok|lock_num"+currentLock);
					return;
				}else{
					//如果没有获得锁则等下次事件的发生
					System.err.println("thread_name="+Thread.currentThread().getName()+"|wait lock|lock_num"+currentLock);
					mutex.wait();
				}
				
			}
		}
	}
	
	
	/**
	 * 释放锁
	 * @throws InterruptedException
	 * @throws KeeperException
	 */
	public void release() throws InterruptedException, KeeperException{
		String lockName = String.format("%010d", currentLock);
		zk.delete(rootLockNode + "/" + LOCK_NODE + lockName, -1);
		System.err.println("thread_name="+Thread.currentThread().getName()+"|release lock | lock_num"+currentLock);
	}
	
	
	/**
	 * 请求zk服务器，获取锁
	 */
	@Override
	public void process(WatchedEvent arg0) {
		synchronized (mutex) {
			mutex.notify();
		}
	}

	
	
}
