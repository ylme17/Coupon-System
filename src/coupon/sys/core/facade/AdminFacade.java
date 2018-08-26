package coupon.sys.core.facade;

import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class AdminFacade implements ClientFacade {

	private CompanyDao companyDaoDb;
	private CustomerDao customerDaoDb;
	private CouponDao couponDaoDb;

	public AdminFacade(CompanyDao companyDaoDb, CustomerDao customerDaoDb, CouponDao couponDaoDb) {
		this.companyDaoDb = companyDaoDb;
		this.customerDaoDb = customerDaoDb;
		this.couponDaoDb = couponDaoDb;
	}
	
	public AdminFacade(CompanyDao companyDaoDb, CustomerDao customerDaoDb) {
		this.companyDaoDb = companyDaoDb;
		this.customerDaoDb = customerDaoDb;
	}

	public void createCompany(Company company) throws CouponSystemException {
		if (companyDaoDb.checkIfExist(company) == false) {
			companyDaoDb.createCompany(company);
		} else {
			throw new CouponSystemException("company " + company.getName() + ", already exist");
		}
	}

	public void removeComapny(Company company) {
		try {
			couponDaoDb.removeCustomerCoupon(company);
			couponDaoDb.removeCouponByCompany(company);
			companyDaoDb.removeCompanyCoupon(company);
			companyDaoDb.removeCompany(company);
		} catch (CouponSystemException e) {
			System.out.println(e + ", company was not removed");
		}
	}

	public void updateCompany(Company company) {
		try {
			companyDaoDb.updateCompany(company);
		} catch (CouponSystemException e) {
			System.out.println(e + ", company was not updated");
		}
	}

	public Company getCompany(long id) {
		Company company = null;
		try {
			companyDaoDb.getCompany(id);
		} catch (CouponSystemException e) {
			System.out.println(e + ", failed to get company");
		}
		return company;
	}

	public Collection<Company> getAllCompanies() {
		ArrayList<Company> allCompanies = null;
		try {
			allCompanies = (ArrayList<Company>) companyDaoDb.getAllCompanies();
		} catch (CouponSystemException e) {
			System.out.println(e + ", failed to get companies collection");
		}
		return allCompanies;
	}

	public void createCustomer(Customer customer) throws CouponSystemException {
		if (customerDaoDb.checkIfExist(customer)==false) {
			customerDaoDb.createCustomer(customer);
		} else {
			throw new CouponSystemException("customer was not created");
		}
	}

	public void removeCustomer(Customer customer) {
		try {
			customerDaoDb.removeCustomer(customer);
		} catch (CouponSystemException e) {
			System.out.println(e + ", customer was not removed");
		}
	}

	public void updateCustomer(Customer customer) {
		try {
			customerDaoDb.updateCustomer(customer);
		} catch (CouponSystemException e) {
			System.out.println(e + ", customer was not updated");
		}
	}

	public Customer getCustomer(long id) {
		Customer customer = null;
		try {
			customerDaoDb.getCustomer(id);
		} catch (CouponSystemException e) {
			System.out.println(e + ", failed to get customer");
		}
		return customer;
	}

	public Collection<Customer> getAllCustomer() {
		ArrayList<Customer> allCustomers = null;
		try {
			allCustomers = (ArrayList<Customer>) customerDaoDb.getAllCustomer();
		} catch (CouponSystemException e) {
			System.out.println(e + ", failed to get customers collection");
		}
		return allCustomers;
	}

}
