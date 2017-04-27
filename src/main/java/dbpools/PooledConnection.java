package dbpools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 被数据库连接池管理的管道对象
 * @author yuezh2   2017年4月26日 下午4:09:18
 *
 */
public class PooledConnection {
	//封装一个真正的物理数据连接管道
	private Connection conn;
	//标识管道是否被占用
	private boolean isBusy = false;
	
	
	/**
	 * 初始化构造函数
	 * @param conn
	 * @param isBusy
	 */
	public PooledConnection(Connection conn,boolean isBusy){
		this.conn = conn;
		this.isBusy=isBusy;
	}
	
	
	//管道本身要具备释放的方法
	public void close(){
		this.isBusy = false;
	}
	
	
	
	/**
	 * 连接管道具备操作数据库的方法
	 * @param sql
	 * @return
	 */
	public ResultSet queryBySql(String sql){
		ResultSet rs = null;
		Statement statement = null;
		try{
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
		}catch(SQLException e){
			System.out.println("执行出现异常: "+e.getMessage());
		}
		return rs;
	}
	
	

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
	

}
