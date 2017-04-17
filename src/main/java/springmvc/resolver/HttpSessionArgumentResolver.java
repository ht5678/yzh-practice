package springmvc.resolver;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import springmvc.annotation.Service;



/**
 * 
 * @author yuezh2   2017年4月17日 下午5:01:53
 *
 */
@Service("httpSessionArgumentResolver")
public class HttpSessionArgumentResolver implements ArgumentResolver {

	
	@Override
	public boolean supprt(Class<?> type, int index, Method method) {
		return HttpSession.class.isAssignableFrom(type);
	}

	
	
	@Override
	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
			Method method) {
		return request.getSession();
	}

}
