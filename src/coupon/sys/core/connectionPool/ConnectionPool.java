package coupon.sys.core.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coupon.sys.core.exceptions.CouponSystemException;

public class ConnectionPool {
	
	private Set<Connection> connections=new HashSet<>();
	private Set<Connection> connectionsBackup=new HashSet<>();
	public static final int POOL_SIZE=10;
	private String url="jdbc:derby://localhost:1527/CouponSysdb";
	private static ConnectionPool instance;
	
	public ConnectionPool() throws CouponSystemException {
		try {
			for (int i = 0; i < POOL_SIZE; i++) {
				Connection con=DriverManager.getConnection(url);
				connections.add(con);
				connectionsBackup.add(con);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("connection pool initialization error", e);
		}
	}
	
	public static ConnectionPool getInstance() {
		return instance;
	}

	public synchronized Connection getConnection() {
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Iterator<Connection> it=connections.iterator();
		Connection con =it.next();
		it.remove();
		return con;
	}
	
	public synchronized void returnConnection(Connection con) {
		connections.add(con);
		notifyAll();
	}
	
	public synchronized void closeAllConnections() throws CouponSystemException{
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponSystemException("connection pool shutdown error", e);
			}
		}
	}

}
