package guice.demo.untargeted;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import guice.demo.base.DatabaseTransactionLog;
import guice.demo.base.PaypalCreditCardProcessor;




/**
 * 
 * @author yuezh2   2016年7月13日 下午5:18:35
 *
 */
public class UntargetedBillingModule extends AbstractModule {

	
	
	
	
	@Override
	protected void configure() {
		//1
		bind(DatabaseTransactionLog.class);
		bind(PaypalCreditCardProcessor.class).in(Singleton.class);

		
		//2  names
//		bind(DatabaseTransactionLog.class).annotatedWith(Names.named("foo")).to(DatabaseTransactionLog.class);
//		bind(PaypalCreditCardProcessor.class).annotatedWith(Names.named("foo")).to(PaypalCreditCardProcessor.class)
//				.in(Singleton.class);
		
	}

}
