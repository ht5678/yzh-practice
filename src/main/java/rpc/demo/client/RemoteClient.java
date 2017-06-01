package rpc.demo.client;

import com.google.common.util.concurrent.SettableFuture;

import rpc.demo.common.ProviderInfo;
import rpc.demo.server.RemoteRequest;
import rpc.demo.server.RemoteResponse;

/**
 * 
 * @author sdwhy
 *
 */
public class RemoteClient {

	private ProviderInfo providerInfo;
	
	
	public RemoteClient(ProviderInfo providerInfo){
		this.providerInfo = providerInfo;
	}
	
	
	public RemoteResponse send(RemoteRequest request)throws Exception{
		final SettableFuture<RemoteResponse> future = SettableFuture.create();
		
	}
	
}
