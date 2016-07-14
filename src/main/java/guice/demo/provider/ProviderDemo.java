package guice.demo.provider;

import com.google.inject.Guice;
import com.google.inject.Injector;

import guice.demo.base.DatabaseTransactionLog;

/**
 * 
 * @author yuezh2   2016年7月13日 下午5:19:09
 *
 */
public class ProviderDemo {

	
	
	
	public static void main(String[] args) {
	    /*
	     * Guice.createInjector() takes your Modules, and returns a new Injector
	     * instance. Most applications will call this method exactly once, in their
	     * main() method.
	     */
	    Injector injector = Guice.createInjector(new ProviderBillingModule());

	    /*
	     * Now that we've got the injector, we can build objects.
	     */
	    DatabaseTransactionLog log = injector.getInstance(DatabaseTransactionLog.class);
	    
	    
	    System.out.println(log.getConnection());
	  }
	
	
}
