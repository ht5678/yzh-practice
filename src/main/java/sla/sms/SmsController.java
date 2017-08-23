package sla.sms;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author yuezh2   2017年8月10日 下午5:17:25
 *
 */
@Controller
@RequestMapping(value="sms")
public class SmsController {
	
	
	private static AtomicInteger counter = new AtomicInteger(0);
	
	
	/**
	 * 模拟发送短信的服务
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="send/{user}")
	public String test(@PathVariable("user")String user)throws Exception{
		Thread.sleep(500);
		System.out.println(user+"发送了一条短信 , 编号:"+counter.incrementAndGet());
		return "end";
	}
	

}
