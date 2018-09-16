package coupon.sys.core.couponSystem;

import coupon.sys.core.connections.ConnectionPool;
import coupon.sys.core.dailyTask.DailyCouponExpirationTask;
import coupon.sys.core.dao.CompanyDAO;
import coupon.sys.core.dao.CouponDAO;
import coupon.sys.core.dao.CustomerDAO;
import coupon.sys.core.dao.db.CompanyDAODb;
import coupon.sys.core.dao.db.CouponDAODb;
import coupon.sys.core.dao.db.CustomerDAODb;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.exceptions.DbException;
import coupon.sys.core.exceptions.LoginException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.ClientFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;

/**
 * this is the operating class - singleton
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class CouponSystem {

	private CouponDAO couponDAO;
	private CustomerDAO customerDAO;
	private CompanyDAO companyDAO;
	private DailyCouponExpirationTask dailyCouponExpirationTask;
	private ClientFacade clientFacade;
	private ConnectionPool connectionPool;
	private static CouponSystem instance;

	private CouponSystem() throws CouponSystemException {
		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		couponDAO = new CouponDAODb();
		customerDAO = new CustomerDAODb();
		companyDAO = new CompanyDAODb();

		Thread dailyCouponExpirationTaskThread = new Thread(dailyCouponExpirationTask,
				"daily expiration deleting task");
		dailyCouponExpirationTaskThread.start();
	}

	/**
	 * get instance method
	 * 
	 * @return instance
	 * @throws CouponSystemException
	 */
	public synchronized static CouponSystem getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}

	/**
	 * login method for clients no need to choose client type because every name of
	 * company or customer is unique
	 * 
	 * @param name
	 * @param password
	 * @return client facade
	 * @throws DbException
	 * @throws LoginException
	 */
	public ClientFacade login(String name, String password) throws LoginException, DbException {
		try {
			if (name.equals("admin") && password.equals("1234")) {
				clientFacade = new AdminFacade(companyDAO, customerDAO, couponDAO);
			} else if (companyDAO.login(name, password)) {
				clientFacade = new CompanyFacade(couponDAO, companyDAO, companyDAO.getCompany(name));
				System.out.println("company " + name + " logged");
			} else if (customerDAO.login(name, password)) {
				clientFacade = new CustomerFacade(customerDAO, couponDAO, customerDAO.getCustomer(name));
				System.out.println("customer " + name + " logged");
			} else {
				throw new LoginException();
			}
		} catch (DbException e) {
			throw new LoginException();
		}
		return clientFacade;
	}

	/**
	 * this method shuts down the system
	 * 
	 * @throws CouponSystemException
	 * @throws InterruptedException
	 */
	public void shutDown() throws CouponSystemException, InterruptedException {
		this.dailyCouponExpirationTask.stopTask();
		this.connectionPool.closeAllConnections();
	}

}
