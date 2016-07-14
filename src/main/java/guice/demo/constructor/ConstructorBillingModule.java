package guice.demo.constructor;

import com.google.inject.AbstractModule;

import guice.demo.base.Connection;
import guice.demo.base.DatabaseTransactionLog;
import guice.demo.base.TransactionLog;




/**
 * 
 * @author yuezh2   2016年7月13日 下午5:18:35
 *
 */
public class ConstructorBillingModule extends AbstractModule {

	
	
	
	
	@Override
	protected void configure() {
		try {
		      bind(TransactionLog.class).toConstructor(
		          DatabaseTransactionLog.class.getConstructor(Connection.class));
		    } catch (NoSuchMethodException e) {
		      addError(e);
		    }
	}

}
