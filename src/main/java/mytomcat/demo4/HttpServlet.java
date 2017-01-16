package mytomcat.demo4;



/**
 * 
 * @author yuezh2   2017年1月16日 下午1:56:55
 *
 */
public abstract  class HttpServlet {

	
	public abstract void service(HttpRequest request , HttpResponse response) throws Exception;
	
}
