package rpc.demo.client;

import rpc.demo.common.ProviderInfo;

/**
 * 
 * @author yuezh2   2017年6月7日 下午5:25:01
 *
 */
public class ServiceProviderManager {
	
	
	
	
	public static ProviderInfo getProvider(String serviceName){
		
		ProviderInfo pi = new ProviderInfo("127.0.0.1",8081);
		
		return pi;
	}
	

}
