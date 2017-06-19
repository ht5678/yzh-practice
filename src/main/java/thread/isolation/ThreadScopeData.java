package thread.isolation;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 底层线程业务线的数据隔离 - 模仿底层购买业务线程
 * @author yuezh2   2017年6月14日 下午5:30:48
 *
 */
public class ThreadScopeData {

	//因为每个线程对象都是唯一数据的这个关系
	private static Map<Thread, Integer> buyThreadForDataMap = new HashMap<>();
	
	
	public static void main(String[] args) {
		//模仿客户端点击购买产生的每一个线程
		for(int i = 0 ; i < 2;i++){
			new Thread(new Runnable() {
				public void run() {
					//产生价格信息
					int data = new Random().nextInt();
					//价格展示
					System.out.println("产生的线程名称:"+Thread.currentThread().getName()+"has put  data "+data);
					//维护线程进去
					buyThreadForDataMap.put(Thread.currentThread(), data);
					//A模块处理当前数据
					new A().get();
					//B模块处理
					new B().get();
				}
			}).start();;
		}
		
	}
	
	
	//A处理模块
	static class A{
		//拿每个线程带来的数据进行处理
		public int get(){
			int data = buyThreadForDataMap.get(Thread.currentThread());
			//业务处理
			System.out.println(Thread.currentThread().getName()+"进入,拿到处理的数据是:"+data);
			return data;
		}
		
	}
	
	
	//B处理模块
	static class B{
		//拿每个线程带来的数据进行处理
		public int get(){
			int data = buyThreadForDataMap.get(Thread.currentThread());
			//业务处理
			System.out.println(Thread.currentThread().getName()+"进入,拿到处理的数据是:"+data);
			return data;
		}
		
	}
	
}
