package thread.isolation;


/**
 * 
 * @author yuezh2   2017年6月14日 下午4:04:39
 *
 */
public class BusinessDemo {
	//内部有一个控制属性
	private boolean isShowSonThread = true;
	//子线程先处理问题 , 
	public synchronized void sonBusiness(int i){
		while(!isShowSonThread){
			try {
				//子线程就在外面等着了
				this.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//代表没有人
		for(int j=0;j<=30;j++){
			System.out.println("==============================子线程运行第:"+i+"轮, 第:"+j+"次!");
		}
		
		//释放资源 , 
		isShowSonThread=false;
		//通知主线程
		this.notify();
	}
	
	
	
	
	//子线程先处理问题 , 
	public synchronized void mainBusiness(int i){
		while(isShowSonThread){
			try {
				//子线程就在外面等着了
				this.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//代表没有人
		for(int j=0;j<=40;j++){
			System.out.println("主线程运行第:"+i+",第:"+j+"次!");
		}
		
		//释放资源 , 
		isShowSonThread=true;
		//通知子线程
		this.notify();
	}
	
	
}
