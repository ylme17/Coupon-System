package coupon.sys.core.couponSystem;

import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.dao.db.CompanyDaoDb;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.dao.db.CustomerDaoDb;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.exceptions.LoginException;
import coupon.sys.core.exceptions.ObjectDontExistException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.ClientFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;
import coupon.sys.core.thread.DailyCouponExpirationTask;

/**
 * this is the operating class - singleton
 * @author YECHIEL
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
	 * instance of this class
	 */
	public static CouponSystem instance;

	private CouponSystem() throws CouponSystemException{
		dailyCouponExpirationTask = new DailyCouponExpirationTask();
		couponDao = new CouponDaoDb();
		customerDao = new CustomerDaoDb();
		companyDao = new CompanyDaoDb();
		try {
			connectionPool = ConnectionPool.getInstance();
			Thread dailyCouponExpirationTaskThread = new Thread(dailyCouponExpirationTask, "daily expiration deleting task");
			dailyCouponExpirationTaskThread.start();
		} catch (Exception e) {
			throw new CouponSystemException("cant get a connection to activate daily task");
		}
	}

	/**
	 * get instance method
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
	 * login method for clients
	 * @param name name of client
	 * @param password password of client
	 * @return relevant facade
	 * @throws CouponSystemException
	 */
	public ClientFacade login(String name, String password) throws CouponSystemException{
		try {
			if(name.equals("admin") && password.equals("1234")) {
				clientFacade=new AdminFacade(this.companyDao, this.customerDao);
			}else if(companyDao.login(name, password)) {
				clientFacade=new CompanyFacade(couponDao, companyDao, companyDao.getCompany(name));
			}else if(customerDao.login(name, password)) {
				clientFacade=new CustomerFacade(customerDao, couponDao, customerDao.getCustomer(name));
			}else {
				throw new LoginException();
			}
		} catch (LoginException | ObjectDontExistException e) {
			System.out.println(e);
		}
		return clientFacade;
	}

	/**
	 * this method shuts down the system
	 * @throws CouponSystemException
	 */
	public void shutDown() throws CouponSystemException {
		this.dailyCouponExpirationTask.stopTask();
		this.connectionPool.closeAllConnections();
	}

}
