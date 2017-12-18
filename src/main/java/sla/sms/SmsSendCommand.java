package sla.sms;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * 
 * @author yuezh2   2017年9月4日 下午2:22:30
 *
 */
public class SmsSendCommand extends HystrixCommand<String>{
	
	
	protected SmsSendCommand(){
		super(HystrixCommandGroupKey.Factory.asKey("smsGroup"));
	}
	
	

	@Override
	protected String run() throws Exception {
		//远程调用rpc
		URL url = new URL("http://localhost:8080/sms/send/tony");
		byte[] result = new byte[3];
		InputStream input = url.openStream();
		IOUtils.readFully(input, result);
		
		return new String(result);
	}



	@Override
	protected String getFallback() {
		//降级策略 , 再次查询 , 查询备用接口/缓存/mock
		return super.getFallback();
	}
	
	
	
	
	

}
