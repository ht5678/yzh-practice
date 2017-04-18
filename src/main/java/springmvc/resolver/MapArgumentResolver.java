package springmvc.resolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springmvc.annotation.Service;


/**
 * 
 * @author yuezh2   2017年4月17日 下午6:05:07
 *
 */
@Service("mapArgumentResolver")
public class MapArgumentResolver implements ArgumentResolver {

	@Override
	public boolean supprt(Class<?> type, int index, Method method) {
		return Map.class.isAssignableFrom(type);
	}

	@Override
	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
			Method method) {
		
		Map<String, String[]> params = request.getParameterMap();
		
		Map<String, String> result = new HashMap<>();
		
		for(Map.Entry<String, String[]> entry : params.entrySet()){
			result.put(entry.getKey(), entry.getValue()[0]);
		}
		
		return result;
	}

}
