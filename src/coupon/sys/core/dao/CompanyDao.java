package coupon.sys.core.dao;

import java.util.Collection;
import java.util.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.exceptions.DbException;

/**
 * this is interface for all company methods
 * 
 * @author YECHIEL.MOSHE
 *
 */
public interface CompanyDao {

	/**
	 * this method create a company
	 */
	public void createCompany(Company company) throws DbException;

	/**
	 * this method remove the company
	 */
	public void removeCompany(Company company) throws DbException;

	/**
	 * this method update the company
	 */
	public void updateCompany(Company company) throws DbException;

	/**
	 * this method get specific company by id
	 */
	public Company getCompany(long id) throws DbException;

	/**
	 * this method get all companies
	 */
	public Collection<Company> getAllCompanies() throws DbException;

	/**
	 * this method get all coupons by company
	 */
	public Collection<Coupon> getCoupons(Company company) throws DbException;

	/**
	 * this method use to login for company client
	 */
	public boolean login(String name, String password) throws DbException;

	/**
	 * this method check if company exist in db
	 */
	public boolean checkIfExist(Company company) throws DbException;

	/**
	 * this method check if company exist in db with same name of customer
	 */
	public boolean checkIfExist(Customer customer) throws DbException;

	/**
	 * this method remove the coupon from company-coupon table
	 */
	public void removeCompanyCoupon(Company company) throws DbException;

	/**
	 * this method insert coupon to company-coupon table when coupon created
	 */
	public void insertCouponCreation(long companyId, long couponId) throws DbException;

	/**
	 * this method check if coupon belong to specific company
	 */
	public boolean couponBelongComapny(long companyId, long couponId) throws DbException;

	/**
	 * this method get the coupon by type and company
	 */
	public Collection<Coupon> getCouponByType(Company company, CouponType type) throws DbException;

	/**
	 * this method get specific company by name
	 */
	public Company getCompany(String name) throws DbException;

	/**
	 * this method get coupons per company by price inside collection
	 */
	public Collection<Coupon> getCouponsByPrice(Company company, double Price) throws DbException;

	/**
	 * this method get coupons by date for company inside collection
	 */
	public Collection<Coupon> getCouponsByStartDate(Company company, Date date) throws DbException;

}
