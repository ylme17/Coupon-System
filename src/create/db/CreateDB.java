package create.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB {
	public static void main(String[] args) throws SQLException {
		Connection con=null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			System.out.println("driver loaded");
			String url="jdbc:derby://localhost:1527/CouponSysdb;create=true";
			con=DriverManager.getConnection(url);
			System.out.println("connection established");
			System.out.println("db created");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("db not created");
		}finally {
			if(con!=null) {
				con.close();
			}
			System.out.println("connection closed");
		}
	}

}
