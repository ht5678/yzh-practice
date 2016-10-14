package netty.io.demo.fundamental.uptime;

import io.netty.bootstrap.Bootstrap;

/**
 * 
 * @author yuezh2   2016年10月14日 下午3:35:20
 *
 */
public class Main {
	
	
	public static void main(String[] args) {
		UptimeClient uc = new UptimeClient();
		uc.configureBootstrap(new Bootstrap()).connect();
	}
	

}
