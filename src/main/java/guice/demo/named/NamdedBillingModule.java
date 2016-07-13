package guice.demo.named;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import guice.demo.base.CreditCardProcessor;
import guice.demo.base.DatabaseTransactionLog;
import guice.demo.base.PaypalCreditCardProcessor;
import guice.demo.base.TransactionLog;




/**
 * 
 * @author yuezh2   2016年7月13日 下午5:18:35
 *
 */
public class NamdedBillingModule extends AbstractModule {

	
	
	
	
	@Override
	protected void configure() {
		/*
	      * This tells Guice that whenever it sees a dependency on a TransactionLog,
	      * it should satisfy the dependency using a DatabaseTransactionLog.
	      */
	    bind(TransactionLog.class).to(DatabaseTransactionLog.class);

	     /*
	      * Similarly, this binding tells Guice that when CreditCardProcessor is used in
	      * a dependency, that should be satisfied with a PaypalCreditCardProcessor.
	      */
		bind(CreditCardProcessor.class).annotatedWith(Names.named("checkout")).to(PaypalCreditCardProcessor.class);

	}

}
