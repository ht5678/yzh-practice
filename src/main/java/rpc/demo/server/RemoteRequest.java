package rpc.demo.server;

import java.io.Serializable;

/**
 * 
 * @author sdwhy
 *
 */
public class RemoteRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7348112206208175052L;
	//接口类名
	private String serviceName;
	//方法
	private String methodName;
	//参数类型
	private Class<?>[] parameterTypes;
	//参数
	private Object[] arguments;
	//请求id
	private String requestId;
	
	
	

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}

	
}
