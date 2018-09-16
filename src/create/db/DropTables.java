package create.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * this class drop tables
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class DropTables {

	public static void dropAllTables() {

		String url = "jdbc:derby://localhost:1527/CouponSysdb";

		try (Connection con = DriverManager.getConnection(url)) {
			Statement st = con.createStatement();

			String dropCompanyTableSql = "DROP TABLE company";
			st.execute(dropCompanyTableSql);

			String dropCustomerTableSql = "DROP TABLE customer";
			st.execute(dropCustomerTableSql);

			String dropCouponTableSql = "DROP TABLE coupon";
			st.execute(dropCouponTableSql);

			String dropCompanyCouponTableSql = "DROP TABLE company_coupon";
			st.execute(dropCompanyCouponTableSql);

			String dropCustomerCouponTableSql = "DROP TABLE customer_coupon";
			st.execute(dropCustomerCouponTableSql);

			st.close();
			System.out.println("tables deleted");
		} catch (SQLException e) {
			System.out.println("tables not deleted - " + e);
		}

	}
}
