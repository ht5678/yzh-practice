package netty.io.demo.apply.test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import netty.io.demo.apply.idle.HeartBeatsClient;


/**
 * 并发测试
 * @author yuezh2   2018年3月20日 下午4:38:47
 *
 */
public class TestDemo {

	
	private static int size =200;
	
	
    public static void main(String[] args) throws Exception{  
            try {
            	
            	String host =args[0];
                int port = Integer.valueOf(args[1]);
                int group = Integer.valueOf(args[2]);
    	
                System.out.println(host+":"+port);
                
		    	for(int m =0 ; m<group ; m++){
			        CyclicBarrier barrier = new CyclicBarrier(size);  
			        ExecutorService es = Executors.newCachedThreadPool();  
			        for (int i = 0; i < size; i++) {  
			            es.execute(new Roommate(barrier , host , port));  
			        }  
			        
			        Thread.sleep(2000);
		    	}
    	
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        
    }  
}
  
class Roommate implements Runnable {
    private CyclicBarrier barrier;  
    private static int Count = 1;  
    private int id;  
    private String host;
    private int port;
  
    public Roommate(CyclicBarrier barrier , String host , int port) {  
        this.barrier = barrier;  
        this.id = Count++;  
        this.host = host;
        this.port = port;
    }  
  
    @Override  
    public void run() {  
        try {  
            barrier.await();  
            new HeartBeatsClient().connect(port, host);
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
    }  
} 
