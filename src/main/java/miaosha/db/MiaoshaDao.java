package miaosha.db;

import java.math.BigDecimal;

/**
 * 
 * @author yuezh2   2017年5月18日 下午3:46:32
 *
 */
public interface MiaoshaDao {
	
	
	/**
	 * 
	 * 更新库存
	 * @return
	 */
	public int modifyAmount(String code , BigDecimal amount);

}
