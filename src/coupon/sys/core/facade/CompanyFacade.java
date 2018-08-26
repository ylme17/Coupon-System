package coupon.sys.core.facade;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CompanyFacade implements ClientFacade {

	private CouponDao couponDao;
	private CompanyDao companyDao;
	private Company company;

	public CompanyFacade(CouponDao couponDao, CompanyDao companyDao, Company company) {
		this.couponDao = couponDao;
		this.companyDao = companyDao;
		this.company = company;
	}

	public void createCoupon(Coupon coupon) throws CouponSystemException {
		if(couponDao.checkIfExist(coupon)==false) {
			couponDao.createCoupon(coupon);
			companyDao.insertCouponCreation(company.getId(), coupon.getId());
		}else {
			throw new CouponSystemException("coupon '"+coupon.getTitle()+"', is already exist");
		}
	}

	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		try {
			if(companyDao.couponBelongComapny(this.company.getId(), coupon.getId())) {
				couponDao.removeCoupon(coupon);
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException("coupon "+coupon.getTitle()+" doesn't belong to company '"+this.company.getName()+"'");
		}
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		try {
			if(companyDao.couponBelongComapny(this.company.getId(), coupon.getId())) {
				couponDao.updateCoupon(coupon);
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException("coupon "+coupon.getTitle()+" doesn't belong to company '"+this.company.getName()+"'");
		}
	}

	public Coupon getCoupon(long id) throws CouponSystemException {
		Coupon coupon = couponDao.getCoupon(id);
		if(companyDao.couponBelongComapny(this.company.getId(), id)) {
			couponDao.updateCoupon(coupon);
		}else {
			throw new CouponSystemException("coupon "+coupon.getId()+" doesn't belong to company '"+this.company.getName()+"'");
		}
		return coupon;
	}

	public Collection<Coupon> getAllCoupon() throws CouponSystemException {
		Collection<Coupon> allCoupon = companyDao.getCoupons(this.company);
		if(allCoupon!=null) {
			return allCoupon;
		}else {
			throw new CouponSystemException("there are no coupons");
		}
	}

	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException {
		Collection<Coupon> couponByType = companyDao.getCouponByType(this.company, type);
		if(couponByType!=null) {
			return couponByType;
		}else {
			throw new CouponSystemException("there are no coupons of type "+type);
		}
	}

}
