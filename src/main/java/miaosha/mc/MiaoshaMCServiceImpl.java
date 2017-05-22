package miaosha.mc;

import java.io.IOException;
import java.math.BigDecimal;

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
		return result;
	}
	
	
	
	
	/**
	 * cache方式实现锁库存
	 * 
	 * @param code
	 * @param amount		库存
	 * @return		扣减产品库存是否成功
	 */
	public boolean modifyAmount(String code , BigDecimal amount){
		try{
			//decr : 原有 > amount  正常
			//原有<amount 			    返回0,不正常
			long result = cache.decr(code , amount.intValue());
			if(result >0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
}
