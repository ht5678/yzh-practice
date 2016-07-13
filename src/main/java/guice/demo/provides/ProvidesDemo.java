package guice.demo.provides;

import com.google.inject.Guice;
import com.google.inject.Injector;




/**
 * 
 * 将实例化后的bean自定义名称
 * 
 * @author yuezh2   2016年7月13日 下午5:19:09
 *
 */
public class ProvidesDemo {

	
	
	
	public static void main(String[] args) {
	    /*
	     * Guice.createInjector() takes your Modules, and returns a new Injector
	     * instance. Most applications will call this method exactly once, in their
	     * main() method.
	     */
	    Injector injector = Guice.createInjector(new ProvidesBillingModule());

	    /*
	     * Now that we've got the injector, we can build objects.
	     */
	    ProvidesBillingService billingService = injector.getInstance(ProvidesBillingService.class);
	    
	    
	    System.out.println(billingService);
	  }
	
	
}
