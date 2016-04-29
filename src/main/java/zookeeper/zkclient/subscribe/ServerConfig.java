package zookeeper.zkclient.subscribe;


/**
 * 节点的配置
 * @author yuezh2   2016年4月20日 上午12:47:08
 *
 */
public class ServerConfig {

	/** 数据库的连接地址 */
	private String dbUrl;
	/** 用户名 */
	private String username;
	/** 密码 */
	private String password;
	
	

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
