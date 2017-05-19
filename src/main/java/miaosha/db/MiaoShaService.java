package miaosha.db;

import java.math.BigDecimal;

/**
 * 
 * @author yuezh2   2017年5月3日 下午4:54:00
 *
 */
public interface MiaoShaService {

	/**
	 * 秒杀DB实现
	 * 
	 * int i = update goods set amount=amount - #amount# where code=xxx and amount - #amount#>=0
	 * 
	 * @param code
	 * @param amount		库存
	 * @return		扣减产品库存是否成功
	 */
	public boolean modifyAmount(String code , BigDecimal amount);
	
	
}
