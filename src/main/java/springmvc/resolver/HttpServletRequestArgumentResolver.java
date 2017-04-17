package springmvc.resolver;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springmvc.annotation.Service;


/**
 * 
 * @author yuezh2   2017年4月13日 下午4:00:55
 *
 */
@Service("httpServletRequestArgumentResolver")
public class HttpServletRequestArgumentResolver implements ArgumentResolver {

	@Override
	public boolean supprt(Class<?> type, int index, Method method) {
		return ServletRequest.class.isAssignableFrom(type);
	}

	
	
	@Override
	public Object argumentResolver(HttpServletRequest request , HttpServletResponse response , Class<?> type, int index, Method method) {
		return request;
	}

}
