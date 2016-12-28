package mytomcat;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * tomcat  核心服务类
 * @author yuezh2   2016年12月28日 下午2:29:47
 *
 */
public class Server {
	
	
	//统计服务被访问了多少次
	private static int count = 0;
	
	
	public static void main(String[] args) {
		//http底层就是socket
		try {
			ServerSocket ss = new ServerSocket();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
