package coupon.sys.core.couponSystem;

import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.dao.db.CompanyDaoDb;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.dao.db.CustomerDaoDb;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.exceptions.DbException;
import coupon.sys.core.exceptions.LoginException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.ClientFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;
import coupon.sys.core.thread.DailyCouponExpirationTask;

/**
 * this is the operating class - singleton
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class CouponSystem {

	private CouponDao couponDao;
	private CustomerDao customerDao;
	private CompanyDao companyDao;
	private DailyCouponExpirationTask dailyCouponExpirationTask;
	private ClientFacade clientFacade;
	private ConnectionPool connectionPool;

	/**
	 * instance of the coupon system
	 */
	public static CouponSystem instance;

	private CouponSystem() throws CouponSystemException {
		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		couponDao = new CouponDaoDb();
		customerDao = new CustomerDaoDb();
		companyDao = new CompanyDaoDb();

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
	public static CouponSystem getInstance() throws CouponSystemException {
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
				clientFacade = new AdminFacade(companyDao, customerDao, couponDao);
			} else if (companyDao.login(name, password)) {
				clientFacade = new CompanyFacade(couponDao, companyDao, companyDao.getCompany(name));
				System.out.println("company " + name + " logged");
			} else if (customerDao.login(name, password)) {
				clientFacade = new CustomerFacade(customerDao, couponDao, customerDao.getCustomer(name));
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
