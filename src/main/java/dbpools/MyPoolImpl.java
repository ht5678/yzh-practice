package dbpools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Properties;

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
	
	
	/**
	 * 初始化连接池参数方法
	 */
	private void init(){
		//读取外部配置的参数
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("/dbpools/dbpools.properties");
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

		initCount = Integer.valueOf(properties.getProperty("jdbcDriver"));
		stepSize = Integer.valueOf(properties.getProperty("jdbcDriver"));
		poolMaxSize = Integer.valueOf(properties.getProperty("jdbcDriver"));
		
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
		return null;
	}
	
	/**
	 * 对内创建管道
	 * @param count
	 */
	public void createConnections(int count){
		
	}

}
