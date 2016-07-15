package guice.demo.builtin;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * 
 * guice有内置的样板实现， 比如logger 
 * 
 * @author yuezh2   2016年7月13日 下午5:19:09
 *
 */
public class ConsoleDemo {

	
	
	
	public static void main(String[] args) {
	    /*
	     * Guice.createInjector() takes your Modules, and returns a new Injector
	     * instance. Most applications will call this method exactly once, in their
	     * main() method.
	     */
	    Injector injector = Guice.createInjector(new ConsoleBillingModule());

	    /*
	     * Now that we've got the injector, we can build objects.
	     */
	    ConsoleTransactionLog log = injector.getInstance(ConsoleTransactionLog.class);
	    
	    
	    log.logConnectException(new Exception("test built in ..."));
	  }
	
	
}
