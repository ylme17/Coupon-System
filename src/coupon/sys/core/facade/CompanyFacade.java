package coupon.sys.core.facade;

import java.util.Collection;
import java.util.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.exceptions.DbException;
import coupon.sys.core.exceptions.ObjectAlreadyExistException;
import coupon.sys.core.exceptions.ObjectDontExistException;

/**
 * this class implements the business logic of company
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class CompanyFacade implements ClientFacade {

	private CouponDao couponDao;
	private CompanyDao companyDao;
	private Company company;

	/**
	 * construct the company facade and get company, companyDao and couponDao
	 * 
	 * @param couponDao
	 * @param companyDao
	 * @param company
	 */
	public CompanyFacade(CouponDao couponDao, CompanyDao companyDao, Company company) {
		this.couponDao = couponDao;
		this.companyDao = companyDao;
		this.company = company;
	}

	/**
	 * this method create coupon for company first check if coupon exist by title
	 * then create the coupon and insert to company-coupon
	 * 
	 * @param coupon
	 * @throws ObjectAlreadyExistException
	 * @throws DbException
	 */
	public void createCoupon(Coupon coupon) throws ObjectAlreadyExistException, DbException {
		if (couponDao.checkIfExist(coupon) == false) {
			couponDao.createCoupon(coupon);
			companyDao.insertCouponCreation(company.getId(), coupon.getId());
			System.out.println("coupon created [title: " + coupon.getTitle() + ", id: " + coupon.getId() + "]");
		} else {
			throw new ObjectAlreadyExistException("coupon '" + coupon.getTitle() + "', is already exist");
		}
	}

	/**
	 * this method remove coupon for company first check if coupon belong to company
	 * then remove the coupon from customer-coupon and company-coupon tables then
	 * remove the coupon itself
	 * 
	 * @param coupon
	 * @throws DbException
	 * @throws ObjectDontExistException
	 */
	public void removeCoupon(Coupon coupon) throws ObjectDontExistException, DbException {
		if (companyDao.couponBelongComapny(company.getId(), coupon.getId())) {
			couponDao.removeCustomerCoupon(coupon);
			couponDao.removeCompanyCoupon(coupon);
			couponDao.removeCoupon(coupon);
			System.out.println("coupon " + coupon.getId() + " deleted");
		} else {
			throw new ObjectDontExistException("coupon not belong to company " + company.getName());
		}
	}

	/**
	 * this method update coupon only for end-date and price
	 * 
	 * @param coupon
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public void updateCoupon(Coupon coupon) throws ObjectDontExistException, DbException {
		Coupon coupondb = couponDao.getCoupon(coupon.getId());
		if (companyDao.couponBelongComapny(company.getId(), coupondb.getId())) {
			coupondb.setEndDate(coupon.getEndDate());
			coupondb.setPrice(coupon.getPrice());
			couponDao.updateCoupon(coupondb);
			System.out.println("coupon " + coupon.getId() + " updated");
		} else {
			throw new ObjectDontExistException(
					"coupon " + coupon.getId() + " not belong to company '" + company.getName() + "'");
		}
	}

	/**
	 * this method get coupon for company
	 * 
	 * @param id
	 * @return coupon
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Coupon getCoupon(long id) throws ObjectDontExistException, DbException {
		Coupon coupon = couponDao.getCoupon(id);
		if (companyDao.couponBelongComapny(company.getId(), id)) {
			System.out.println(coupon.toString());
			return coupon;
		} else {
			throw new ObjectDontExistException(
					"coupon " + coupon.getId() + " not belong to company '" + company.getName() + "'");
		}
	}

	/**
	 * this method get all coupons for company
	 * 
	 * @return
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Collection<Coupon> getAllCoupon() throws ObjectDontExistException, DbException {
		Collection<Coupon> allCoupon = companyDao.getCoupons(company);
		if (!allCoupon.isEmpty()) {
			System.out.println(allCoupon.toString());
			return allCoupon;
		} else {
			throw new ObjectDontExistException("there are no coupons");
		}
	}

	/**
	 * this method get coupons by type
	 * 
	 * @param type
	 * @return coupons by type
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Collection<Coupon> getCouponByType(CouponType type) throws ObjectDontExistException, DbException {
		Collection<Coupon> couponByType = companyDao.getCouponByType(company, type);
		if (!couponByType.isEmpty()) {
			System.out.println(couponByType.toString());
			return couponByType;
		} else {
			throw new ObjectDontExistException("no coupons of type " + type);
		}
	}

	/**
	 * this method get company info
	 * 
	 * @return company information
	 * @throws DbException
	 */
	public Company getCompanyInfo() throws DbException {
		Company companyInfo = companyDao.getCompany(company.getId());
		System.out.println(companyInfo);
		return companyInfo;
	}

	/**
	 * this method get coupons by price for company
	 * 
	 * @param price
	 * @return coupons by price
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Collection<Coupon> getCouponByPrice(double price) throws ObjectDontExistException, DbException {
		Collection<Coupon> couponByPrice = companyDao.getCouponsByPrice(company, price);
		if (!couponByPrice.isEmpty()) {
			System.out.println(couponByPrice.toString());
			return couponByPrice;
		} else {
			throw new ObjectDontExistException("no coupons up to price " + price);
		}
	}

	/**
	 * this method get coupons by start-date for company
	 * 
	 * @param date
	 * @return coupon up to date
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Collection<Coupon> getCouponByStartDate(Date date) throws ObjectDontExistException, DbException {
		Collection<Coupon> couponByDate = companyDao.getCouponsByStartDate(company, date);
		if (!couponByDate.isEmpty()) {
			System.out.println(couponByDate.toString());
			return couponByDate;
		} else {
			throw new ObjectDontExistException("no coupons up to date " + date);
		}
	}

}
