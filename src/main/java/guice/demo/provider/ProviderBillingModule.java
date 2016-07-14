package guice.demo.provider;

import javax.inject.Provider;

import com.google.inject.AbstractModule;

import guice.demo.base.TransactionLog;

/**
 * 
 * @author yuezh2   2016年7月14日 上午11:19:12
 *
 */
public class ProviderBillingModule extends AbstractModule {
	  
	
	@Override
	protected void configure() {
		bind(TransactionLog.class)
			.toProvider((Class<? extends Provider<? extends TransactionLog>>) DatabaseTransactionLogProvider.class);
	}
	
	
}