package coupon.sys.test;

import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.dao.db.CustomerDaoDb;
import coupon.sys.core.exceptions.DbException;

public class TestCustomerDao {

	public static void main(String[] args) throws DbException {
		
		CustomerDao cus1=new CustomerDaoDb();
		
		//create
//		cus1.createCustomer(new Customer(1, "Avi", "543"));
//		cus1.createCustomer(new Customer(2, "Asi", "76432"));
//		cus1.createCustomer(new Customer(4, "Moshe", "54953"));
//		cus1.createCustomer(new Customer(5, "David", "6243"));
		
		//remove
//		cus1.removeCustomer(new Customer(3));
		
		//update
//		cus1.updateCustomer(new Customer(4, "John", "12345"));
		
		//get by id
//		cus1.getCustomer(2);
		
		//get by name
//		cus1.getCustomer("David");
		
		//get all
//		cus1.getAllCustomer();
		
		//login
//		cus1.login("Avi", "12345");
		
		//exist
//		cus1.checkIfExist(new Customer("gtd"));
		
		//insert customer-coupon
//		cus1.insertCouponPurchase(1, 6);
//		cus1.insertCouponPurchase(1, 11);
//		cus1.insertCouponPurchase(1, 12);
//		cus1.insertCouponPurchase(4, 5);
//		cus1.insertCouponPurchase(4, 9);
//		cus1.insertCouponPurchase(4, 10);
//		cus1.insertCouponPurchase(5, 7);
//		cus1.insertCouponPurchase(5, 8);
		
		//get coupons
//		cus1.getCoupons(new Customer(1));
		
		//purchased
//		cus1.alreadyPurchased(5, 6);
		
		//coupons by type
//		cus1.getCouponByType(new Customer(4), CouponType.ELECTRICITY);
		
		//coupons up to price
//		cus1.getCouponUpToPrice(new Customer(1), 1200);
		
		//delete customer-coupon
		cus1.removeCustomerCoupon(new Customer(5));

	}

}
