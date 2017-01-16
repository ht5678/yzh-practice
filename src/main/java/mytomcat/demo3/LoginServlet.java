package mytomcat.demo3;



/**
 * 
 * @author yuezh2   2017年1月16日 下午1:58:24
 *
 */
public class LoginServlet extends HttpServlet {

	
	
	@Override
	public void service(HttpRequest request, HttpResponse response) throws Exception {
		String username = request.getParams().get("username").toString();
		String password = request.getParams().get("password").toString();

		if("admin".equals(username) && "admin".equals(password)){
			response.writeHtmlFile("/src/main/webapp/pages/login/welcome.html");
		}else{
			response.writeHtmlFile("/src/main/webapp/pages/login/error.html");
		}
		
	}

}
