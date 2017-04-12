package springmvc.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springmvc.annotation.Controller;
import springmvc.annotation.Qualifier;
import springmvc.annotation.RequestMapping;
import springmvc.annotation.Service;
import springmvc.controller.DemoController;
import springmvc.utils.ConfigUtils;

/**
 * 
 * http://localhost:8081/yzh-practice/demo/insert
 * 
 * 核心控制类
 * @author yuezh2   2017年4月7日 下午3:36:17
 *
 */
@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet{
	
	//将我们扫描的全限定类名放入集合类
	private List<String> packageNames = new ArrayList<>();
	
	//注解属性 对应 各层实例对象map
	private Map<String, Object> instanceMaps = new HashMap<>();
	
	//url - 方法  对应handler的map集合
	private Map<String, Method> handlerMap = new HashMap<>();
	
	/**
	 * 初始化
	 */
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
		try {
			//扫描全包
			String basePackage = ConfigUtils.getBasePackageName("D:\\workspace\\git\\yzh-practice\\src\\main\\resources\\springmvc\\springmvc.xml");
			scanBase(basePackage);
			//找到实例
			filterAndInstance();
			//运行的时候依赖注入
			springIoc();
			//通过请求url获取相应的处理类,方法连
			handlerMaps();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//通过我们的方法连接来拿到相应的处理类实例
		
		
	}
	
	
	


	/**
	 * 通过请求url获取相应的处理类,方法连
	 */
	private void handlerMaps() {
		if(instanceMaps.size()<=0){
			return;
		}
		
		//有控制层和service层的
		for(Entry<String, Object> entry : instanceMaps.entrySet()){
			if(entry.getValue().getClass().isAnnotationPresent(Controller.class)){
				Controller controllerAnnotation = (Controller)entry.getValue().getClass().getAnnotation(Controller.class);
				String baseUrl = controllerAnnotation.value();
				Method[] controllerMethods = entry.getValue().getClass().getMethods();
				for(Method controllerMethod : controllerMethods){
					//@RequestMapping注解我们才请求url
					if(controllerMethod.isAnnotationPresent(RequestMapping.class)){
						String methodUrl = ((RequestMapping)(controllerMethod.getAnnotation(RequestMapping.class))).value();
						handlerMap.put("/"+baseUrl+"/"+methodUrl, controllerMethod);
						System.out.println("/"+baseUrl+"/"+methodUrl);
					}else{
						continue;
					}
				}
			}
		}
		
		
	}





	/**
	 * 运行时依赖注入
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void springIoc() throws IllegalArgumentException, IllegalAccessException {
		if(instanceMaps.size()<=0){
			return;
		}
		
		for(Entry<String, Object> entry : instanceMaps.entrySet()){
			Field[] fields = entry.getValue().getClass().getDeclaredFields();
			for(Field field : fields){
				if(field.isAnnotationPresent(Qualifier.class)){
					//判断这个变量上是否含有注入的这个注解Qualifier
					Qualifier qualifier = (Qualifier)field.getAnnotation(Qualifier.class);
					String key = qualifier.value();
					field.setAccessible(true);
					//注入响应的实例
					field.set(entry.getValue(), instanceMaps.get(key));
				}else{
					continue;
				}
			}
		}
		
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
				packageNames.add(basePackage+"."+eachFile.getName());
			}
		}
	}
	
	//将全包名称替换成成一个路径/
	private String replacePath(String path){
		return path.replaceAll("\\.", "/");
	}
	
	
	
	/**
	 * 拦截方法请求并且找到url对应的handler
	 */
	private void filterAndInstance() throws Exception{
		//判断集合是否有实例
		if(packageNames.size()<=0){
			return ;
		}
		
		for(String className : packageNames){
			//文件格式合法性校验
			if(!className.endsWith("class")){
				continue;
			}
			
			Class ccName  = Class.forName(className.replace(".class", ""));
			if(ccName.isAnnotationPresent(Controller.class)){
				Object instance = ccName.newInstance();
				//将实例装入map key
				Controller controller = (Controller)ccName.getAnnotation(Controller.class);
				//通过注解对象拿到属性 xml : key:beanID , value :class=com.d
				String key = controller.value();
				//通过注释的key找到对应的bean的是实例
				instanceMaps.put(key, instance);
			}else if(ccName.isAnnotationPresent(Service.class)){
				Object instance = ccName.newInstance();
				//将实例装入map key
				Service service = (Service)ccName.getAnnotation(Service.class);
				//通过注解对象拿到属性 xml : key:beanID , value :class=com.d
				String key = service.value();
				//通过注释的key找到对应的bean的是实例
				instanceMaps.put(key, instance);
			}else{
				//没有注解
				continue;
			}
		}
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//拿到完整的路径
		String url = req.getRequestURI();
		String projectName = req.getContextPath();
		//请求连接 baseURL + "/" + methodURI
		String uri = url.replace(projectName, "");
		Method method = handlerMap.get(uri);
		PrintWriter outWriter = resp.getWriter();
		if(method == null){
			outWriter.write("您访问的资源没有或者检查您的访问地址是否正确!");
			return;
		}
		
		//拿到完整路径 : localhost:8080/myspring_mvc/demo/insert
		String annotationName = url.split("/")[2];
		DemoController controller = (DemoController)instanceMaps.get(annotationName);
		
		try {
			method.invoke(controller, new Object[]{req , resp,null});
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	
	
	
}
