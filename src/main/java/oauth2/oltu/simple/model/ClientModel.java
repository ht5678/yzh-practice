package oauth2.oltu.simple.model;



/**
 * 
 * @author yuezh2   2017年8月1日 下午3:59:12
 *
 */
public class ClientModel {

	/* 设备id */
	private String clientId;
	/* 设备名 */
	private String clientName;
	/* 重定向url */
	private String redirectUri;

	
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	
	
}
