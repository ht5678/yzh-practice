package miaosha.db;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * CREATE TABLE `TB_GOODS_INFO` (
	  `ID` int(11) NOT NULL,
	  `GOOD_CODE` varchar(50) DEFAULT NULL COMMENT '商品编号',
	  `AMOUNT` decimal(10,0) DEFAULT NULL COMMENT '剩余数量',
	  `SINGLE_AMOUNT` decimal(10,0) DEFAULT NULL COMMENT '单次限额',
	  PRIMARY KEY (`ID`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 * 
 * 
 * 
 * @author yuezh2   2017年5月18日 下午3:46:49
 *
 */
@Repository
public class MiaoshaDaoImpl implements MiaoshaDao {

	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(MiaoshaDaoImpl.class);
	
	@Resource(name="template")
	private JdbcTemplate template;
	
	
	
	
//	public int insertData(){
//		
//	}
	
	
	
	
	/**
	 * 
	 * 更新库存
	 * @return
	 */
	public int modifyAmount(String code , BigDecimal amount){
		int result = 0;
		try{
			result = template.update("update test.TB_GOODS_INFO set AMOUNT=AMOUNT-? where good_code=? and AMOUNT-?>=0", new Object[]{amount,code,amount});
		}catch(Exception e){
			LOGGER.error(String.format("the error occured:%s", e.getMessage()), e);
		}
		return result;
	}
	
	
	
	
}
