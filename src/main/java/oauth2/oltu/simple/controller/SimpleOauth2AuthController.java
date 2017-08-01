package oauth2.oltu.simple.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
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
public class SimpleOauth2AuthController {

	@Autowired
	private OAuthIssuer oAuthIssuer;
	
	@Autowired
	private SimpleOauth2AuthService service;
	
	
	/**
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
