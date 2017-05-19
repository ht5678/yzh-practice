package miaosha.db;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author yuezh2   2017年5月18日 下午3:46:10
 *
 */
@Repository
public class MiaoShaServiceImpl implements MiaoShaService {

	
	@Autowired
	private MiaoshaDao dao;
	
	
	
	/**
	 * 秒杀DB实现
	 * 
	 * int i = update goods set amount=amount - #amount# where code=xxx and amount - #amount#>=0
	 * 
	 * @param code
	 * @param amount		库存
	 * @return		扣减产品库存是否成功
	 */
	public boolean modifyAmount(String code , BigDecimal amount){
		return dao.modifyAmount(code, amount)==1;
	}
	
}
