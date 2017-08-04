package oauth2.oltu.custom.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import oauth2.oltu.custom.common.oauth.CodeAuthorizeHandler;
import oauth2.oltu.custom.common.oauth.OAuthAuthxRequest;
import oauth2.oltu.custom.common.oauth.OAuthTokenHandleDispatcher;
import oauth2.oltu.custom.common.oauth.OAuthTokenxRequest;
import oauth2.oltu.custom.common.utils.WebUtils;
import oauth2.oltu.custom.service.CustomOauth2AuthService;

/**
 * 
 * @author yuezh2   2017年7月31日 下午4:23:43
 *
 */
@Controller
@RequestMapping(value="custom/oauth")
public class CustomOauth2AuthController {

	private static final Logger LOG = LoggerFactory.getLogger(CustomOauth2AuthController.class);
	
	@Autowired
	private OAuthIssuer oAuthIssuer;
	
	@Autowired
	private CustomOauth2AuthService service;
	
	
	
	
	/**
	 * 
	 * 请求url:
	 * 		http://localhost:8081/yzh-practice/simple/oauth/token?client_id=test&client_secret=test&grant_type=authorization_code&code=ac0bd18863b07adfb518cc6e6dfcfcab&redirect_uri=http://localhost:8081/yzh-practice/oauth/authorize?response_type=code&scope=read%20write&client_id=test&redirect_uri=http%3A%2F%2Flocalhost%3A7777%2Fspring-oauth-client%2Fauthorization_code_callback&state=09876999
	 * 
	 * 返回token
	 * @return
	 */
	@RequestMapping(value="token")
	public void token(Model model , HttpServletRequest request , HttpServletResponse response) throws OAuthSystemException {
        try {
            OAuthTokenxRequest tokenRequest = new OAuthTokenxRequest(request);

            OAuthTokenHandleDispatcher tokenHandleDispatcher = new OAuthTokenHandleDispatcher(tokenRequest, response);
            tokenHandleDispatcher.dispatch();

        } catch (OAuthProblemException e) {
            //exception
            OAuthResponse oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(e.getRedirectUri())
                    .error(e)
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
        }
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * 请求url:
	 * 		localhost:8081/yzh-practice/simple/oauth/authorize?response_type=code&scope=read write&client_id=test&state=09876999
	 * 
	 * 返回code
	 * @return
	 */
	@RequestMapping(value="authorize")
	public void authorize(Model model , HttpServletRequest request , HttpServletResponse response) throws OAuthSystemException, ServletException, IOException{
		try {
            OAuthAuthxRequest oauthRequest = new OAuthAuthxRequest(request);


            if (oauthRequest.isCode()) {
                CodeAuthorizeHandler codeAuthorizeHandler = new CodeAuthorizeHandler(oauthRequest, response);
                LOG.debug("Go to  response_type = 'code' handler: {}", codeAuthorizeHandler);
                codeAuthorizeHandler.handle();

            } else {
                unsupportResponseType(oauthRequest, response);
            }

        } catch (OAuthProblemException e) {
            //exception
            OAuthResponse oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(e.getRedirectUri())
                    .error(e)
                    .buildJSONMessage();
            WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
        }
	}
	
	
	
	
	
	private void unsupportResponseType(OAuthAuthxRequest oauthRequest, HttpServletResponse response) throws OAuthSystemException {
        final String responseType = oauthRequest.getResponseType();
        LOG.debug("Unsupport response_type '{}' by client_id '{}'", responseType, oauthRequest.getClientId());

        OAuthResponse oAuthResponse = OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE)
                .setErrorDescription("Unsupport response_type '" + responseType + "'")
                .buildJSONMessage();
        WebUtils.writeOAuthJsonResponse(response, oAuthResponse);
    }
	
}
