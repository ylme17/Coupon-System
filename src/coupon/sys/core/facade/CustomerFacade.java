package coupon.sys.core.facade;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.exceptions.DbException;
import coupon.sys.core.exceptions.ObjectAlreadyExistException;
import coupon.sys.core.exceptions.ObjectDontExistException;
import coupon.sys.core.util.CurrentDate;

/**
 * this class implements the business logic of customer
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class CustomerFacade implements ClientFacade {

	private Customer customer;
	private CustomerDao customerDao;
	private CouponDao couponDao;

	/**
	 * construct the customer facade and get customer, customerDao and couponDao
	 * 
	 * @param customerDao
	 * @param couponDao
	 * @param customer
	 */
	public CustomerFacade(CustomerDao customerDao, CouponDao couponDao, Customer customer) {
		this.customerDao = customerDao;
		this.couponDao = couponDao;
		this.customer = customer;
	}

	/**
	 * this method for purchase coupon by customer the method check if the coupon
	 * not purchased yet by customer and if the coupon out of stock and if the
	 * coupon not expired
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		Coupon coupondb = couponDao.getCoupon(coupon.getId());
		if (coupondb != null) {
			if (customerDao.alreadyPurchased(customer.getId(), coupondb.getId()) == false) {
				if (coupondb.getAmount() >= 1) {
					if (CurrentDate.getCurrentDate().before(coupondb.getEndDate())) {
						customerDao.insertCouponPurchase(customer.getId(), coupondb.getId());
						coupondb.setAmount(coupondb.getAmount() - 1);
						couponDao.updateCoupon(coupondb);
						System.out.println("coupon purchased");
					} else {
						throw new CouponSystemException("coupon already expired");
					}
				} else {
					throw new ObjectDontExistException("coupon is out of stock");
				}
			} else {
				throw new ObjectAlreadyExistException("coupon already purchased by the customer");
			}
		} else {
			throw new ObjectDontExistException("coupon not exist");
		}
	}

	/**
	 * this method get all purchased coupons for customer
	 * 
	 * @return purchased coupons
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Collection<Coupon> getAllPurchasedCoupons() throws ObjectDontExistException, DbException {
		Collection<Coupon> purchasedCoupons = customerDao.getCoupons(customer);
		if (!purchasedCoupons.isEmpty()) {
			System.out.println(purchasedCoupons.toString());
			return purchasedCoupons;
		} else {
			throw new ObjectDontExistException("customer not have coupons");
		}
	}

	/**
	 * this method get purchased coupons by type for customer
	 * 
	 * @return purchased coupons
	 * @param type coupon type from enum couponType
	 * @return purchased coupons by type
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type)
			throws ObjectDontExistException, DbException {
		Collection<Coupon> purchasedCouponByType = customerDao.getCouponsByType(customer, type);
		if (!purchasedCouponByType.isEmpty()) {
			System.out.println(purchasedCouponByType.toString());
			return purchasedCouponByType;
		} else {
			throw new ObjectDontExistException("no coupons of " + type);
		}
	}

	/**
	 * this method get purchased coupons by price for customer
	 * 
	 * @param price
	 * @return purchased coupons by price
	 * @throws ObjectDontExistException
	 * @throws DbException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws ObjectDontExistException, DbException {
		Collection<Coupon> purchasedCouponByPrice = customerDao.getCouponsByPrice(customer, price);
		if (!purchasedCouponByPrice.isEmpty()) {
			System.out.println(purchasedCouponByPrice.toString());
			return purchasedCouponByPrice;
		} else {
			throw new ObjectDontExistException("no coupons up to price " + price);
		}
	}

}
