package oauth2.oltu.simple.service;

import oauth2.oltu.simple.model.ClientModel;

/**
 * 
 * @author yuezh2   2017年7月31日 下午5:52:52
 *
 */
public interface SimpleOauth2AuthService {
	
	/**
	 * 通过设备id查询
	 * @param clientId
	 * @return
	 */
	public ClientModel queryByClientId(String clientId);

}
