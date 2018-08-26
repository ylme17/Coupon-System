package coupon.sys.core.couponSystem;

import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.dao.db.CompanyDaoDb;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.dao.db.CustomerDaoDb;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.ClientFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;
import coupon.sys.core.thread.DailyCouponExpirationTask;

public class CouponSystem {

	private CouponDao couponDao;
	private CustomerDao customerDao;
	private CompanyDao companyDao;
	private DailyCouponExpirationTask dailyCouponExpirationTask;
	private ClientFacade clientFacade;
	private ConnectionPool connectionPool;

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
			throw new CouponSystemException("cant get a connection ot activate daily task");
		}
	}

	public static CouponSystem getInstance() throws CouponSystemException {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}

	public ClientFacade login(String name, String password, Enum<ClientType> clientType) throws CouponSystemException{
		try {
			if(ClientType.ADMIN.equals(clientType) && name.equals("admin") && password.equals("1234")) {
				clientFacade=new AdminFacade(this.companyDao, this.customerDao);
			}else if(ClientType.COMPANY.equals(clientType)&&companyDao.login(name, password)) {
				clientFacade=new CompanyFacade(couponDao, companyDao, companyDao.getCompany(name));
			}else if(ClientType.CUSTOMER.equals(clientType)&&customerDao.login(name, password)) {
				clientFacade=new CustomerFacade(customerDao, couponDao, customerDao.getCustomer(name));
			}else {
				throw new CouponSystemException("wrong user name or password, please try again");
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException("wrong user name or password, please try again" ,e);
		}
		return clientFacade;
	}

	public void shutDown() throws CouponSystemException {
		this.dailyCouponExpirationTask.stopTask();
		this.connectionPool.closeAllConnections();
	}

}
