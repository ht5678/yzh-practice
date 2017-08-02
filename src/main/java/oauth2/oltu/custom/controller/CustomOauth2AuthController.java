package oauth2.oltu.custom.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import oauth2.oltu.simple.common.Constants;
import oauth2.oltu.simple.model.ClientModel;
import oauth2.oltu.simple.service.SimpleOauth2AuthService;

/**
 * 
 * @author yuezh2   2017年7月31日 下午4:23:43
 *
 */
@Controller
@RequestMapping(value="simple/oauth")
public class CustomOauth2AuthController {

	@Autowired
	private OAuthIssuer oAuthIssuer;
	
	@Autowired
	private SimpleOauth2AuthService service;
	
	
	
	
	/**
	 * 
	 * 请求url:
	 * 		http://localhost:8081/yzh-practice/simple/oauth/token?client_id=test&client_secret=test&grant_type=authorization_code&code=ac0bd18863b07adfb518cc6e6dfcfcab&redirect_uri=http://localhost:8081/yzh-practice/oauth/authorize?response_type=code&scope=read%20write&client_id=test&redirect_uri=http%3A%2F%2Flocalhost%3A7777%2Fspring-oauth-client%2Fauthorization_code_callback&state=09876999
	 * 
	 * 返回token
	 * @return
	 */
	@RequestMapping(value="token")
	public void token(Model model , HttpServletRequest request , HttpServletResponse response) {
		OAuthTokenRequest oauthRequest = null;
		 
		    try {
		           oauthRequest = new OAuthTokenRequest(request);
		        
//		           validateClient(oauthRequest);
		 
		           String authzCode = oauthRequest.getCode();
		           
		           //校验code
		           
		           
		 
		           // some code , 这些应该都要存到数据库的
		           String accessToken = oAuthIssuer.accessToken();
		           String refreshToken = oAuthIssuer.refreshToken();
		 
		           // some code
		            OAuthResponse r = OAuthASResponse
		                .tokenResponse(HttpServletResponse.SC_OK)
		                .setAccessToken(accessToken)
		                .setExpiresIn("3600")
		                .setRefreshToken(refreshToken)
		                .buildJSONMessage();
		 
		        response.setStatus(r.getResponseStatus());
		        PrintWriter pw = response.getWriter();
		        pw.print(r.getBody());
		        pw.flush();
		        pw.close();
		         //if something goes wrong
		    } catch(Exception ex) {
		    	
		    	if(ex instanceof OAuthProblemException){
		    		try{
				         final OAuthResponse resp = OAuthASResponse
				             .errorResponse(HttpServletResponse.SC_FOUND)
				             .error((OAuthProblemException)ex)
				             .location(Constants.ERROR_URL)
				             .buildQueryMessage();
				 
				         response.sendRedirect(resp.getLocationUri());
		    		}catch(Exception e){
		    			//nothing
		    			e.printStackTrace();
		    		}
		    	}
		    	
		    	ex.printStackTrace();
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
	public void authorize(Model model , HttpServletRequest request , HttpServletResponse response) {
		try {
	         //dynamically recognize an OAuth profile based on request characteristic (params,
	         // method, content type etc.), perform validation
	         OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
	         
	         //校验 , 也可以做成在数据库存储 , 存储的时候统一校验 , 就不用每次请求都校验了
//	         validateRedirectionURI(oauthRequest)
	         
	       //clientid : 客户端标识
			String clientId = oauthRequest.getClientId();
			//响应类型
			String responseType = oauthRequest.getResponseType();
			
			//获取重定向url
			ClientModel cm = service.queryByClientId(clientId);
			
			if(StringUtils.isEmpty(cm.getRedirectUri())){
				throw new Exception("not validate client");
				//error
			}
				
	 
	         //build OAuth response
	         OAuthResponse resp = OAuthASResponse
	             .authorizationResponse(request , HttpServletResponse.SC_OK)
	             .setCode(oAuthIssuer.authorizationCode())
	             .location(cm.getRedirectUri())
	             .buildQueryMessage();
	 
	         response.sendRedirect(resp.getLocationUri());
	 
	         //if something goes wrong
	    } catch(Exception ex) {
	    	
	    	if(ex instanceof OAuthProblemException){
	    		try{
			         final OAuthResponse resp = OAuthASResponse
			             .errorResponse(HttpServletResponse.SC_FOUND)
			             .error((OAuthProblemException)ex)
			             .location(Constants.ERROR_URL)
			             .buildQueryMessage();
			 
			         response.sendRedirect(resp.getLocationUri());
	    		}catch(Exception e){
	    			//nothing
	    			e.printStackTrace();
	    		}
	    	}
	    	
	    	ex.printStackTrace();
	    }
	}
	
	
}
