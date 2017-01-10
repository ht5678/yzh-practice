package mytomcat.demo1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * tomcat  核心服务类
 * @author yuezh2   2016年12月28日 下午2:29:47
 *
 */
public class Server {
	
	
	//统计服务被访问了多少次
	private static int count = 0;
	private static final int PORT = 9999;
	private static final String ENTER = "\r\n";
	private static final String SPACE = " ";
	
	
	
	public static void main(String[] args) {
		//http底层就是socket  , 提升作用域
		ServerSocket ss = null;
		Socket socket = null;
		
		try {
			ss = new ServerSocket(PORT);
			System.out.println("服务器已经初始化了 , 等待客户端连接中");
			
			//轮训
			while(true){
				socket = ss.accept();
				count++;
				System.out.println("第"+count+"次连接服务器");
				//--------------------------------------拿到请求信息-------------------------------------------------
				//获取输入流 , 获得用户的请求信息
				InputStream is = socket.getInputStream();
				byte[] bytes = new byte[1024];
				int len = is.read(bytes);
				if(len>0){
					String msg = new String(bytes,0,len);
					System.out.println(msg);
				}else{
					System.out.println("bad request");
				}
				
				//--------------------------------------发送请求信息-------------------------------------------------
				OutputStream os = socket.getOutputStream();
				
				//正文
				String html = "<html><head><title>请求成功</title></head><body>"+new Date()+"</body></html>";
				
				StringBuilder sb = new StringBuilder();
				/*通用头域begin*/
				sb.append("HTTP/1.1").append(SPACE).append("200").append(SPACE).append("OK").append(ENTER);
				sb.append("Server:myServer").append(SPACE).append("0.0.1v").append(ENTER);
				sb.append("Date:Sat,"+SPACE).append(new Date()).append(ENTER);
				sb.append("Content-Type:text/html;charset=UTF-8").append(ENTER);
				sb.append("Content-Length:").append(html.getBytes().length).append(ENTER);
				/*通用头域end*/
				sb.append(ENTER);//空一行
				sb.append(html);//正文部分
				
				os.write(sb.toString().getBytes());
				os.flush();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
