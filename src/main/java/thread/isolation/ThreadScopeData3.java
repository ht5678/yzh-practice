package thread.isolation;

import java.util.Random;

/**
 * 底层线程业务线的数据隔离 - 模仿底层购买业务线程  , 然后将底层的处理数据结构隔离
 * @author yuezh2   2017年6月14日 下午5:30:48
 *
 */
public class ThreadScopeData3 {

	
	
	
	public static void main(String[] args) {
		//模仿客户端点击购买产生的每一个线程
		for(int i = 0 ; i < 200000;i++){
			new Thread(new Runnable() {
				public void run() {
					//产生价格信息
					int data = new Random().nextInt();
					//价格展示
					System.out.println("产生的线程名称:"+Thread.currentThread().getName()+"has put  data "+data+"_Connection");
					
					MyDealScopeData.getThreadInScopeData().setName(data+"_Connection");
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
		public void get(){
			MyDealScopeData data = MyDealScopeData.getThreadInScopeData();
			//业务处理
			System.out.println(Thread.currentThread().getName()+"进入,拿到处理的数据是:"+data.getName());
		}
		
	}
	
	
	//B处理模块
	static class B{
		//拿每个线程带来的数据进行处理
		public void get(){
			MyDealScopeData data = MyDealScopeData.getThreadInScopeData();
			//业务处理
			System.out.println(Thread.currentThread().getName()+"进入,拿到处理的数据是:"+data.getName());
		}
		
	}
	
	
	
	
	
	
	
	
}




//暴露一个公告接口  实例
class MyDealScopeData{
	//因为每个线程对象都是唯一数据的这个关系
		private static ThreadLocal<MyDealScopeData> buyThreadForDataMap = new ThreadLocal<>();
		private int id;
		private String name;
		
		
		private  MyDealScopeData(){
			
		}
		
		
		//暴露一个实例 , 资源消耗比那个内部类多一点
		public static MyDealScopeData getThreadInScopeData(){
			MyDealScopeData instanceData = buyThreadForDataMap.get();
			if(instanceData == null){
				instanceData = new MyDealScopeData();
				buyThreadForDataMap.set(instanceData);
			}
			return instanceData;
		}


		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
}
