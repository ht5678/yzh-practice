package mytomcat.demo4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import kafka.common.Config;

/**
 * tomcat  核心服务类
 * 
 * 静态文件请求:
 * 		http://localhost:9999/src/main/webapp/pages/login/login.html
 * 
 * 		http://localhost:9999/src/main/webapp/pages/login/regist.html
 * 
 * 
 * @author yuezh2   2016年12月28日 下午2:29:47
 *
 */
public class Server {
	
	
	//统计服务被访问了多少次
	private static int count = 0;
	private static final int PORT = 9999;
	
	
	
	
	public static void main(String[] args) {
		//http底层就是socket  , 提升作用域
		ServerSocket ss = null;
		Socket socket = null;
		
		
		try {
			
			ConfigInitializer.init();
			Map<String, String> urlMappings = ConfigInitializer.getUrlMappings();
			
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
				HttpRequest request = new HttpRequest(is);
				
				
				//--------------------------------------发送请求信息-------------------------------------------------
				OutputStream os = socket.getOutputStream();
				HttpResponse response = new HttpResponse(os);
				
				
				//--------------------------------------业务逻辑-------------------------------------------------
				//静态请求
				String uri = request.getUri();
				if(uri.endsWith(".html")){
					response.writeHtmlFile(uri);
				}else if(uri.endsWith(".action")){
					
					String className = urlMappings.get(uri);
					Class<HttpServlet> clazz = (Class<HttpServlet>) Class.forName(className.trim());
					HttpServlet hs = clazz.newInstance();
					hs.service(request, response);
					
				}else{
					response.writeHtmlFile("/src/main/webapp/pages/login/error.html");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
