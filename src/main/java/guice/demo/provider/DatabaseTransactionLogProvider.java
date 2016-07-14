package guice.demo.provider;

import com.google.inject.Inject;

import guice.demo.base.Connection;
import guice.demo.base.DatabaseTransactionLog;
import guice.demo.base.TransactionLog;



/**
 * 
 * @author yuezh2   2016年7月14日 上午11:18:24
 *
 */
public class DatabaseTransactionLogProvider implements Provider<TransactionLog> {
  
	private final Connection connection;

  
	  @Inject
	  public DatabaseTransactionLogProvider(Connection connection) {
	    this.connection = connection;
	  }

	  
	  
	  public TransactionLog get() {
	    DatabaseTransactionLog transactionLog = new DatabaseTransactionLog();
	    transactionLog.setConnection(connection);
	    return transactionLog;
	  }
  
  
}