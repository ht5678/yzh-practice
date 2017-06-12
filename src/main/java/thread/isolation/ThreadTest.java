package thread.isolation;


/**
 * 
 * 		有一个这样的场景 , IBM以前一到非常经典的线程面试要求 : 子线程循环跑30次 , 暂停 , 然后转到主线程跑40次 ,
 * 接着子线程循环跑30次 , 暂停 , 然后转到主线程跑40次如此往复一共这样交替50次
 * 
 * @author yuezh2   2017年6月12日 下午4:47:15
 *
 */
public class ThreadTest {
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//子线程部分要求: 是50轮换每个轮换循环跑30次
		new Thread(new Runnable() {
			public void run() {
				for(int i =1;i<=50;i++){
					//子线程跑30次
					for(int j = 1;j<=30;j++){
						System.out.println("==============================子线程运行第:"+i+"轮, 第:"+j+"次!");
					}
				}
			}
		}).start();
		
		//50轮回主线程部分
		for(int i =1;i<=50;i++){
			for(int j =1;j<=40;j++){
				System.out.println("主线程运行第:"+i+",第:"+j+"次!");
			}
		}
	}
	

}
