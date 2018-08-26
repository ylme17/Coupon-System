	package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.exceptions.CouponSystemException;

public interface CustomerDao {
	
	public void createCustomer(Customer customer) throws CouponSystemException;
	
	public void removeCustomer(Customer customer) throws CouponSystemException;
	
	public void updateCustomer(Customer customer) throws CouponSystemException;
	
	public Customer getCustomer(long id) throws CouponSystemException;
	
	public Collection<Customer> getAllCustomer() throws CouponSystemException;
	
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException;
	
	public boolean login (String name, String password) throws CouponSystemException;
	
	public boolean alreadyPurchased(long customerId, long couponId) throws CouponSystemException;
	
	public void insertCouponPurchase(long customerId, long couponId) throws CouponSystemException;
	
	public Collection<Coupon> getCouponByType(Customer customer, CouponType couponType) throws CouponSystemException;
	
	public Collection<Coupon> getCouponUpToPrice(Customer customer, double upToPrice) throws CouponSystemException;
	
	public boolean checkIfExist(Customer customer) throws CouponSystemException;
	
	public void removeCustomerCoupon(Customer customer) throws CouponSystemException;
	
	public Customer getCustomer(String name) throws CouponSystemException;


}
