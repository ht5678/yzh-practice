package guice.demo.instance;

import com.google.inject.Guice;
import com.google.inject.Injector;




/**
 * 
 * 注入实例化的对象
 * 
 * @author yuezh2   2016年7月13日 下午5:19:09
 *
 */
public class InstanceDemo {

	
	
	
	public static void main(String[] args) {
	    /*
	     * Guice.createInjector() takes your Modules, and returns a new Injector
	     * instance. Most applications will call this method exactly once, in their
	     * main() method.
	     */
	    Injector injector = Guice.createInjector(new InstanceBillingModule());

	    /*
	     * Now that we've got the injector, we can build objects.
	     */
	    InstanceBillingService billingService = injector.getInstance(InstanceBillingService.class);
	    
	    
	    System.out.println(billingService);
	  }
	
	
}
