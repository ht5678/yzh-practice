package netty.io.demo.mqtt;



/**
 * 
 * @author yuezh2   2016年12月22日 下午4:37:24
 *
 */
public class ClientTestDemo {

	
	
	public static void main(String[] args) throws Exception{
//		for( int i = 0 ; i < 10 ;i++){
//			new Thread(new Runnable() {
//				public void run() {
//					MqttClient2Test test = new MqttClient2Test();
//					try {
//						test.test();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			});
//			
//		}
		
		
		for( int i = 0 ; i < 1 ;i++){
//			MqttClient2Test test = new MqttClient2Test();
//			test.test();
			
			new Thread(new Runnable() {
				public void run() {
					MqttClient2Test client = new MqttClient2Test();
					try {
						client.test();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			
		}
		
//		MqttClient2Test test2 = new MqttClient2Test();
//		test2.test();
		Thread.sleep(1000 * 60);
		
	}
	
	
}
