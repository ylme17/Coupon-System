package coupon.sys.test;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.couponSystem.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.CustomerFacade;

public class TestCustomer {
	public static void main(String[] args) throws CouponSystemException {

		try {
			CouponSystem couponSystem = CouponSystem.getInstance();

			CustomerFacade customerFacade1 = (CustomerFacade) couponSystem.login("yossi", "563");

			customerFacade1.purchaseCoupon(new Coupon(21));
			customerFacade1.purchaseCoupon(new Coupon(22));
			customerFacade1.purchaseCoupon(new Coupon(24));
			customerFacade1.purchaseCoupon(new Coupon(27));
			customerFacade1.purchaseCoupon(new Coupon(23));

			customerFacade1.getAllPurchasedCoupons();

			CustomerFacade customerFacade2 = (CustomerFacade) couponSystem.login("david", "2956");

			customerFacade2.purchaseCoupon(new Coupon(29));
			customerFacade2.purchaseCoupon(new Coupon(23));
			customerFacade2.purchaseCoupon(new Coupon(27));
			customerFacade2.purchaseCoupon(new Coupon(21));
			customerFacade2.purchaseCoupon(new Coupon(26));
			customerFacade2.purchaseCoupon(new Coupon(24));

			customerFacade2.getAllPurchasedCouponsByType(CouponType.RESTURANTS);

			CustomerFacade customerFacade3 = (CustomerFacade) couponSystem.login("avi", "9748");

			customerFacade3.purchaseCoupon(new Coupon(27));
			customerFacade3.purchaseCoupon(new Coupon(21));
			customerFacade3.purchaseCoupon(new Coupon(29));

			customerFacade3.getAllPurchasedCouponsByPrice(200);

		} catch (CouponSystemException e) {
			System.out.println(e);
		}
	}

}
