package oauth2.oltu.custom.dao;

import oauth2.oltu.custom.model.AccessToken;
import oauth2.oltu.custom.model.ClientDetails;
import oauth2.oltu.custom.model.OauthCode;
import oauth2.oltu.simple.model.ClientModel;

/**
 * 
 * @author yuezh2   2017年7月31日 下午5:51:50
 *
 */
public interface CustomOauth2AuthDao {
	
	/**
	 * 新增accesstoken
	 * @param accessToken
	 * @return
	 */
	public int saveAccessToken(final AccessToken accessToken);
	
	
	/**
	 * 删除accesstoken
	 * @param accessToken
	 * @return
	 */
	public int deleteAccessToken(final AccessToken accessToken);
	
	
	
	/**
	 * 查询accesstoken
	 * @param clientId
	 * @param username
	 * @param authenticationId
	 * @return
	 */
	public AccessToken findAccessToken(String clientId, String username, String authenticationId);
	
	
	/**
	 * 删除code
	 * @param code
	 * @return
	 */
	public int removeOauthCode(String code);
	
	
	/**
	 * 生成oauthcode
	 * @param oauthCode
	 * @return
	 */
	public int saveOauthCode(final OauthCode oauthCode);
	
	
	/**
	 * 删除oauthcode
	 * @param oauthCode
	 * @return
	 */
	public int deleteOauthCode(final OauthCode oauthCode);
	
	
	/**
	 * 通过用户名和clientid查找code
	 * @param username
	 * @param clientId
	 * @return
	 */
	public OauthCode findOauthCodeByUsernameClientId(String username , String clientId);
	
	
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
	public ClientDetails loadClientDetails(String clientId) ;
	
	
	/**
	 * 通过设备id查询
	 * @param clientId
	 * @return
	 */
	public ClientModel queryByClientId(String clientId);

}
