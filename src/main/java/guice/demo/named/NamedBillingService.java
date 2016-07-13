package guice.demo.named;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import guice.demo.base.BillingService;
import guice.demo.base.CreditCard;
import guice.demo.base.CreditCardProcessor;
import guice.demo.base.PizzaOrder;
import guice.demo.base.Receipt;
import guice.demo.base.TransactionLog;

/**
 * 
 * @author yuezh2   2016年7月13日 下午5:09:06
 *
 */
public class NamedBillingService implements BillingService{
	
//	private final CreditCardProcessor processor;
//	private final TransactionLog transactionLog;

	  
	  @Inject
	  NamedBillingService(@Named(value="checkout") CreditCardProcessor processor, 
	      TransactionLog transactionLog) {
//	    this.processor = processor;
//	    this.transactionLog = transactionLog;
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
