package guice.demo.instance;

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
public class InstanceBillingService implements BillingService{
	
//	private final CreditCardProcessor processor;
//	private final TransactionLog transactionLog;

	  
	  @Inject
	  InstanceBillingService(@Named(value="JDBC URL") String url , @Named(value="login timeout seconds") Integer times) {
//	    this.processor = processor;
//	    this.transactionLog = transactionLog;
		  System.out.println(url);
		  System.out.println(times);
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
