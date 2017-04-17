package springmvc.handler;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author yuezh2   2017年4月12日 下午4:13:59
 *
 */
public interface HandlerAdapter {

	
	
	public Object[] handle(HttpServletRequest request , HttpServletResponse response , Method method
			,Map<String, Object> beans);
	
	
}
