package miaosha.mc;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author yuezh2   2017年5月19日 下午5:55:07
 *
 */
@Repository
public class MiaoshaMCServiceImpl implements MiaoshaMCService {

	
	
//	@Autowired
	private static SpyMemCacheClient cache ;
	
	
	static{
		try {
			cache = new SpyMemCacheClient("lenovodb:11211");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 初始化库存
	 * @param code
	 * @param amount
	 * @return
	 */
	public boolean initAmount(String code , BigDecimal amount){
		boolean result  = cache.set(code, amount.intValue()+"");
		cache.set(code+"_value", 0L);
		return result;
	}
	
	
	
	
	/**
	 * cache方式实现锁库存
	 * 
	 * decr是有线程保证的,但是最大的缺点就是 , 不知道之前是什么值
	 * 
	 * @param code
	 * @param amount		库存
	 * @return		扣减产品库存是否成功
	 */
	public boolean modifyAmount(String code , BigDecimal amount , Long total){
		try{
			//decr : 原有 > amount  正常
			//原有<amount 			    返回0,不正常
			
			
			//1.100 - 1000 = 0
			//read缓存 -----------  不行
			//
			long result = cache.decr(code , amount.intValue());
			if(result >0){
				cache.incr(code+"_value", amount.longValue());
				return true;
			}else{
				Long totalAmount = Long.parseLong((cache.get(code+"_value")+"").trim());
				cache.set(code, total - totalAmount , DateUtils.addSeconds(new Date(), 10));
				return false;
			}
			
			
			//解决方案:
			//用于解决mc的多卖少卖问题 , 在decr的时候 ,如果每次减少库存的数量是随机的 , 这个时候      原有<amount 			    返回0,不正常 
			//
			//每次只减少一个库存 , 最后如果全部减少成功 , 就证明可以生成订单了 , 需要注意的一点是 , 库存数量要比实际+1  , 但是显示的时候是  实际数 ,
			//这样保证不会多出库存
			//for(int i = 0 ; i < 5 ;i++){
			//		int result = mc.decr("xxx");
			//		if(result>=1)		flag = true			else flag= false
			//}
			
			//如果是秒杀 , 每次只能秒杀一个 , 就不存在这个问题 , 但是还是要注意  库存数量要比实际+1
			
			//mc的cas方式 , 没有具体研究过 , 这里做一个标记
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
}
