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

			customerFacade1.purchaseCoupon(new Coupon(2));
			customerFacade1.purchaseCoupon(new Coupon(4));
			customerFacade1.purchaseCoupon(new Coupon(8));
			customerFacade1.purchaseCoupon(new Coupon(3));
			customerFacade1.purchaseCoupon(new Coupon(7));

//			customerFacade1.getAllPurchasedCoupons();

			//2nd customer
			CustomerFacade customerFacade2 = (CustomerFacade) couponSystem.login(TestData.customerNameDavid, TestData.customerPasswordDavid);

			customerFacade2.purchaseCoupon(new Coupon(9));
			customerFacade2.purchaseCoupon(new Coupon(4));
			customerFacade2.purchaseCoupon(new Coupon(5));
			customerFacade2.purchaseCoupon(new Coupon(2));
			customerFacade2.purchaseCoupon(new Coupon(8));
			customerFacade2.purchaseCoupon(new Coupon(1));

//			customerFacade2.getAllPurchasedCouponsByType(CouponType.RESTURANTS);

			//3rd customer
			CustomerFacade customerFacade3 = (CustomerFacade) couponSystem.login(TestData.customerNameAvi, TestData.customerPasswordAvi);

			customerFacade3.purchaseCoupon(new Coupon(2));
			customerFacade3.purchaseCoupon(new Coupon(6));
			customerFacade3.purchaseCoupon(new Coupon(9));

//			customerFacade3.getAllPurchasedCouponsByPrice(200);

		} catch (CouponSystemException e) {
			System.out.println(e);
		}
	}

}
