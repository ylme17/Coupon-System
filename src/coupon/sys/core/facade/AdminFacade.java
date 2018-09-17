package coupon.sys.core.facade;

import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.CompanyDAO;
import coupon.sys.core.dao.CouponDAO;
import coupon.sys.core.dao.CustomerDAO;
import coupon.sys.core.exceptions.DbException;
import coupon.sys.core.exceptions.ObjectAlreadyExistException;
import coupon.sys.core.exceptions.ObjectDontExistException;

/**
 * this class implements the business logic of Admin
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class AdminFacade implements ClientFacade {

	private CompanyDAO companyDAO;
	private CustomerDAO customerDAO;
	private CouponDAO couponDAO;

	/**
	 * construct the Admin facade and get companyDao, customerDao and couponDao
	 * 
	 * @param companyDAO
	 * @param customerDAO
	 * @param couponDAO
	 */
	public AdminFacade(CompanyDAO companyDAO, CustomerDAO customerDAO, CouponDAO couponDAO) {
		this.companyDAO = companyDAO;
		this.customerDAO = customerDAO;
		this.couponDAO = couponDAO;
	}

	/**
	 * this method create company 
	 * check if company or customer with same name exist
	 * 
	 * @param company
	 * @throws ObjectAlreadyExistException
	 * @throws DbException
	 */
	public void createCompany(Company company) throws ObjectAlreadyExistException, DbException {
		if (companyDAO.checkIfExist(company) == false && customerDAO.checkIfExist(company) == false) {
			if(company.getPassword().length()>=5) {
				if(company.getPassword().length()<=8) {
					companyDAO.createCompany(company);
					System.out.println("company created, id:" + company.getId() + " name:" + company.getName());
				} else {
					System.out.println("password too long");
				}
			} else {
				System.out.println("password too short");
			}
		} else {
			throw new ObjectAlreadyExistException(company.getName() + " already exist");
		}
	}

	/**
	 * this method remove company with all coupons first delete coupons from
	 * customer-coupon table, second delete coupons from coupon table third delete
	 * coupons from company-coupon table and finally delete the company itself
	 * 
	 * @param company
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public void removeCompany(Company company) throws ObjectDontExistException, DbException {
		Company companydb = companyDAO.getCompany(company.getId());
		if (companydb != null) {
			couponDAO.removeCustomerCoupon(companydb);
			couponDAO.removeCouponByCompany(companydb);
			companyDAO.removeCompanyCoupon(companydb);
			companyDAO.removeCompany(companydb);
			System.out.println("company " + company.getId() + " deleted");
		} else {
			throw new ObjectDontExistException();
		}
	}

	/**
	 * this method update company, only password and email
	 * 
	 * @param company
	 * @throws DbException
	 * @throws ObjectDontExistException
	 */
	public void updateCompany(Company company) throws DbException, ObjectDontExistException {
		Company companydb = companyDAO.getCompany(company.getId());
		if (companydb != null) {
			companydb.setPassword(company.getPassword());
			companydb.setEmail(company.getEmail());
			if(company.getPassword().length()>=5) {
				if(company.getPassword().length()<=8) {
					companyDAO.updateCompany(companydb);
					System.out.println("company " + company.getId() + " updated");
				} else {
					System.out.println("password too long");
				}
			} else {
				System.out.println("password too short");
			}
		} else {
			throw new ObjectDontExistException();
		}
	}

	/**
	 * this method get all companies
	 * 
	 * @return all companies
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Collection<Company> getAllCompanies() throws ObjectDontExistException, DbException {
		Collection<Company> allCompanies = new ArrayList<>();
		allCompanies = companyDAO.getAllCompanies();
		if (!allCompanies.isEmpty()) {
			System.out.println(allCompanies.toString());
			return allCompanies;
		} else {
			throw new ObjectDontExistException("no companies");
		}
	}

	/**
	 * this method get company by id
	 * 
	 * @param id
	 * @return company
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Company getCompany(long id) throws ObjectDontExistException, DbException {
		Company company = companyDAO.getCompany(id);
		if (company != null) {
			System.out.println(company.toString());
			return company;
		} else {
			throw new ObjectDontExistException();
		}
	}

	/**
	 * this method create customer
	 * check if customer or company with same name exist
	 * 
	 * @param customer
	 * @throws ObjectAlreadyExistException
	 * @throws DbException
	 */
	public void createCustomer(Customer customer) throws ObjectAlreadyExistException, DbException {
		if (customerDAO.checkIfExist(customer) == false && companyDAO.checkIfExist(customer) == false) {
			if(customer.getPassword().length()>=5) {
				if(customer.getPassword().length()<=8) {
					customerDAO.createCustomer(customer);
					System.out.println("customer created, id:" + customer.getId() + " name:" + customer.getName());
				} else {
					System.out.println("password too long");
				}
			} else {
				System.out.println("password too short");
			}
		} else {
			throw new ObjectAlreadyExistException(customer.getName() + " already exist");
		}
	}

	/**
	 * this method remove customer from customer and customer-coupon tables
	 * 
	 * @param customer
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public void removeCustomer(Customer customer) throws ObjectDontExistException, DbException {
		Customer customerdb = customerDAO.getCustomer(customer.getId());
		if (customerdb != null) {
			customerDAO.removeCustomerCoupon(customerdb);
			customerDAO.removeCustomer(customerdb);
			System.out.println("customer " + customer.getId() + " deleted");
		} else {
			throw new ObjectDontExistException();
		}
	}

	/**
	 * this method update customer, can update only password
	 * 
	 * @param customer
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public void updateCustomer(Customer customer) throws ObjectDontExistException, DbException {
		Customer customerdb = customerDAO.getCustomer(customer.getId());
		if (customerdb != null) {
			customerdb.setPassword(customer.getPassword());
			if(customer.getPassword().length()>=5) {
				if(customer.getPassword().length()<=8) {
					customerDAO.updateCustomer(customerdb);
					System.out.println("customer " + customer.getId() + " updated");
				} else {
					System.out.println("password too long");
				}
			} else {
				System.out.println("password too short");
			}
		} else {
			throw new ObjectDontExistException();
		}
	}

	/**
	 * this method get all customers
	 * 
	 * @return all customers
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Collection<Customer> getAllCustomer() throws ObjectDontExistException, DbException {
		Collection<Customer> allCustomers = new ArrayList<>();
		allCustomers = customerDAO.getAllCustomer();
		if (!allCustomers.isEmpty()) {
			System.out.println(allCustomers.toString());
			return allCustomers;
		} else {
			throw new ObjectDontExistException("there are no customers");
		}
	}

	/**
	 * this method get customer by id
	 * 
	 * @param id
	 * @return customer
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Customer getCustomer(long id) throws ObjectDontExistException, DbException {
		Customer customer = customerDAO.getCustomer(id);
		if (customer != null) {
			System.out.println(customer.toString());
			return customer;
		} else {
			throw new ObjectDontExistException();
		}
	}

}
