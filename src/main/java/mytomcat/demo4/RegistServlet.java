package mytomcat.demo4;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author yuezh2   2017年1月16日 下午1:58:24
 *
 */
public class RegistServlet extends HttpServlet {

	
	
	@Override
	public void service(HttpRequest request, HttpResponse response) throws Exception {
		String username = request.getParams().get("username").toString();
		String password = request.getParams().get("password").toString();

		if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)){
			response.writeHtmlFile("/src/main/webapp/pages/login/success.html");
		}else{
			response.writeHtmlFile("/src/main/webapp/pages/login/error.html");
		}
		
	}

}
