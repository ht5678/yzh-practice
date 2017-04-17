package springmvc.resolver;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author yuezh2   2017年4月12日 下午5:05:21
 *
 */
public interface ArgumentResolver {

	
	/**
	 * 是否支持这个参数的解析
	 * @param type		参数类型
	 * @param index	参数位置
	 * @param method	参数所在方法
	 * @return
	 */
	public boolean supprt(Class<?> type , int index , Method method);
	
	
	/**
	 * 
	 * @param type
	 * @param index
	 * @param method
	 * @return
	 */
	public Object argumentResolver(HttpServletRequest request , HttpServletResponse response , Class<?> type, int index, Method method);
	
}
