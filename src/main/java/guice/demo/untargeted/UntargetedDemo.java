package guice.demo.untargeted;

import com.google.inject.Guice;
import com.google.inject.Injector;

import guice.demo.base.RealBillingService;
import guice.demo.simple.SimpleBillingModule;

/**
 * 
 * autowire by type
 * 
 * @author yuezh2   2016年7月14日 下午3:56:08
 *
 */
public class UntargetedDemo {
	
	
	public static void main(String[] args) {
		/*
	     * Guice.createInjector() takes your Modules, and returns a new Injector
	     * instance. Most applications will call this method exactly once, in their
	     * main() method.
	     */
	    Injector injector = Guice.createInjector(new SimpleBillingModule());

	    /*
	     * Now that we've got the injector, we can build objects.
	     */
	    UntargetedBillingService billingService = injector.getInstance(UntargetedBillingService.class);
	    
	    
	    System.out.println(billingService);
	}

}
