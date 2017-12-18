package sla.sms;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author yuezh2   2017年9月4日 下午2:41:30
 *
 */
@Controller
@RequestMapping(value="slatest")
public class TestController {

	
	@RequestMapping("test")
	public String test()throws IOException{
		URL url = new URL("http://localhost:8081/yzh-practice/sms/send/tony");
		byte[] result = new byte[3];
		InputStream input = url.openStream();
		IOUtils.readFully(input, result);
		
		System.out.println(new String(result));
		
		return new String(result);
	}
	
	
	
	
	@RequestMapping("test_hystrix")
	public String testHystrix()throws IOException{
		
		return new SmsSendCommand().execute();
	}
	
	
	
	
}
