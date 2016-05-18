package zookeeper.zkclient.nameservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * 
 * @author sdwhy
 *
 */
public class IdMaker {
	
	//客户端连接
	private ZkClient zkClient = null;
	//zk的地址
	private final String server ;
	//根节点名称
	private final String root;
	//节点名称
	private final String nodeName;
	//服务是否正在运行
	private volatile boolean running  = false;
	//线程池
	private ExecutorService cleanExector = null;
	
	
	/**
	 * 删除节点方式
	 *
	 */
	public enum RemoveMethod{
		NONE,IMMEDIATELY,DELAY
	}
	
	
	
	
	/**
	 * 构造函数
	 * @param zkServer
	 * @param root
	 * @param nodeName
	 */
	public IdMaker(String zkServer , String root , String nodeName){
		this.root = root;
		this.server = zkServer;
		this.nodeName = nodeName;
	}
	
	
	
	
	/**
	 * 启动服务
	 * @throws Exception
	 */
	public void start()throws Exception{
		if(running){//服务已经运行
			throw new Exception("server has stated ... ");
		}
		running = true;
		//服务初始化
		init();
		
	}
	
	
	
	/**
	 * 停止服务
	 * @throws Exception
	 */
	public void stop()throws Exception{
		if(!running){
			throw new Exception("server has stopped ......");
		}
		running = false;
		
		//释放资源
		freeResource();
	}
	
	
	/**
	 * 初始化服务资源
	 */
	private void init(){
		zkClient = new ZkClient(server, 5000 , 5000, new BytesPushThroughSerializer());
		cleanExector = Executors.newFixedThreadPool(10);
		try{
			zkClient.createPersistent(root, true);
		}catch(ZkNodeExistsException e){
			//ignore
		}
	}
	
	/**
	 * 释放服务资源
	 */
	private void freeResource(){
		if(zkClient!=null){
			zkClient.close();
			zkClient = null;
		}
	}
	
	
	/**
	 * 检测当前服务是否正在运行
	 */
	private void checkRunning()throws Exception{
		if(!running){
			throw new Exception("请先调用start");
		}
	}
	
	
	/**
	 * 生成分布式id
	 * @return
	 * @throws Exception
	 */
	public String generateId()throws Exception{
		
		checkRunning();
		
		final String fullNodePath = root.concat("/").concat(nodeName);
		//持久顺序节点
		final String ourPath = zkClient.createPersistentSequential(fullNodePath, null);
		
		//为了避免资源浪费,删除掉节点
		//方式一:立即删除
		zkClient.delete(ourPath);
		//方式二:延迟删除,线程池
		
		//node-00000000 , node-00000001 
		return ExtractId(ourPath);
	}
	
	
	/**
	 * 截取顺序节点的数字,id
	 * @param ourPath
	 * @return
	 */
	private String ExtractId(String ourPath){
		int index = ourPath.lastIndexOf(nodeName);
		if(index>=0){
			index = index + nodeName.length();
			return index<=ourPath.length()?ourPath.substring(index):"";
		}
		return ourPath;
	}
	

}
