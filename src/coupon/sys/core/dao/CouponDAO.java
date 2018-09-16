package coupon.sys.core.dao;

import java.util.Collection;
import java.util.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.exceptions.DbException;

/**
 * this is interface for all coupon methods
 * 
 * @author YECHIEL.MOSHE
 *
 */
public interface CouponDAO {

	/**
	 * this method create coupon
	 */
	public void createCoupon(Coupon coupon) throws DbException;

	/**
	 * this method remove coupon by id from coupon table
	 */
	public void removeCoupon(Coupon coupon) throws DbException;

	/**
	 * this method update the coupon
	 */
	public void updateCoupon(Coupon coupon) throws DbException;

	/**
	 * this method get coupon by id
	 */
	public Coupon getCoupon(long id) throws DbException;

	/**
	 * this method get all coupons
	 */
	public Collection<Coupon> getAllCoupon() throws DbException;

	/**
	 * this method get coupons by type
	 */
	public Collection<Coupon> getCouponByType(CouponType type) throws DbException;

	/**
	 * this method remove coupons from customer-coupon table by coupon id this
	 * method help to delete coupon
	 */
	public void removeCustomerCoupon(Coupon coupon) throws DbException;

	/**
	 * this method remove coupons from company-coupon table by coupon id this method
	 * help to delete coupon
	 */
	public void removeCompanyCoupon(Coupon coupon) throws DbException;

	/**
	 * this method remove coupons by company id, this method help to delete company
	 */
	public void removeCouponByCompany(Company company) throws DbException;

	/**
	 * this method remove coupons from customer-coupon table by company id this
	 * method help to delete company
	 */
	public void removeCustomerCoupon(Company company) throws DbException;

	/**
	 * this method check if coupon exist by title to create one
	 */
	public boolean checkIfExist(Coupon coupon) throws DbException;

	/**
	 * this method get coupons by date inside collection
	 */
	public Collection<Coupon> getCouponByDate(Date date) throws DbException;

}
