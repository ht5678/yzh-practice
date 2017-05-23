package miaosha.mc;

import java.math.BigDecimal;

/**
 * 
 * @author yuezh2   2017年5月19日 下午5:54:54
 *
 */
public interface MiaoshaMCService {
	
	/**
	 * 初始化库存
	 * @param code
	 * @param amount
	 * @return
	 */
	public boolean initAmount(String code , BigDecimal amount);
	
	

	/**
	 * cache方式实现锁库存
	 * 
	 * @param code
	 * @param amount		库存
	 * @return		扣减产品库存是否成功
	 */
	public boolean modifyAmount(String code , BigDecimal amount , Long total);
	
	
}
