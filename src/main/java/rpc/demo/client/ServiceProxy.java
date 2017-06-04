package rpc.demo.client;

import java.lang.reflect.Method;
import java.util.UUID;

import com.google.common.reflect.AbstractInvocationHandler;

import rpc.demo.common.ProviderInfo;
import rpc.demo.server.RemoteRequest;
import rpc.demo.server.RemoteResponse;

/**
 * 反射代理服务
 * @author sdwhy
 *
 */
public class ServiceProxy extends AbstractInvocationHandler{

	private String serviceName;
	
	
	public ServiceProxy(String serviceName){
		this.serviceName = serviceName;
	}


	
	/**
	 * 通过动态代理的类名 , 方法名, 参数类型, 参数值发送到服务器
	 * 
	 */
	@Override
	protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
		
		ProviderInfo provider = ServiceProviderManager.getProvider(serviceName);
		
		//构造request请求
		RemoteRequest request = new RemoteRequest();
		request.setRequestId(UUID.randomUUID().toString());
		request.setServiceName(serviceName);
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setArguments(args);
		
		RemoteClient client = new RemoteClient(provider);	//构造远程客户端对象
		RemoteResponse response = client.send(request);		//客户端发送远程请求到服务器
		
		return response.getResponseValue();		//异步获取服务端响应数据
	}
	
	
}
