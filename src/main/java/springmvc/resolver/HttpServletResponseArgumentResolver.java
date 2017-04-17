package springmvc.resolver;

import java.lang.reflect.Method;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springmvc.annotation.Service;


/**
 * 
 * @author yuezh2   2017年4月17日 下午4:43:49
 *
 */
@Service("httpServletResponseArgumentResolver")
public class HttpServletResponseArgumentResolver implements ArgumentResolver {

	
	
	@Override
	public boolean supprt(Class<?> type, int index, Method method) {
		return ServletResponse.class.isAssignableFrom(type);
	}

	
	
	@Override
	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
			Method method) {
		return response;
	}

}
