package rpc.demo.client;

import com.google.common.reflect.Reflection;

/**
 * 
 * @author sdwhy
 *
 */
public class ServiceProxyBeanFactory {

	public static Object getService(String serviceName)throws ClassNotFoundException{
		Class<?> serviceClass = Class.forName(serviceName);	//serviceName接口
		return Reflection.newProxy(serviceClass, new ServiceProxy(serviceName));		//google guava的动态实现类
	}
	
}
