package coupon.sys.core.thread;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.exceptions.DbException;
import coupon.sys.core.util.CurrentDate;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDao couponDaoDb;
	private boolean quit;
	private Collection<Coupon> expiredCoupons;

	public DailyCouponExpirationTask() {
		this.quit = false;
	}

	@Override
	public void run() {
		try {
			couponDaoDb = new CouponDaoDb();
			if (!this.quit) {
				this.expiredCoupons = couponDaoDb.getCouponByDate(CurrentDate.getCurrentDate());
				if (this.expiredCoupons != null) {
					for (Coupon coupon : expiredCoupons) {
						this.couponDaoDb.removeCoupon(coupon);
					}
				}
			}
		} catch (DbException e) {
			System.out.println(e + ", failed to remove expired coupons");
		}
	}

	public void stopTask() {
		this.quit = true;
	}

}
