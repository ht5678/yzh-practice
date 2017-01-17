package proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 
 * @author yuezh2   2017年1月16日 下午6:55:06
 *
 */
public class JdkInvocationHandler  implements InvocationHandler {  
    //目标对象  
    private Object target;  
      
    public JdkInvocationHandler(Object target){  
        this.target = target;  
    }  
      
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {  
        //执行相应的目标方法  
        Object rs = method.invoke(target,args);  
        return rs;  
    }  
  
}  
