package dbpools;



/**
 * 并发编程调用适配器
 * 
 * @author yuezh2   2017年4月27日 下午3:11:00
 *
 */
public class PoolManager {

	//为什么用内部类
	private static class createPool{
		private static MyPoolImpl pools = new MyPoolImpl();
	}
	
	/**
	 * 多个线程在加载内部类对象的时候 , 线程时互斥的
	 * 索引用单例内部类的模式避免线程混乱
	 * jvm类加载在加载类到内存的原理是一样的
	 * @return
	 */
	public static MyPoolImpl getPoolInstance(){
		return  createPool.pools;
	}
	
	
}
