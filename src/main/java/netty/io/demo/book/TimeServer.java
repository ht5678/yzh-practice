package netty.io.demo.book;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author yuezh2   2018年4月10日 上午10:45:37
 *
 */
public class TimeServer {

	
	public static void main(String[] args) throws Exception{
		int port = 8080;
		ServerSocket server = null;
		try{
			server = new ServerSocket(port);
			System.out.println("the time server is start in port:"+port);
			Socket socket = null;
			
			while(true){
				socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
			}
		}finally{
			if(server!=null){
				System.out.println("the time server close");
				server.close();
				server = null;
			}
		}
	}
	
	
	
	
	
	
}
