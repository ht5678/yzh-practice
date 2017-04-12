package springmvc.utils;

import java.io.File;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author yuezh2   2017年4月11日 下午6:14:49
 *
 */
public class ConfigUtils {

	
	
	public static String getBasePackageName(String path)throws Exception{
		SAXReader reader = new SAXReader();
		File file = new File(path);
		Document document = reader.read(file);
		//拿到根元素
		Element rootElement = document.getRootElement();
		List<Element> childElements = rootElement.elements();
		Element element = childElements.get(0);
		Attribute attribute = element.attribute("base-package");
		return attribute.getText();
	}
	
	
	
}
