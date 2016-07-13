package guice.demo.provides;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import guice.demo.base.DatabaseTransactionLog;
import guice.demo.base.TransactionLog;




/**
 * 
 * @author yuezh2   2016年7月13日 下午5:18:35
 *
 */
public class ProvidesBillingModule extends AbstractModule {

	
	
	
	
	@Override
	protected void configure() {
		

	}
	
	
	
	
	@Provides	@Named(value="customNames1")
	  TransactionLog customNames() {
	    DatabaseTransactionLog transactionLog = new DatabaseTransactionLog();
	    transactionLog.setJdbcUrl("jdbc:mysql://localhost/pizza");
	    transactionLog.setThreadPoolSize(30);
	    return transactionLog;
	  }
	
	

	
//	 @Provides @PayPal
//	  CreditCardProcessor providePayPalCreditCardProcessor(
//	      @Named("PayPal API key") String apiKey) {
//	    PayPalCreditCardProcessor processor = new PayPalCreditCardProcessor();
//	    processor.setApiKey(apiKey);
//	    return processor;
//	  }

}
