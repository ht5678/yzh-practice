package proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import proxy.Foo;

/**
 * 
 * @author yuezh2   2017年1月16日 下午6:43:02
 *
 */
public class JdkProxyTest {

	
	public static void main(String[] args) {
		
		//生成$Proxy0的class文件  
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true"); 
		
        final OrderServiceImpl osi = new OrderServiceImpl();
//        System.out.println(osi);
        
//		Class clazzProxy3 = Proxy.getProxyClass(OrderService.class.getClassLoader(), new Class[]{OrderService.class});
		OrderService f2 = (OrderService) Proxy.newProxyInstance(OrderService.class.getClassLoader()
				, new Class[]{OrderService.class}, new JdkInvocationHandler(osi));
		
		f2.getOrderStatus("ddd");
		System.out.println(f2);
		
	}
	
	
}
