package mytomcat.demo2;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author yuezh2   2017年1月10日 上午1:27:52
 *
 */
public class Request {

	private String uri;
	
	
	
	/**
	 * 获取请求信息
	 * @param is
	 * @throws Exception
	 */
	public Request(InputStream is ) throws IOException{
		byte[] bytes = new byte[1024];
		int len = is.read(bytes);
		if(len>0){
			String msg = new String(bytes,0,len);
			uri = msg.split(" ")[1];
			System.out.println(uri);
		}else{
			System.out.println("bad request");
		}
	}




	public String getUri() {
		return uri;
	}

	
	
}
