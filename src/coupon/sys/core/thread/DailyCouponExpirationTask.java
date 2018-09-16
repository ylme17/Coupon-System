package coupon.sys.core.thread;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.exceptions.DbException;
import coupon.sys.core.util.CurrentDate;

/**
 * this class for delete expired coupons with daily task
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class DailyCouponExpirationTask implements Runnable {

	private CouponDao couponDao;
	private boolean quit;
	private Collection<Coupon> expiredCoupons;

	/**
	 * the constructor of the class defined the variable quit as false
	 */
	public DailyCouponExpirationTask() {
		quit = false;
	}

	/**
	 * this method implements run method from runnable interface and run the task
	 * first check if quit is false second get all expired coupons inside collection
	 * third delete the coupons from all tables the method sleep for 24 hours
	 */
	@Override
	public void run() {
		try {
			couponDao = new CouponDaoDb();
			if (!quit) {
				expiredCoupons = couponDao.getCouponByDate(CurrentDate.getCurrentDate());
				if (!expiredCoupons.isEmpty()) {
					for (Coupon coupon : expiredCoupons) {
						couponDao.removeCustomerCoupon(coupon);
						couponDao.removeCompanyCoupon(coupon);
						couponDao.removeCoupon(coupon);
					}
				}
			}
			Thread.sleep(1000 * 60 * 60 * 24);
		} catch (DbException e) {
			System.out.println(e + ", failed to remove expired coupons");
		} catch (InterruptedException e) {
			System.out.println("daily expiration task stop working");
		}
	}

	/**
	 * this method stop the task when defined the variable quit as true
	 */
	public void stopTask() {
		quit = true;
	}

}
