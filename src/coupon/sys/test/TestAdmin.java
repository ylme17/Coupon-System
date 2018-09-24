package coupon.sys.test;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.couponSystem.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import create.db.CreateTables;
import create.db.DropTables;

public class TestAdmin {

	public static void main(String[] args) throws CouponSystemException, InterruptedException {

//		DropTables.dropAllTables();
//		CreateTables.createTables();

		try {
			CouponSystem couponSystem = CouponSystem.getInstance();

			AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234");

			adminFacade.createCompany(new Company(TestData.companyNameTeva, TestData.companyPasswordTeva, TestData.companyEmailTeva));
			adminFacade.createCompany(new Company(TestData.companyNameCheckPoint, TestData.companyPasswordCheckPoint, TestData.companyEmailCheckPoint));
			adminFacade.createCompany(new Company(TestData.companyNameZIM, TestData.companyPasswordZIM, TestData.companyEmailZIM));
//
//			adminFacade.removeCompany(new Company(5));
//
//			adminFacade.updateCompany(new Company("zim", "6337485", "zimShipping@gmail"));
//
//			adminFacade.getAllCompanies();
//
//			adminFacade.getCompany(4);
//
			adminFacade.createCustomer(new Customer(TestData.customerNameAvi, TestData.customerPasswordAvi));
			adminFacade.createCustomer(new Customer(TestData.customerNameYossi, TestData.customerPasswordYossi));
			adminFacade.createCustomer(new Customer(TestData.customerNameDavid, TestData.customerPasswordDavid));
//
//			adminFacade.removeCustomer(new Customer(4));
//
//			adminFacade.updateCustomer(new Customer("david", "2939156"));
//
//			adminFacade.getAllCustomers();
//
//			adminFacade.getCustomer(2);

		} catch (CouponSystemException e) {
			System.out.println(e);
		}

	}

}
