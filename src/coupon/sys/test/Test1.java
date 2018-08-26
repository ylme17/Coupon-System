package coupon.sys.test;

import coupon.sys.core.beans.Company;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class Test1 {

	public static void main(String[] args) throws CouponSystemException {
		
		Company company=new Company(1, "teva", "1234", "test");
		CompanyDao companyd = (CompanyDao) company;
		
		companyd.createCompany(company);

	}

}
