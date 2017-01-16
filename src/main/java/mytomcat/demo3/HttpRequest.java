package mytomcat.demo3;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author yuezh2   2017年1月10日 上午1:27:52
 *
 */
public class HttpRequest {

	private String uri;
	
	private String method;
	
	private Map<String, Object> params = new HashMap<>();
	
	
	/**
	 * 获取请求信息
	 * @param is
	 * @throws Exception
	 */
	public HttpRequest(InputStream is ) throws IOException{
		byte[] bytes = new byte[1024];
		int len = is.read(bytes);
		if(len>0){
			String msg = new String(bytes,0,len);
			method = msg.split(" ")[0];
			uri = msg.split(" ")[1];
			
			//动态请求参数处理
			//get请求参数
			if("get".equalsIgnoreCase(method) && uri.indexOf("?")!=-1){
				String paramStr = uri.substring(uri.indexOf("?")+1);
				if(StringUtils.isNotEmpty(paramStr)){
					String[] paramArr = paramStr.split("&");
					for(String param : paramArr){
						String[] kvs = param.split("=");
						params.put(kvs[0], kvs[1]);
					}
				}
				
			}
			
			//post请求参数
			if("post".equalsIgnoreCase(method)){
				String paramStr = msg.substring(msg.indexOf("\r\n\r\n")+4);
				if(StringUtils.isNotEmpty(paramStr)){
					String[] paramArr = paramStr.split("&");
					for(String param : paramArr){
						String[] kvs = param.split("=");
						params.put(kvs[0], kvs[1]);
					}
				}
			}
			
			System.out.println(uri);
		}else{
			System.out.println("bad request");
		}
	}




	public String getUri() {
		return uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	
	
}
