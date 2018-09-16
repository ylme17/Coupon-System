package coupon.sys.test;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.couponSystem.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import create.db.CreateTables;
import create.db.DropTables;

public class TestAdmin {

	public static void main(String[] args) throws CouponSystemException {

//		DropTables.dropAllTables();
//		CreateTables.createTables();

		try {
			CouponSystem couponSystem = CouponSystem.getInstance();

			AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234");

			adminFacade.createCompany(new Company(1, "isracard", "12345", "isracard@gmail"));
			adminFacade.createCompany(new Company(2, "cal", "0987", "cal@gmail"));
			adminFacade.createCompany(new Company(3, "teva", "765", "teva@gmail"));
			adminFacade.createCompany(new Company(4, "checkpoint", "543", "checkpoint@gmail"));
			adminFacade.createCompany(new Company(5, "zim", "756", "zim@gmail"));
			adminFacade.createCompany(new Company(6, "shufersal", "795", "shufersal@gmail"));
			adminFacade.createCompany(new Company(7, "IAI", "1q2w3e", "IAI@gmail"));

			adminFacade.removeCompany(new Company(6));

			adminFacade.updateCompany(new Company(3, "teva", "934", "tevaIndustries@gmail"));
			adminFacade.updateCompany(new Company(5, "zim", "635", "zimShipping@gmail"));

			adminFacade.getAllCompanies();

			adminFacade.getCompany(4);

			adminFacade.createCustomer(new Customer(1, "avi", "9748"));
			adminFacade.createCustomer(new Customer(2, "yossi", "563"));
			adminFacade.createCustomer(new Customer(3, "david", "05t"));
			adminFacade.createCustomer(new Customer(4, "yoni", "h765"));

			adminFacade.removeCustomer(new Customer(4));

			adminFacade.updateCustomer(new Customer(2, "david", "2956"));

			adminFacade.getAllCustomer();

			adminFacade.getCustomer(2);

		} catch (CouponSystemException e) {
			System.out.println(e);
		}

	}

}
