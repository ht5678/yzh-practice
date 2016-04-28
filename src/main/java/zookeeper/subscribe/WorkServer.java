package zookeeper.subscribe;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

import com.alibaba.fastjson.JSON;

/**
 * 工作服务器节点 操作
 * @author yuezh2   2016年4月20日 上午12:52:42
 *
 */
public class WorkServer {
	/** zk client */
	private ZkClient zkClient;
	/** zk中config节点的路径 */
	private String configPath;
	/**  zk中servers节点的路径 */
	private String serversPath;
	/** 该工作节点的基本信息 */
	private ServerData serverData;
	/** 该工作节点的配置信息 */
	private ServerConfig serverConfig;
	/** 数据修改监听器 */
	private IZkDataListener dataListener;

	
	/**
	 * 构造函数
	 * @param configPath	zk中config节点的路径
	 * @param serverPath	zk中servers节点的路径
	 * @param serverData	该工作节点的基本信息
	 * @param zkClient		zkclient
	 * @param initConfig		初始化的配置
	 */
	public WorkServer(String configPath , String serverPath , ServerData serverData , ZkClient zkClient , ServerConfig initConfig){
		this.configPath = configPath;
		this.serversPath = serverPath;
		this.serverData = serverData;
		this.zkClient = zkClient;
		this.serverConfig = initConfig;
		this.dataListener = new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				//配置文件更新
				String retJson = new String((byte[])data);
				System.out.println("配置文件更新:"+retJson);
				ServerConfig serverConfigLocal = (ServerConfig)JSON.parseObject(retJson,ServerConfig.class);
				updateConfig(serverConfigLocal);
			}
		};
	}
	
	/**
	 * 上线工作节点服务器
	 * 
	 * *创建该工作节点的临时节点
	 * *为该工作节点订阅zk配置文件节点的配置更新事件
	 * 
	 */
	public void start(){
		System.out.println("work server start ...  ");
		initRunning();
		
	}
	
	
	/**
	 * 下线工作节点服务器,并且取消config的配置更新监听
	 */
	public void stop(){
		System.out.println("work server stop ... ");
		zkClient.unsubscribeDataChanges(configPath, dataListener);
	}
	
	
	
	private void initRunning(){
		registMe();
		
		zkClient.subscribeDataChanges(configPath, dataListener);
	
	}
	
	
	/**
	 * 注册带有该工作节点信息的临时节点,并且注册zk上配置节点的数据更新事件
	 */
	private void registMe(){
		String mePath = serversPath.concat("/").concat(serverData.getAddress());
		try{
			zkClient.createEphemeral(mePath,JSON.toJSONString(serverData).getBytes());
		}catch(ZkNoNodeException e){
			//出现这个异常表示serverspath可能没有创建,这个时候要先创建serverspath,. 然后再创建  工作节点  的临时节点
			zkClient.createPersistent(serversPath,true);
			registMe();
		}
		
	}
	
	
	
	private void updateConfig(ServerConfig configLocal){
		this.serverConfig = configLocal;
	}
	
	
}
