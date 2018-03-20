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
    	
    	for(int m =0 ; m<5 ; m++){
	    	//测试组1
	        CyclicBarrier barrier = new CyclicBarrier(size, new Runnable() {  
	            //栅栏动作，在计数器为0的时候执行  
	            @Override  
	            public void run() {  
	                System.out.println("我们都准备好了.");  
	            }  
	        });  
	          
	        ExecutorService es = Executors.newCachedThreadPool();  
	        for (int i = 0; i < size; i++) {  
	            es.execute(new Roommate(barrier));  
	        }  
	        
	        Thread.sleep(2000);
    	}
        
    }  
}
  
class Roommate implements Runnable {
    private CyclicBarrier barrier;  
    private static int Count = 1;  
    private int id;  
  
    public Roommate(CyclicBarrier barrier) {  
        this.barrier = barrier;  
        this.id = Count++;  
    }  
  
    @Override  
    public void run() {  
        System.out.println(id + " : 我到了");  
        try {  
            //通知barrier，已经完成动作，在等待  
            barrier.await();  
            new HeartBeatsClient().connect(8080, "10.250.0.30");
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
    }  
} 
