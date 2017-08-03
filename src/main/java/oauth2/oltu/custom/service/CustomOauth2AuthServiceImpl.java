package oauth2.oltu.custom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oauth2.oltu.custom.dao.CustomOauth2AuthDao;
import oauth2.oltu.custom.model.AccessToken;
import oauth2.oltu.custom.model.ClientDetails;
import oauth2.oltu.custom.model.OauthCode;
import oauth2.oltu.simple.model.ClientModel;

/**
 * 
 * @author yuezh2   2017年7月31日 下午5:53:10
 *
 */
@Service
public class CustomOauth2AuthServiceImpl implements CustomOauth2AuthService {

	
	@Autowired
	private CustomOauth2AuthDao dao;
	
	
	
	/**
	 * 查询token
	 * @param token
	 * @param clientId
	 * @return
	 */
	public AccessToken loadAccessTokenByRefreshToken(String token  , String clientId){
		return dao.loadAccessTokenByRefreshToken(token, clientId);
	}
	
	
	/**
	 * 查询code
	 * @param code
	 * @param clientId
	 * @return
	 */
	public OauthCode loadOauthCode(String code , String clientId){
		return dao.loadOauthCode(code, clientId);
	}
	
	
	
	/**
	 * 
	 * @param clientId
	 * @return
	 */
	public ClientDetails loadClientDetails(String clientId){
		return dao.loadClientDetails(clientId);
	}
	
	
	
	
	/**
	 * 通过设备id查询
	 * @param clientId
	 * @return
	 */
	public ClientModel queryByClientId(String clientId){
		return dao.queryByClientId(clientId);
	}
	
}
