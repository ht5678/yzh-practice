package dbpools;

import java.sql.ResultSet;

/**
 * 
 * @author yuezh2   2017年4月27日 下午3:15:33
 *
 */
public class MyPoolTest {

	private static MyPoolImpl poolImpl = PoolManager.getPoolInstance();
	
	
	
	/**
	 * 业务测试方法
	 */
	public static void selectDate(){
		PooledConnection conn = poolImpl.getConnection();
		ResultSet rs = conn.queryBySql("select * from auth_user");
		System.out.println("线程名称:"+Thread.currentThread().getName());
		try{
			while(rs.next()){
				System.out.println("username:"+rs.getString("username"));
			}
			rs.close();
			//管道线程占用表示置位没有占用 , 从而达到服用目的 , epoll机制
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//测试一: 基础测试
//		MyPoolTest test = new MyPoolTest();
//		test.selectDate();
		
		//测试二:多线程测试
		for(int i = 0 ; i <4000 ;i++){
			new Thread(new Runnable() {
				public void run() {
					//真正的业务操作方法
					MyPoolTest test = new MyPoolTest();
					test.selectDate();
				}
			}).start();
		}
	}
	
}
