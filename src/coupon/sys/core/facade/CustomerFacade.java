package coupon.sys.core.facade;

import java.util.Calendar;
import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CustomerFacade implements ClientFacade {

	private Customer currCustomer;
	private CustomerDao customerDao;
	private CouponDao couponDao;

	public CustomerFacade(CustomerDao customerDao, CouponDao couponDao, Customer currCustomer) {
		this.customerDao = customerDao;
		this.couponDao = couponDao;
		this.currCustomer = currCustomer;
	}

	public void purchaseCoupon(Coupon coupon) throws CouponSystemException {
		try {
			Coupon coupondb = couponDao.getCoupon(coupon.getId());
			if (coupondb != null) {
				if (customerDao.alreadyPurchased(currCustomer.getId(), coupondb.getId()) == false) {
					if (coupondb.getAmount() > 0) {
						Calendar currDate = Calendar.getInstance();
						currDate.getTime();
						if (currDate.before(coupondb.getEndDate())) {
							customerDao.insertCouponPurchase(currCustomer.getId(), coupondb.getId());
							coupondb.setAmount(coupondb.getAmount() - 1);
							couponDao.updateCoupon(coupondb);
						} else {
							throw new CouponSystemException("coupon has already expired");
						}
					} else {
						throw new CouponSystemException("coupon is out of stock");
					}
				} else {
					throw new CouponSystemException("coupon already purchased by the customer");
				}
			} else {
				throw new CouponSystemException("coupon does not exist");
			}
		} catch (CouponSystemException e) {
			throw new CouponSystemException("purchase has failed");
		}
	}

	public Collection<Coupon> getAllPurchasedCoupon() throws CouponSystemException{
		Collection<Coupon> purchasedCoupons = customerDao.getCoupons(this.currCustomer);
		if(purchasedCoupons!=null) {
			return purchasedCoupons;
		}else {
			throw new CouponSystemException("there aren't any coupon to the customer");
		}
	}

	public Collection<Coupon> getAllPurchasedCouponByType(CouponType couponType) throws CouponSystemException{
		Collection<Coupon> purchasedCouponByType = customerDao.getCouponByType(this.currCustomer, couponType);
		if(purchasedCouponByType!=null) {
			return purchasedCouponByType;
		}else {
			throw new CouponSystemException("there aren't coupons of "+couponType);
		}
	}

	public Collection<Coupon> getAllPurchasedCouponUpToPrice(double price) throws CouponSystemException{
		Collection<Coupon> purchasedCouponByPrice = customerDao.getCouponUpToPrice(this.currCustomer, price);
		if(purchasedCouponByPrice!=null) {
			return purchasedCouponByPrice;
		}else {
			throw new CouponSystemException("there aren't coupons up to price "+price);
		}
	}

}
