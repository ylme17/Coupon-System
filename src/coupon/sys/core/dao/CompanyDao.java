package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.exceptions.CouponSystemException;

public interface CompanyDao {
	
	public void createCompany(Company company) throws CouponSystemException;
	
	public void removeCompany(Company company) throws CouponSystemException;
	
	public void updateCompany(Company company) throws CouponSystemException;
	
	public Company getCompany(long id) throws CouponSystemException;
		
	public Collection<Company> getAllCompanies() throws CouponSystemException;

	public Collection<Coupon> getCoupons(Company company) throws CouponSystemException;
	
	public boolean login(String name, String password) throws CouponSystemException;
	
	public boolean checkIfExist(Company company) throws CouponSystemException;
	
	public void removeCompanyCoupon(Company company) throws CouponSystemException;
	
	public void insertCouponCreation(long companyId, long couponId) throws CouponSystemException;
	
	public boolean couponBelongComapny(long companyId, long couponId) throws CouponSystemException;
	
	public Collection<Coupon> getCouponByType(Company company, CouponType type) throws CouponSystemException;
	
	public Company getCompany(String name) throws CouponSystemException;

	
}
