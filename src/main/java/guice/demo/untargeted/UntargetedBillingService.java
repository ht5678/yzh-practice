package guice.demo.untargeted;

import com.google.inject.Inject;

import guice.demo.base.BillingService;
import guice.demo.base.CreditCard;
import guice.demo.base.DatabaseTransactionLog;
import guice.demo.base.PaypalCreditCardProcessor;
import guice.demo.base.PizzaOrder;
import guice.demo.base.Receipt;

/**
 * 
 * @author yuezh2   2016年7月13日 下午5:09:06
 *
 */
public class UntargetedBillingService implements BillingService{
	
//	private final CreditCardProcessor processor;
//	private final TransactionLog transactionLog;

	  
	  @Inject
	  UntargetedBillingService(PaypalCreditCardProcessor processor, 
			  DatabaseTransactionLog transactionLog) {
//	    this.processor = processor;
//	    this.transactionLog = transactionLog;
		  
		  System.out.println(processor);
		  System.out.println(transactionLog);
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
