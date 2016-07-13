package guice.demo.base;

import com.google.inject.Inject;

/**
 * 
 * @author yuezh2   2016年7月13日 下午5:09:06
 *
 */
public class RealBillingService implements BillingService{
	
	private final CreditCardProcessor processor;
	private final TransactionLog transactionLog;

	  
	  
	  
	  @Inject
	  RealBillingService(CreditCardProcessor processor, 
	      TransactionLog transactionLog) {
	    this.processor = processor;
	    this.transactionLog = transactionLog;
	  }

	  
	  
	  /**
	   * 
	   * @param order
	   * @param creditCard
	   * @return
	   */
	  public Receipt chargeOrder(PizzaOrder order, CreditCard creditCard) {
//		  try {
//		      ChargeResult result = processor.charge(creditCard, order.getAmount());
//		      transactionLog.logChargeResult(result);
//
//		      return result.wasSuccessful()
//		          ? Receipt.forSuccessfulCharge(order.getAmount())
//		          : Receipt.forDeclinedCharge(result.getDeclineMessage());
//		     } catch (UnreachableException e) {
//		      transactionLog.logConnectException(e);
//		      return Receipt.forSystemFailure(e.getMessage());
//		    }
		  return null;
	  }

}
