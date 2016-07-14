package guice.demo.provider;




/**
 * 
 * @author yuezh2   2016年7月14日 上午11:14:02
 *
 * @param <T>
 */
public interface Provider<T> {

	T get();
	
}