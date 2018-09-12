package create.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * this class create tables
 * @author YECHIEL.MOSHE
 *
 */
public class CreateTable {
	public static void main(String[] args) {
		
		String url="jdbc:derby://localhost:1527/CouponSysdb";
		
		try(Connection con=DriverManager.getConnection(url)){
			
			Statement stmt=con.createStatement();
			
			String CompanyTable="CREATE TABLE company(id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, company_name VARCHAR(50), "
					+ "password VARCHAR(50), email VARCHAR(50))";
			stmt.execute(CompanyTable);
			
			String CustomerTable="CREATE TABLE customer(id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, customer_name VARCHAR(50), "
					+ "password VARCHAR(50))";
			stmt.execute(CustomerTable);
			
			String CouponTable="CREATE TABLE coupon(id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, title VARCHAR(50), start_date DATE, "
					+ "end_date DATE, amount INTEGER, type VARCHAR(50), message VARCHAR(50), price DOUBLE, image VARCHAR(50))";
			stmt.execute(CouponTable);
			
			String CustomerCouponTable="CREATE TABLE customer_coupon(customer_id BIGINT, coupon_id BIGINT, "
					+ "PRIMARY KEY(customer_id, coupon_id))";
			stmt.execute(CustomerCouponTable);
			
			String CompanyCouponTable="CREATE TABLE company_coupon(company_id BIGINT, coupon_id BIGINT, "
					+ "PRIMARY KEY(company_id, coupon_id))";
			stmt.execute(CompanyCouponTable);
			
			System.out.println("tables created");
		}catch(SQLException e){
			System.out.println("tables not created");
		}
	}

}
