package rpc.demo.server;


/**
 * 
 * @author sdwhy
 *
 */
public class RemoteResponse {

	private String requestId;
	
	private Object responseValue;
	
	

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Object getResponseValue() {
		return responseValue;
	}

	public void setResponseValue(Object responseValue) {
		this.responseValue = responseValue;
	}

	
	
}
