package create.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropDB {

	public static void main(String[] args) {
		
		String url="jdbc:derby://localhost:1527/CouponSysdb";
		
		try(Connection con=DriverManager.getConnection(url)){
			String dropDbSql="DROP DATABASE CouponSysdb";
			Statement st=con.createStatement();
			st.execute(dropDbSql);
			st.close();
			System.out.println("db deleted");
		} catch(SQLException e) {
			System.out.println("db not deleted - "+e);
		}

	}
}
