package zookeeper.mastersel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

/**
 * 
 * @author sdwhy
 *
 */
public class WorkServer {
	
	//节点是否正在运行
	private volatile boolean running = false;
	//zk连接
	private ZkClient zkClient;
	//master节点路径
	private static final String MATER_PATH="/master";
	//监听master节点删除事件
	private IZkDataListener dataListener;
	//记录当前服务器的基本信息
	private RunningData serverData;
	//记录当前集群master的节点信息
	private RunningData masterData;
	
	private ScheduledExecutorService delayExcutor = Executors.newScheduledThreadPool(1);
	
	private int delayTime =5;
	
	
	
	public WorkServer(RunningData rd){
		this.serverData = rd;
		this.dataListener = new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String arg0) throws Exception {
				// TODO Auto-generated method stub
				takeMaster();
				if(masterData != null && masterData.getName().equals(serverData.getName())){
					takeMaster();
				}else{
					delayExcutor.schedule(new Runnable() {
						public void run() {
							takeMaster();
						}
					},delayTime,TimeUnit.SECONDS);
				}
			}
			
			@Override
			public void handleDataChange(String arg0, Object arg1) throws Exception {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	
	public void start() throws Exception{
		if(running){
			throw new Exception("server has startup ... ");
		}
		running = true;
		zkClient.subscribeDataChanges(MATER_PATH, dataListener);
	}
	
	
	public void stop() throws Exception{
		if(!running){
			throw new Exception("server has stopped ... ");
		}
		running = false;
		zkClient.unsubscribeDataChanges(MATER_PATH, dataListener);
		releaseMaster();
	}

	
	private void takeMaster(){
		if(!running)
			return;
		try{
			zkClient.create(MATER_PATH, serverData, CreateMode.EPHEMERAL);
			masterData = serverData;
			
			//作为演示,每隔5s钟释放一次master
			delayExcutor.schedule(new Runnable() {
				public void run() {
					if(checkMaster()){
						releaseMaster();
					}
				}
			}, 5, TimeUnit.SECONDS);
			
		}catch(ZkNodeExistsException e){
			RunningData runningData = zkClient.readData(MATER_PATH, true);
			if(runningData == null){
				takeMaster();
			}else{
				masterData = runningData;
			}
		}catch(Exception e2){
			System.out.println(e2.getMessage());
		}
	}
	
	
	private void releaseMaster(){
		if(checkMaster()){
			zkClient.delete(MATER_PATH);
		}
	}
	
	
	private boolean checkMaster(){

		try{
			RunningData eventData = zkClient.readData(MATER_PATH);
			masterData = eventData;
			
			if(masterData.getName().equals(serverData.getName())){
				return true;
			}
			
			return false;
		}catch(ZkNoNodeException e){
			return false;
		}catch (ZkInterruptedException e) {
			return checkMaster();
		}catch(ZkException e){
			return false;
		}
	}
	
	
	
}
