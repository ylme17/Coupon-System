package create.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB {
	public static void main(String[] args) {
		Connection con=null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			System.out.println("driver loaded");
			String url="jdbc:derby://localhost:1527/CouponSysdb;create=true";
			con=DriverManager.getConnection(url);
			System.out.println("connection established "+con);
			System.out.println("db created");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("connection closed "+con);
		}
	}

}
