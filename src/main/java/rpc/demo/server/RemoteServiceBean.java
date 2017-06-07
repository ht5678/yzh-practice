package rpc.demo.server;

/**
 * 
 * @author sdwhy
 *
 */
public class RemoteServiceBean {

	private String serviceName;
	
	private Object serviceImpl;
	
	
	
	public void init(){
		RemoteServer.addServices(serviceName, serviceImpl);
	}

	
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Object getServiceImpl() {
		return serviceImpl;
	}

	public void setServiceImpl(Object serviceImpl) {
		this.serviceImpl = serviceImpl;
	}
	
	
}
