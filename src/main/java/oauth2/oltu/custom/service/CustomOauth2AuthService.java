package oauth2.oltu.custom.service;

import oauth2.oltu.custom.model.AccessToken;
import oauth2.oltu.custom.model.ClientDetails;
import oauth2.oltu.custom.model.OauthCode;
import oauth2.oltu.simple.model.ClientModel;

/**
 * 
 * @author yuezh2   2017年7月31日 下午5:52:52
 *
 */
public interface CustomOauth2AuthService {
	
	/**
	 * 查询token
	 * @param token
	 * @param clientId
	 * @return
	 */
	public AccessToken loadAccessTokenByRefreshToken(String token  , String clientId);
	
	/**
	 * 查询code
	 * @param code
	 * @param clientId
	 * @return
	 */
	public OauthCode loadOauthCode(String code , String clientId);
	
	/**
	 * 
	 * @param clientId
	 * @return
	 */
	public ClientDetails loadClientDetails(String clientId);
	
	/**
	 * 通过设备id查询
	 * @param clientId
	 * @return
	 */
	public ClientModel queryByClientId(String clientId);

}
