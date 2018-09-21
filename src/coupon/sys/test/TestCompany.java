package coupon.sys.test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.couponSystem.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.utils.CurrentDate;

public class TestCompany {

	public static void main(String[] args) throws CouponSystemException {

		try {
			CouponSystem couponSystem = CouponSystem.getInstance();

			//1st company
			CompanyFacade companyFacade1 = (CompanyFacade) couponSystem.login(TestData.companyNameTeva, TestData.companyPasswordTeva);

			companyFacade1.createCoupon(new Coupon(10, "camping", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 25).getTime(), 25, CouponType.CAMPING, "camp", 50, "png"));
			companyFacade1.createCoupon(new Coupon(11, "breakfast", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 27).getTime(), 10, CouponType.RESTURANTS, "breakfast and coffee", 100, "png"));
			companyFacade1.createCoupon(new Coupon(12, "lunch", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 30).getTime(), 20, CouponType.RESTURANTS, "lunch", 150, "png"));
			companyFacade1.createCoupon(new Coupon(13, "bat", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 24).getTime(), 50, CouponType.SPORTS, "baseball", 150, "png"));

//			companyFacade1.getCouponByType(CouponType.RESTURANTS);
//			companyFacade1.getCouponByPrice(120);
//			companyFacade1.getAllCoupon();

			//2nd company
			CompanyFacade companyFacade2 = (CompanyFacade) couponSystem.login(TestData.companyNameCheckPoint, TestData.companyPasswordCheckPoint);

			companyFacade2.createCoupon(new Coupon(14, "dinner", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 23).getTime(), 32, CouponType.RESTURANTS, "light dinner", 40, "png"));
			companyFacade2.createCoupon(new Coupon(15, "microwave", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.OCTOBER, 2).getTime(), 49, CouponType.ELECTRICITY,	"great microwave", 500, "png"));
			companyFacade2.createCoupon(new Coupon(16, "dishwasher", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 28).getTime(), 20, CouponType.ELECTRICITY, "stop do dishes", 1000, "png"));

//			companyFacade2.getCouponByStartDate(new GregorianCalendar(2018, Calendar.SEPTEMBER, 13).getTime());
//			companyFacade2.removeCoupon(new Coupon(25));
//			companyFacade2.updateCoupon(new Coupon(28, new GregorianCalendar(2018, Calendar.SEPTEMBER, 25).getTime(), 15));
			
			//3rd company
			CompanyFacade companyFacade3 = (CompanyFacade) couponSystem.login(TestData.companyNameZIM, TestData.companyPasswordZIM);

			companyFacade3.createCoupon(new Coupon(17, "vacation", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 30).getTime(), 20, CouponType.TRAVELLING, "go travel", 2000, "png"));
			companyFacade3.createCoupon(new Coupon(18, "bands", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 27).getTime(), 100, CouponType.HEALTH, "first aid", 20, "png"));

//			companyFacade3.getCoupon(27);
//			companyFacade3.getCompanyInfo();


		} catch (CouponSystemException e) {
			System.out.println(e);
		}

	}

}
