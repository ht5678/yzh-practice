package guice.demo.annotation;

import com.google.inject.Guice;
import com.google.inject.Injector;




/**
 * 
 * 多个class绑定同一个type
 * 
 * @author yuezh2   2016年7月13日 下午5:19:09
 *
 */
public class AnnotationDemo {

	
	
	
	public static void main(String[] args) {
	    /*
	     * Guice.createInjector() takes your Modules, and returns a new Injector
	     * instance. Most applications will call this method exactly once, in their
	     * main() method.
	     */
	    Injector injector = Guice.createInjector(new AnnotationBillingModule());

	    /*
	     * Now that we've got the injector, we can build objects.
	     */
	    AnnotationBillingService billingService = injector.getInstance(AnnotationBillingService.class);
	    
	    
	    System.out.println(billingService);
	  }
	
	
}
