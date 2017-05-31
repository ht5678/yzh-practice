package transaction;


/**
 * 银行接口
 * @author sdwhy
 *
 */
public interface BankService {

	
	/**
	 * 出款接口
	 * @param orderInfo
	 * @return
	 */
	public BankResponse moneyOut(OrderVO orderInfo);
	
}
