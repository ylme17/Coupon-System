package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.exceptions.DbException;

/**
 * this is interface for all customer methods
 * 
 * @author YECHIEL.MOSHE
 *
 */
public interface CustomerDAO {

	/**
	 * this method create customer
	 */
	public void createCustomer(Customer customer) throws DbException;

	/**
	 * this method delete customer
	 */
	public void removeCustomer(Customer customer) throws DbException;

	/**
	 * this method update customer
	 */
	public void updateCustomer(Customer customer) throws DbException;

	/**
	 * this method get customer by id
	 */
	public Customer getCustomer(long id) throws DbException;

	/**
	 * this method get all customers inside collection
	 */
	public Collection<Customer> getAllCustomer() throws DbException;

	/**
	 * this method get all coupons per customer inside collection
	 */
	public Collection<Coupon> getCoupons() throws DbException;

	/**
	 * this is a login method for customer
	 */
	public boolean login(String name, String password) throws DbException;

	/**
	 * this method check if customer purchased the coupon already by customer id and
	 * coupon id with customer-coupon table
	 */
	public boolean alreadyPurchased(long couponId) throws DbException;

	/**
	 * this method insert the coupon purchase to customer-coupon table
	 */
	public void insertCouponPurchase(long couponId) throws DbException;

	/**
	 * this method get coupons per customer by type inside collection
	 */
	public Collection<Coupon> getCouponsByType(CouponType type) throws DbException;

	/**
	 * this method get coupons per customer by price inside collection
	 */
	public Collection<Coupon> getCouponsByPrice(double Price) throws DbException;

	/**
	 * this method check if customer already exist in db
	 */
	public boolean checkIfExist(Customer customer) throws DbException;

	/**
	 * this method check if customer already exist with same name of company in db
	 */
	public boolean checkIfExist(Company company) throws DbException;

	/**
	 * this method remove all coupons from customer-coupon table by customer
	 */
	public void removeCustomerCoupon(Customer customer) throws DbException;

//	/**
//	 * this method get customer by name
//	 */
//	public Customer getCustomer(String name) throws DbException;

}
