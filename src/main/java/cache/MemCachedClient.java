package cache;

import java.io.IOException;

import miaosha.mc.SpyMemCacheClient;

/**
 * 
 * @author yuezh2   2017年5月25日 上午11:34:20
 *
 */
public class MemCachedClient {

	
	private static SpyMemCacheClient cache ;
	
	
	static{
		try {
			cache = new SpyMemCacheClient("lenovodb:11211");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		MemCachedClient client = new MemCachedClient();
		boolean flag1 = client.cache.set("test_1", "2");
		System.out.println("初始化数据结果:"+flag1);
		long flag2 = client.cache.decr("test_1");
		System.out.println("减少库存结果:"+flag2);
		long flag3 = client.cache.decr("test_1");
		System.out.println("减少库存结果:"+flag3);
		long flag4 = client.cache.decr("test_1");
		System.out.println("减少库存结果:"+flag4);
		
	}
	
}
