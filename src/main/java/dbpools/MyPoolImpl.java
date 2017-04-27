package dbpools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import com.mysql.jdbc.Driver;

/**
 * 
 * @author yuezh2   2017年4月26日 下午4:41:13
 *
 */
public class MyPoolImpl implements IMypool {
	
	//准备连接池需要的参数(创建管道参数+连接限制管道数量参数)
	private static String jdbcDriver = "";
	private static String jdbcUrl = "";
	private static String userName = "";
	private static String pwd = "";
	//限制管道数量
	private static int initCount;
	private static int stepSize;
	private static int poolMaxSize;
	//把创建好的管道放入集合
	private static Vector<PooledConnection> pooledConnections = new Vector<>();
	
	
	public MyPoolImpl(){
		init();
	}
	
	
	
	/**
	 * 初始化连接池参数方法
	 */
	private void init(){
		//读取外部配置的参数
		InputStream is = this.getClass().getResourceAsStream("/dbpools/dbpools.properties");
		Properties properties = new Properties();
		try{
			properties.load(is);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		jdbcDriver = properties.getProperty("jdbcDriver");
		jdbcUrl = properties.getProperty("jdbcurl");
		userName = properties.getProperty("userName");
		pwd = properties.getProperty("password");

		initCount = Integer.valueOf(properties.getProperty("initCount"));
		stepSize = Integer.valueOf(properties.getProperty("stepSize"));
		poolMaxSize = Integer.valueOf(properties.getProperty("poolMaxSize"));
		
		//接下来我们需要创建初始数量的管道
		try{
			Driver mysqlDriver = (Driver) Class.forName(jdbcDriver).newInstance();
			DriverManager.registerDriver(mysqlDriver);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//真正的创建对象
		createConnections(initCount);
		
	}
	
	

	/**
	 * 对外提供管道服务
	 * @return
	 */
	public PooledConnection getConnection(){
		if(pooledConnections.size()==0){
			throw new RuntimeException("当前没有管道可用于数据库连接");
		}
		
		//获取管道对象 , 
		//1.没有被占用
		//2.管道没有超时
		PooledConnection conn = getRealConnection();
		while(conn == null){
			createConnections(stepSize);
			conn = getRealConnection();
			//通过经验 , 避免我们拿到的再次为空
			//休息一下,避免竞争
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	
	/**
	 * 自身判断我们拿去的管道是否是真实有效的
	 * @return
	 */
	private synchronized PooledConnection getRealConnection(){
		for(PooledConnection conn : pooledConnections){
			if(!conn.isBusy()){ //没有被占用
				Connection realConn = conn.getConn();
				//对连接池里的管道有效性进行检测
				try {
					if(!realConn.isValid(2000)){
						//业务处理无效的管道 , 替换管道
						Connection validConnection = DriverManager.getConnection(jdbcUrl , userName , pwd);
						conn.setConn(validConnection);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn.setBusy(true);
				//返回给外部服务使用
				return conn;
			}
		}
		return null;
	}
	
	
	/**
	 * 对内创建管道
	 * @param count
	 */
	public void createConnections(int count){
		if(poolMaxSize > 0 && (pooledConnections.size()+count)>poolMaxSize){
			throw new RuntimeException("创建数据库管道失败 , 原因是将超过上限值");
		}
		
		for(int i = 0 ; i < count ; i++){
			try {
				//这是我们真正的物理管道
				Connection conn = DriverManager.getConnection(jdbcUrl , userName , pwd);
				PooledConnection pooledConnection = new PooledConnection(conn, false);
				pooledConnections.add(pooledConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
