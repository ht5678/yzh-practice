package mytomcat.demo4;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.FileUtils;

/**
 * 响应
 * @author yuezh2   2017年1月10日 上午1:39:12
 *
 */
public class HttpResponse {

	private OutputStream os = null;
	private static final String ENTER = "\r\n";
	private static final String SPACE = " ";
	
	
	
	public HttpResponse(OutputStream os){
		this.os = os;
	}
	
	
	/**
	 * 响应动态请求输出
	 * @param content
	 * @throws IOException
	 */
	public void writeContent(String content)throws IOException{
		StringBuilder sb = new StringBuilder();
		/*通用头域begin*/
		sb.append("HTTP/1.1").append(SPACE).append("200").append(SPACE).append("OK").append(ENTER);
		sb.append("Server:myServer").append(SPACE).append("0.0.1v").append(ENTER);
		sb.append("Date:Sat,"+SPACE).append(new Date()).append(ENTER);
		sb.append("Content-Type:text/html;charset=UTF-8").append(ENTER);
		sb.append("Content-Length:").append(content.getBytes().length).append(ENTER);
		/*通用头域end*/
		sb.append(ENTER);//空一行
		sb.append(content);//正文部分
		
		os.write(sb.toString().getBytes());
		os.flush();
		os.close();
	}
	
	
	
	/**
	 * 读取静态文件
	 * @param path
	 * @throws IOException
	 */
	public void writeHtmlFile(String path)throws IOException{
		//读取静态文件
		String htmlContent = FileUtils.readFileToString(new File(path.substring(1)));
		writeContent(htmlContent);
	}
	
	
	
	
}
