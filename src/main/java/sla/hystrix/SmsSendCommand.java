package sla.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * 
 * @author yuezh2   2017年8月23日 下午5:24:00
 *
 */
public class SmsSendCommand extends HystrixCommand<String>{

	
	protected SmsSendCommand(HystrixCommandGroupKey group) {
		super(HystrixCommandGroupKey.Factory.asKey("smsGroup"));
	}

	
	
	
	@Override
	protected String run() throws Exception {
		return null;
	}

	
}
