package oauth2.oltu.custom.service;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

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
	 * 获取token
	 * @param clientDetails
	 * @param code
	 * @return
	 * @throws OAuthSystemException
	 */
	public AccessToken retrieveAuthorizationCodeAccessToken(ClientDetails clientDetails, String code) throws OAuthSystemException;
	
	
	/**
	 * 删除code
	 * @param code
	 * @return
	 */
	public boolean removeOauthCode(String code);
	
	
	/**
	 * 生成code
	 * @param client
	 * @return
	 */
	public String retrieveAuthCode(ClientDetails clientDetails);
	
	
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
