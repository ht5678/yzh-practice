package zookeeper.zkclient.subscribe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * 
 * publish/subscribe
 * 
 * 应用场景:
 * 线上配置修改
 * 
 * 
 * 测试方式:
 * *启动
 * *连接ZOOKEEPER_SERVER的客户端
 * *在zk的客户端执行:
 * 		create /command list		-- 列出所有节点ip
 * 		set /command create		--初始化配置
 * 		set /command modify		--更新配置
 * 
 * 
 * 
 * @author yuezh2   2016年4月20日 上午11:12:49
 *
 */
public class SubscribeZkClient {
	
	
	private static final int CLIENT_QTY = 5;
	
	private static final String ZOOKEEPER_SERVER = "10.99.205.22:12181";
	
	private static final String CONFIG_PATH = "/config";
	
	private static final String COMMAND_PATH = "/command";
	
	private static final String SERVERS_PATH = "/servers";
	
	
	
	public static void main(String[] args) {
		List<ZkClient> clients = new ArrayList<>();
		List<WorkServer> workServers = new ArrayList<>();
		ManageServer manageServer = null;
		
		try{
			ServerConfig initConfig = new ServerConfig();
			initConfig.setDbUrl("jdbc:mysql://localhost:3306/mydb");
			initConfig.setUsername("admin");
			initConfig.setPassword("12345");
			
			ZkClient clientManage = new ZkClient(ZOOKEEPER_SERVER, 50000, 50000,new BytesPushThroughSerializer());
			manageServer = new ManageServer(SERVERS_PATH, COMMAND_PATH, CONFIG_PATH, clientManage, initConfig);
			manageServer.start();
			
			for(int i = 0 ; i < CLIENT_QTY ;++i){
				ZkClient client = new ZkClient(ZOOKEEPER_SERVER, 50000, 50000,new BytesPushThroughSerializer());
				clients.add(client);
				ServerData serverData = new ServerData();
				serverData.setId(i);
				serverData.setName("WorkServer#"+i);
				serverData.setAddress("192.168.1."+i);
				
				WorkServer workServer = new WorkServer(CONFIG_PATH, SERVERS_PATH, serverData, client, initConfig);
				workServers.add(workServer);
				workServer.start();
			}
			System.out.println("敲回车键退出\n");
			new BufferedReader(new InputStreamReader(System.in)).readLine();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			System.out.println("shutting down ......");
			
			for(WorkServer workServer : workServers){
				try {
					workServer.stop();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
			for(ZkClient client : clients){
				try {
					client.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		
	}

}
