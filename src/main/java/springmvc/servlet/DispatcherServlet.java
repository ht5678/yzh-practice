package springmvc.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 核心控制类
 * @author yuezh2   2017年4月7日 下午3:36:17
 *
 */
@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet{
	
	public void init()throws ServletException{
		/**
		 * *扫描我们基础包上的注解: springmvc
		 * *扫描基础包之后,拿到 全限定名(包名+类名)
		 * springmvc\service\impl\DemoServiceImpl.java
		 * 替换上面的/
		 * springmvc.service.impl.DemoServiceImpl
		 * 拿到实例,
		 * 将实例注入到各层的bean 变量
		 */
		//扫描全包
		//找到实例
		//通过我们的方法连接来拿到相应的处理类实例
		
		
	}
	
	
	
	private void scanBase(String basePackage){
		URL url = this.getClass().getClassLoader().getResource("/"+replacePath(basePackage));
		//拿到该路径下面的文件夹和文件
		String pathFile = url.getFile();
		//最终的目的是将这个路径封装成一个File
		File file = new File(pathFile);
		String[] files = file.list();
		for(String path : files){
			//再次构造成一个file
			File eachFile = new File(pathFile+path);
			if(eachFile.isDirectory()){
				//递归应用scanbase
				scanBase(basePackage+"."+eachFile.getName());
			}else if(eachFile.isFile()){
				
			}
		}
	}
	
	//将全包名称替换成成一个路径/
	private String replacePath(String path){
		return path.replaceAll("\\.", "/");
	}
	
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, res);
	}

	
	
	
}
