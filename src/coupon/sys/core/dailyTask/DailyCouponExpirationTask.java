package coupon.sys.core.dailyTask;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.dao.CouponDAO;
import coupon.sys.core.dao.db.CouponDAODb;
import coupon.sys.core.exceptions.DbException;
import coupon.sys.core.utils.CurrentDate;

/**
 * this class for delete expired coupons with daily task
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class DailyCouponExpirationTask implements Runnable {

	private CouponDAO couponDAO;
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
			couponDAO = new CouponDAODb();
			if (!quit) {
				expiredCoupons = couponDAO.getCouponByDate(CurrentDate.getCurrentDate());
				if (!expiredCoupons.isEmpty()) {
					for (Coupon coupon : expiredCoupons) {
						couponDAO.removeCustomerCoupon(coupon);
						couponDAO.removeCompanyCoupon(coupon);
						couponDAO.removeCoupon(coupon);
					}
				}
			}
			Thread.sleep(1000 * 60 * 60 * 24);
		} catch (DbException e) {
			System.out.println(e + ", failed to remove expired coupons");
		} catch (InterruptedException | NullPointerException e) {
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
