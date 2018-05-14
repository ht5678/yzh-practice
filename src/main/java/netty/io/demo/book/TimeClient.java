package netty.io.demo.book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author yuezh2   2018年4月11日 下午2:41:48
 *
 */
public class TimeClient {

	
	public static void main(String[] args) {
		int port =8080;
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			socket =new Socket("127.0.0.1", port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf8"));
			out = new PrintWriter(socket.getOutputStream(),true);
			out.println("send order 2 server succeed");
			String resp = in.readLine();
			System.out.println("Now is :"+resp);
		}catch(Exception e){
			//nothing
		}finally {
			// TODO: handle finally clause
		}
	}
	
	
}
