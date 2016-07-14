package guice.demo.base;



/**
 * 
 * @author yuezh2   2016年7月13日 下午5:11:25
 *
 */
public class DatabaseTransactionLog implements TransactionLog {
	
	private String jdbcUrl;
	
	private Integer threadPoolSize;
	
	private Connection connection;
	
	
	
	/**
	 * 
	 */
	public DatabaseTransactionLog(){
		
	}
	
	
	
	
	/**
	 * 
	 */
	public DatabaseTransactionLog(Connection connection){
		System.out.println("DatabaseTransactionLog constructor");
	}
	
	

	
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}


}
