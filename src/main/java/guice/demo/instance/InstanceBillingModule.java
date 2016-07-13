package guice.demo.instance;

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
public class InstanceBillingModule extends AbstractModule {

	
	
	
	
	@Override
	protected void configure() {
		bind(String.class)
	        .annotatedWith(Names.named("JDBC URL"))
	        .toInstance("jdbc:mysql://localhost/pizza");
	    bind(Integer.class)
	        .annotatedWith(Names.named("login timeout seconds"))
	        .toInstance(10);

	}

}
