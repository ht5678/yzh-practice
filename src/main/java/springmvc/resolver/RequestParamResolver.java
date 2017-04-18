package springmvc.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springmvc.annotation.RequestParam;
import springmvc.annotation.Service;

/**
 * 
 * @author yuezh2   2017年4月17日 下午6:14:07
 *
 */
@Service("requestParamResolver")
public class RequestParamResolver implements ArgumentResolver{

	
	@Override
	public boolean supprt(Class<?> type, int index, Method method) {
		Annotation[][] annos = method.getParameterAnnotations();
		
		Annotation[] paramAns = annos[index];
		
		for(Annotation paramAn : paramAns){
			if(RequestParam.class.isAssignableFrom(paramAn.getClass())){
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Object argumentResolver(HttpServletRequest request, HttpServletResponse response, Class<?> type, int index,
			Method method) {
		
		Annotation[][] annos = method.getParameterAnnotations();
		
		Annotation[] paramAns = annos[index];
		
		for(Annotation paramAn : paramAns){
			if(RequestParam.class.isAssignableFrom(paramAn.getClass())){
				RequestParam rp = (RequestParam)paramAn;
				
				String value = rp.value();
				return request.getParameter(value);
			}
		}
		
		return null;
	}

	
	
	
	
}
