package oauth2.oltu.custom.service;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	@Autowired
	private OAuthIssuer oAuthIssuer;
	@Autowired
	private AuthenticationIdGenerator authenticationIdGenerator;
	/** LOGGER */
	private static final Logger LOG = LoggerFactory.getLogger(CustomOauth2AuthServiceImpl.class);
	
	
	
	
	/**
	 * 获取token
	 * @param clientDetails
	 * @param code
	 * @return
	 * @throws OAuthSystemException
	 */
	public AccessToken retrieveAuthorizationCodeAccessToken(ClientDetails clientDetails, String code) throws OAuthSystemException {
        final OauthCode oauthCode = loadOauthCode(code, clientDetails);
        final String username = oauthCode.username();
        final String clientId = clientDetails.getClientId();

        final String authenticationId = authenticationIdGenerator.generate(clientId, username, null);

        AccessToken accessToken = dao.findAccessToken(clientId, username, authenticationId);
        if (accessToken != null) {
            LOG.debug("Delete existed AccessToken: {}", accessToken);
            dao.deleteAccessToken(accessToken);
        }
        accessToken = createAndSaveAccessToken(clientDetails, clientDetails.supportRefreshToken(), username, authenticationId);
        LOG.debug("Create a new AccessToken: {}", accessToken);

        return accessToken;
    }
	
	
	
	
	
	private AccessToken createAndSaveAccessToken(ClientDetails clientDetails, boolean includeRefreshToken, String username, String authenticationId) throws OAuthSystemException {
        AccessToken accessToken = new AccessToken()
                .clientId(clientDetails.getClientId())
                .username(username)
                .tokenId(oAuthIssuer.accessToken())
                .authenticationId(authenticationId)
                .updateByClientDetails(clientDetails);

        if (includeRefreshToken) {
            accessToken.refreshToken(oAuthIssuer.refreshToken());
        }

        this.dao.saveAccessToken(accessToken);
        LOG.debug("Save AccessToken: {}", accessToken);
        return accessToken;
    }
	
	
	
	
	
	public OauthCode loadOauthCode(String code, ClientDetails clientDetails) {
        final String clientId = clientDetails.getClientId();
        return dao.loadOauthCode(code, clientId);
    }
	
	
	
	
	/**
	 * 删除code
	 * @param code
	 * @return
	 */
	public boolean removeOauthCode(String code){
		return dao.removeOauthCode(code)>0;
	}
	
	
	
	
	/**
	 * 生成code
	 * @param client
	 * @return
	 */
	public String retrieveAuthCode(ClientDetails clientDetails){
		final String clientId = clientDetails.getClientId();
        final String username = currentUsername();

        OauthCode oauthCode = null ;
        try{
	        oauthCode = dao.findOauthCodeByUsernameClientId(username, clientId);
	        if (oauthCode != null) {
	            //Always delete exist
	            LOG.debug("OauthCode ({}) is existed, remove it and create a new one", oauthCode);
	            dao.deleteOauthCode(oauthCode);
	        }
	        //create a new one
	        oauthCode = createOauthCode(clientDetails);
        }catch(Exception e){
    		LOG.error(String.format("the error occured:%s", e.getMessage()), e);
    	}
        return oauthCode.code();
	}
	
	
	
	private OauthCode createOauthCode(ClientDetails clientDetails) throws OAuthSystemException {
        OauthCode oauthCode;
        final String authCode = oAuthIssuer.authorizationCode();

        LOG.debug("Save authorizationCode '{}' of ClientDetails '{}'", authCode, clientDetails);
        oauthCode = this.saveAuthorizationCode(authCode, clientDetails);
        return oauthCode;
    }
	
	
	public OauthCode saveAuthorizationCode(String authCode, ClientDetails clientDetails) {
        final String username = currentUsername();
        OauthCode oauthCode = new OauthCode()
                .code(authCode).username(username)
                .clientId(clientDetails.getClientId());

        dao.saveOauthCode(oauthCode);
        LOG.debug("Save OauthCode: {}", oauthCode);
        return oauthCode;
    }
	
	
	
	
	private String currentUsername() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
	
	
	
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
