package zookeeper.subscribe;

import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author yuezh2   2016年4月20日 上午1:20:31
 *
 */
public class ManageServer {
	
	/** zk client */
	private ZkClient zkClient;
	/** zk中config节点的路径 */
	private String configPath;
	/**  zk中servers节点的路径 */
	private String serversPath;
	/**  zk中command节点的路径 */
	private String commandPath;
	/** 该工作节点的配置信息 */
	private ServerConfig serverConfig;
	/** servers子节点监听器 */
	private IZkChildListener childListener;
	/** 监听command节点的内容变更 */
	private IZkDataListener dataListener;
	/** work server列表 */
	private List<String> workServerList = new ArrayList<>();
	
	
	/**
	 * 构造函数
	 * @param configPath	zk中config节点的路径
	 * @param serverPath	zk中servers节点的路径
	 * @param commandPath  zk中存放命令的节点
	 * @param zkClient		zkclient
	 * @param initConfig		初始化的配置
	 */
	public ManageServer(String serversPath , String commandPath , String configPath , ZkClient zkClient , ServerConfig initConfig){
		this.serversPath = serversPath;
		this.commandPath = commandPath;
		this.configPath = configPath;
		this.zkClient = zkClient;
		this.serverConfig = initConfig;
		this.childListener = new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				StringBuilder sb = new StringBuilder();
				for(String child : currentChilds){
					sb.append(child).append(",");
				}
				System.out.println("server child changes : "+sb.toString());
				workServerList = currentChilds;
			}
		};
		
		this.dataListener = new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				//ignore
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				//获取最新的命令并且执行
				String cmd = new String((byte[])data);
				System.out.println("获取最新命令:"+cmd);
				exeCmd(cmd);
			}
		};
	}
	
	
	
	/**
	 * 订阅工作节点的节点数更新事件
	 * 订阅命令节点的内容更新事件
	 */
	private void initRunning(){
		zkClient.subscribeChildChanges(serversPath, childListener);
		zkClient.subscribeDataChanges(commandPath, dataListener);
	}
	
	
	/**
	 * 启动zk管理节点
	 */
	public void start(){
		initRunning();
	}
	
	
	
	/**
	 * 取消节点的事件监听
	 */
	public void stop(){
		zkClient.unsubscribeChildChanges(serversPath, childListener);
		zkClient.unsubscribeDataChanges(commandPath, dataListener);
	}
	
	
	
	/**
	 * 类型:
	 * 1.list : 列出所有的工作节点(workserverlist)
	 * 2.create : 在zk中创建config节点
	 * 3.modify: 修改zk中config节点的配置
	 * 
	 * @param cmdType
	 */
	private void exeCmd(String cmdType){
		
		if("list".equals(cmdType)){
			execList();
		}else if("create".equals(cmdType)){
			execCreate();
		}else if("modify".equals(cmdType)){
			execModify();
		}else{
			System.out.println("error command type");
		}
		
	}
	
	
	/**
	 * 列出工作服务器的列表
	 */
	private void execList(){
		System.out.println(workServerList.toString());
	}
	
	
	/**
	 * 创建config节点
	 */
	private void execCreate(){
		if(!zkClient.exists(configPath)){
			try{
				zkClient.createPersistent(configPath,JSON.toJSONString(serverConfig).getBytes());
			}catch(ZkNodeExistsException e){
				//节点已经存在了,更新
				zkClient.writeData(configPath, JSON.toJSONString(serverConfig).getBytes());
			}catch(ZkNoNodeException e){
				//父节点不存在
				String parentDir = configPath.substring(0,configPath.lastIndexOf('/'));
				zkClient.createPersistent(parentDir, true);
			}
		}
	}
	
	
	
	/**
	 * 修改工作服务器节点
	 */
	private void execModify(){
		serverConfig.setDbUrl(serverConfig.getDbUrl()+"_modify");
		try{
			zkClient.writeData(configPath, JSON.toJSONString(serverConfig).getBytes());
		}catch(ZkNoNodeException e){
			zkClient.createPersistent(configPath, JSON.toJSONString(serverConfig).getBytes());
		}
	}
	
}
