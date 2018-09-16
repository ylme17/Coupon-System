package coupon.sys.test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.couponSystem.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.util.CurrentDate;

public class TestCompany {

	public static void main(String[] args) throws CouponSystemException {

		try {
			CouponSystem couponSystem = CouponSystem.getInstance();

			CompanyFacade companyFacade1 = (CompanyFacade) couponSystem.login("teva", "765");

			companyFacade1.createCoupon(new Coupon(21, "camping", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 18).getTime(), 25, CouponType.CAMPING, "camp", 50, "png"));
			companyFacade1.createCoupon(new Coupon(22, "breakfast", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 20).getTime(), 10, CouponType.RESTURANTS, "breakfast and coffee", 100, "png"));
			companyFacade1.createCoupon(new Coupon(23, "lunch", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 25).getTime(), 20, CouponType.RESTURANTS, "lunch", 150, "png"));

			companyFacade1.getCouponByType(CouponType.RESTURANTS);
			companyFacade1.getCouponByPrice(120);

			CompanyFacade companyFacade2 = (CompanyFacade) couponSystem.login("zim", "635");

			companyFacade2.createCoupon(new Coupon(24, "dinner", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 17).getTime(), 32, CouponType.RESTURANTS, "light dinner", 40, "png"));
			companyFacade2.createCoupon(new Coupon(25, "microwave", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.OCTOBER, 2).getTime(), 49, CouponType.ELECTRICITY,	"great microwave", 500, "png"));
			companyFacade2.createCoupon(new Coupon(26, "dishwasher", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 22).getTime(), 20, CouponType.ELECTRICITY, "stop do dishes", 1000, "png"));

			companyFacade2.getCouponByStartDate(new GregorianCalendar(2018, Calendar.SEPTEMBER, 13).getTime());
			companyFacade2.removeCoupon(new Coupon(25));

			CompanyFacade companyFacade3 = (CompanyFacade) couponSystem.login("isracard", "12345");

			companyFacade3.createCoupon(new Coupon(27, "vacation", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 20).getTime(), 20, CouponType.TRAVELLING, "go travel", 2000, "png"));

			companyFacade3.getCoupon(27);
			companyFacade3.getCompanyInfo();

			CompanyFacade companyFacade4 = (CompanyFacade) couponSystem.login("shufersal", "795");

			companyFacade4.createCoupon(new Coupon(28, "bands", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 19).getTime(), 100, CouponType.HEALTH, "first aid", 20, "png"));
			companyFacade4.createCoupon(new Coupon(29, "bat", CurrentDate.getCurrentDate(),
					new GregorianCalendar(2018, Calendar.SEPTEMBER, 21).getTime(), 50, CouponType.SPORTS, "baseball", 150, "png"));

			companyFacade4.updateCoupon(new Coupon(28, new GregorianCalendar(2018, Calendar.SEPTEMBER, 25).getTime(), 15));
			companyFacade4.getAllCoupon();

		} catch (CouponSystemException e) {
			System.out.println(e);
		}

	}

}
