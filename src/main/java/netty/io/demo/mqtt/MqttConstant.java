package netty.io.demo.mqtt;


/**
 * 
 * @author yuezh2   2018年3月15日 下午4:20:55
 *
 */
public class MqttConstant {
	
	/** 重新连接时间 */
	public static final int RECONNECT_DELAY = Integer.parseInt(System.getProperty("reconnectDelay","5"));
	/** 重新连接超时 */
	public static final int READ_TIMEOUT = Integer.parseInt(System.getProperty("readTimeout","10"));
	

}
