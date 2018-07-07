package orderservice.server.core.jta;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

public class AtomikosJtaPlatfom extends AbstractJtaPlatform {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6699680949022046641L;
	private static UserTransaction userTransaction;
    private static TransactionManager ransactionManager;
	
	@Override
	protected TransactionManager locateTransactionManager() {
		// TODO Auto-generated method stub
		return ransactionManager;
	}

	@Override
	protected UserTransaction locateUserTransaction() {
		// TODO Auto-generated method stub
		return userTransaction;
	}

	public static UserTransaction getUserTransaction() {
		return userTransaction;
	}

	public static void setUserTransaction(UserTransaction userTransaction) {
		AtomikosJtaPlatfom.userTransaction = userTransaction;
	}

	public static TransactionManager getRansactionManager() {
		return ransactionManager;
	}

	public static void setRansactionManager(TransactionManager ransactionManager) {
		AtomikosJtaPlatfom.ransactionManager = ransactionManager;
	}

	
	
}
