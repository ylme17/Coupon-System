package coupon.sys.test;

import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.dao.db.CouponDaoDb;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.thread.DailyCouponExpirationTask;

public class TestCouponDao {

	public static void main(String[] args) throws CouponSystemException {
		
		CouponDao cou1=new CouponDaoDb();
		DailyCouponExpirationTask task=new DailyCouponExpirationTask();
		
		//create
//		cou1.createCoupon(new Coupon(1, "very good coupon", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 10, CouponType.FOOD, "buy food", 50, "xy"));
//		cou1.createCoupon(new Coupon(2, "the cheepest", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 7, CouponType.CAMPING, "lets camp", 100, "xy"));
//		cou1.createCoupon(new Coupon(5, "great coupon", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 30, CouponType.RESTURANTS, "meat fans", 300, "jpg"));
//		cou1.createCoupon(new Coupon(6, "best", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 18, CouponType.ELECTRICITY, "machine", 1500, "png"));
//		cou1.createCoupon(new Coupon(7, "camping awasome", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 25, CouponType.CAMPING, "camp", 50, "xy"));
//		cou1.createCoupon(new Coupon(8, "breakfast", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 15, CouponType.RESTURANTS, "breakfast and coffee", 100, "jpg"));
//		cou1.createCoupon(new Coupon(9, "lunch", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 20, CouponType.RESTURANTS, "lunch", 150, "jpg"));
//		cou1.createCoupon(new Coupon(10, "dinner", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 32, CouponType.RESTURANTS, "light dinner", 40, "jpg"));
//		cou1.createCoupon(new Coupon(11, "microwave", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 49, CouponType.ELECTRICITY, "great microwave", 500, "png"));
//		cou1.createCoupon(new Coupon(12, "dishwasher", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 20, CouponType.ELECTRICITY, "stop do dishes", 1000, "png"));
//		cou1.createCoupon(new Coupon(13, "bands", CurrentDate.getCurrentDate(), CurrentDate.getDateInWeek(), 100, CouponType.HEALTH, "first aid", 20, "png"));
//		cou1.createCoupon(new Coupon(14, "bat", CurrentDate.getCurrentDate(), CurrentDate.getDateInWeek(), 50, CouponType.SPORTS, "baseball", 150, "png"));
//		cou1.createCoupon(new Coupon(15, "vacation", CurrentDate.getCurrentDate(), new GregorianCalendar(2018, Calendar.SEPTEMBER, 18).getTime(), 20, CouponType.TRAVELLING, "go travel", 2000, "png"));
		
		//remove
//		cou1.removeCoupon(new Coupon(4));
		
		//update
//		cou1.updateCoupon(new Coupon(5, "great coupon", CurrentDate.getCurrTime(), CurrentDate.getCurrTime(), 30, CouponType.RESTURANTS, "eat meat", 200, "jpg"));
		
		//get
//		cou1.getCoupon(6);
		
		//get all
//		cou1.getAllCoupon();
		
		//get by type
//		cou1.getCouponByType(CouponType.RESTURANTS);
		
		//check exist
//		cou1.checkIfExist(new Coupon("dishwasher"));
		
		//remove coupons by company
//		cou1.removeCouponByCompany(new Company(2));
		
		//get by date
//		cou1.getCouponByDate(new GregorianCalendar(2018, Calendar.SEPTEMBER, 8).getTime());
//		cou1.getCouponByDate(CurrentDate.getCurrentDate());
		
		//thread
		task.run();

	}

}
