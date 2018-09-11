package coupon.sys.test;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.dao.db.CompanyDaoDb;
import coupon.sys.core.exceptions.CouponSystemException;

public class TestCompanyDao {

	public static void main(String[] args) throws CouponSystemException {
		
		CompanyDao comp1=new CompanyDaoDb();
		//create
//		comp1.createCompany(new Company(1, "isracard", "12345", "isracard@isracard"));
//		comp1.createCompany(new Company(2, "cal", "0987", "cal@cal"));
//		comp1.createCompany(new Company(3, "teva", "765", "teva@teva"));
//		comp1.createCompany(new Company(4, "checkpoint", "543", "check@point"));
//		comp1.createCompany(new Company(5, "zim", "756", "zim@zim"));
//		comp1.createCompany(new Company(6, "shufersal", "795", "shufersal@shufersal"));

		//remove
//		comp1.removeCompany(new Company(7, "", "", ""));
//		comp1.removeCompany(new Company(8, "", "", ""));

		//update
//		comp1.updateCompany(new Company(7, "cal", "54321", "cal@cal"));
		
		//get by id
//		comp1.getCompany(4);
		
		//get by name
		comp1.getCompany("checkpoint");
		
		//get all
//		comp1.getAllCompanies();
		
		//login
//		comp1.login("checkpoint", "543");
		
		//exist
//		comp1.checkIfExist(new Company("ikea"));
		
		//insert company-coupon
//		comp1.insertCouponCreation(2, 1);
//		comp1.insertCouponCreation(2, 2);
//		comp1.insertCouponCreation(4, 5);
//		comp1.insertCouponCreation(4, 6);
//		comp1.insertCouponCreation(5, 7);
//		comp1.insertCouponCreation(3, 8);
//		comp1.insertCouponCreation(3, 9);
//		comp1.insertCouponCreation(4, 10);
//		comp1.insertCouponCreation(5, 11);
//		comp1.insertCouponCreation(6, 12);
		
		//coupons by company
//		comp1.getCoupons(new Company(1));
//		comp1.getCoupons(new Company(2));
//		comp1.getCoupons(new Company(3));
//		comp1.getCoupons(new Company(4));
//		comp1.getCoupons(new Company(5));
//		comp1.getCoupons(new Company(6));
		
		//belong to company
//		comp1.couponBelongComapny(4, 5);
		
		//coupons by type for company
//		comp1.getCouponByType(new Company(3), CouponType.RESTURANTS);
		
		//remove company-coupon
//		comp1.removeCompanyCoupon(new Company(2));

	}

}
