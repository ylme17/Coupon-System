package coupon.sys.core.thread;

import java.util.Collection;
import java.util.Date;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.exceptions.CouponSystemException;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDao couponDaoDb;
	private boolean quit;
	private Collection<Coupon> couponToDelete;

	public DailyCouponExpirationTask() {
		this.quit = false;
	}

	@Override
	public void run() {
		try {
			couponDaoDb = new CouponDaoDb();
			if (!this.quit) {
				Date date = new Date();
				this.couponToDelete = couponDaoDb.getCouponUpToDate(date);
				if (this.couponToDelete != null) {
					for (Coupon coupon : couponToDelete) {
						this.couponDaoDb.removeCoupon(coupon);
					}
				}
			}
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage() + ", failed to remove old coupons");
		}
	}

	public void stopTask() {
		this.quit = true;
	}

}
