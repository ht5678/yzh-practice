package dbpools;



/**
 * 定义我们的连接池标准
 * @author yuezh2   2017年4月26日 下午4:32:13
 *
 */
public interface IMypool {

	/**
	 * 对外提供管道服务
	 * @return
	 */
	PooledConnection getConnection();
	
	/**
	 * 对内创建管道
	 * @param count
	 */
	void createConnections(int count);
	
}
