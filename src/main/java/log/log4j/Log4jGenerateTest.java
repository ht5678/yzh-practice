package log.log4j;

import org.apache.log4j.Logger;

/**
 * 不断生成日志，测试logstash的log4j插件
 * @author yuezh2
 *
 */
public class Log4jGenerateTest {
	
	/*  logger  */
	private static Logger logger = Logger.getLogger(Log4jGenerateTest.class);
	
	
	
	public static void main(String[] args) {
		for(int i = 0 ; i < 100 ; i++){
			logger.warn("i am a warn logger message");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}
