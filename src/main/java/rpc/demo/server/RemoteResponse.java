package rpc.demo.server;

import java.io.Serializable;

/**
 * 
 * @author sdwhy
 *
 */
public class RemoteResponse implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2063069330018023076L;

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

	
	
	@Override
	public String toString() {
		return "RemoteResponse [requestId=" + requestId + ", responseValue=" + responseValue + "]";
	}

	
	
}
