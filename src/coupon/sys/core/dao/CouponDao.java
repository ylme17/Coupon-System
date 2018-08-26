package coupon.sys.core.dao;

import java.util.Collection;
import java.util.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.exceptions.CouponSystemException;

public interface CouponDao {
	
	public void createCoupon(Coupon coupon) throws CouponSystemException;
	
	public void removeCoupon(Coupon coupon) throws CouponSystemException;
	
	public void updateCoupon(Coupon coupon) throws CouponSystemException;
	
	public Coupon getCoupon(long id) throws CouponSystemException;
	
	public Collection<Coupon> getAllCoupon() throws CouponSystemException;
	
	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException;
	
	public void removeCouponByCompany(Company company) throws CouponSystemException;

	public void removeCustomerCoupon(Company company) throws CouponSystemException;
	
	public boolean checkIfExist(Coupon coupon) throws CouponSystemException;
	
	public Collection<Coupon> getCouponUpToDate(Date date) throws CouponSystemException;

	
}
