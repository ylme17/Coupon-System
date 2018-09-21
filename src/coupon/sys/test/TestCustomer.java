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

			//1st customer
			CustomerFacade customerFacade1 = (CustomerFacade) couponSystem.login(TestData.customerNameYossi, TestData.customerPasswordYossi);

			customerFacade1.purchaseCoupon(new Coupon(11));
			customerFacade1.purchaseCoupon(new Coupon(12));
			customerFacade1.purchaseCoupon(new Coupon(16));
			customerFacade1.purchaseCoupon(new Coupon(14));
			customerFacade1.purchaseCoupon(new Coupon(18));

//			customerFacade1.getAllPurchasedCoupons();

			//2nd customer
			CustomerFacade customerFacade2 = (CustomerFacade) couponSystem.login(TestData.customerNameDavid, TestData.customerPasswordDavid);

			customerFacade2.purchaseCoupon(new Coupon(13));
			customerFacade2.purchaseCoupon(new Coupon(15));
			customerFacade2.purchaseCoupon(new Coupon(17));
			customerFacade2.purchaseCoupon(new Coupon(14));
			customerFacade2.purchaseCoupon(new Coupon(11));
			customerFacade2.purchaseCoupon(new Coupon(16));

//			customerFacade2.getAllPurchasedCouponsByType(CouponType.RESTURANTS);

			//3rd customer
			CustomerFacade customerFacade3 = (CustomerFacade) couponSystem.login(TestData.customerNameAvi, TestData.customerPasswordAvi);

			customerFacade3.purchaseCoupon(new Coupon(16));
			customerFacade3.purchaseCoupon(new Coupon(18));
			customerFacade3.purchaseCoupon(new Coupon(15));

//			customerFacade3.getAllPurchasedCouponsByPrice(200);

		} catch (CouponSystemException e) {
			System.out.println(e);
		}
	}

}
