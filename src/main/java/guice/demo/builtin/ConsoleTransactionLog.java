package guice.demo.builtin;



import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import guice.demo.base.TransactionLog;




/**
 * 
 * @author yuezh2   2016年7月15日 上午11:05:26
 *
 */
@Singleton
public class ConsoleTransactionLog implements TransactionLog {

	private final Logger logger;

	@Inject
	public ConsoleTransactionLog(Logger logger) {
		this.logger = logger;
	}

	public void logConnectException(Exception e) {
		/* the message is logged to the "ConsoleTransacitonLog" logger */
		logger.warning("Connect exception failed, " + e.getMessage());
	}

}