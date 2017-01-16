package mytomcat.demo4;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 配置初始化
 * 
 * @author yuezh2   2017年1月16日 下午3:54:41
 *
 */
public class ConfigInitializer {

	
	private static Map<String, String> urlMappings = new HashMap<String, String>();
	
	private static Map<String, String> tmp = new HashMap<String, String>();
	
	
	/**
	 * 初始化配置文件
	 */
	public static void init(){
		try{
			SAXReader reader = new SAXReader();  
	        Document document = reader.read(new File("src/main/webapp/pages/login/web.xml"));
	        Element root = document.getRootElement();  
	        listNodes(root); 
	        
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	//遍历当前节点下的所有节点  
    private static void listNodes(Element node){  
    	List<Element> servlets = node.elements("servlet");
    	for(Element servlet : servlets){
	        Iterator<Element> iterator = servlet.elementIterator();
	        String servletName = "";
	        String servletClass = "";
	        		
	        while(iterator.hasNext()){  
	            Element e = iterator.next();
	          //如果当前节点内容不为空，则输出  
	            if(!(e.getTextTrim().equals(""))){ 
	            	if("servlet-name".equals(e.getName())){
	            		servletName = e.getText();
	            	}else if("servlet-class".equals(e.getName())){
	            		servletClass = e.getText();
	            	}
//	                 System.out.println( e.getName() + "：" + e.getText());    
	            } 
	        }  
	        
	        tmp.put(servletName, servletClass);
    	}
    	
    	
    	List<Element> smappings = node.elements("servlet-mapping");
    	for(Element servlet : smappings){
	        Iterator<Element> iterator = servlet.elementIterator(); 
	        String servletName = "";
	        String urlPattern = "";
	        while(iterator.hasNext()){  
	            Element e = iterator.next();  
	          //如果当前节点内容不为空，则输出  
	            if(!(e.getTextTrim().equals(""))){ 
	            	if("servlet-name".equals(e.getName())){
	            		servletName = e.getText();
	            	}else if("url-pattern".equals(e.getName())){
	            		urlPattern = e.getText();
	            	}
//	                 System.out.println( e.getName() + "：" + e.getText());    
	            } 
	        }  
	        
	        String servletClass = tmp.get(servletName);
	        urlMappings.put(urlPattern, servletClass);
    	}
    }
	
	
	
	
	
	
//	//遍历当前节点下的所有节点  
//    private static void listNodes(Element node){  
//        System.out.println("当前节点的名称：" + node.getName());  
//        //首先获取当前节点的所有属性节点  
//        List<Attribute> list = node.attributes();  
//        //遍历属性节点  
//        for(Attribute attribute : list){  
//            System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  
//        }  
//        //如果当前节点内容不为空，则输出  
//        if(!(node.getTextTrim().equals(""))){  
//             System.out.println( node.getName() + "：" + node.getText());    
//        }  
//        //同时迭代当前节点下面的所有子节点  
//        //使用递归  
//        Iterator<Element> iterator = node.elementIterator();  
//        while(iterator.hasNext()){  
//            Element e = iterator.next();  
//            listNodes(e);  
//        }  
//    }
    
    
    
    public static void main(String[] args) {
		ConfigInitializer.init();
		System.out.println(urlMappings);
	}


	public static Map<String, String> getUrlMappings() {
		return urlMappings;
	}

	public static void setUrlMappings(Map<String, String> urlMappings) {
		ConfigInitializer.urlMappings = urlMappings;
	}
	
}
