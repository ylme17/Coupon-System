package coupon.sys.core.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coupon.sys.core.exceptions.ConnectionPoolException;

public class ConnectionPool {
	
	private Set<Connection> connections=new HashSet<>();
	private Set<Connection> connectionsBackup=new HashSet<>();
	public static final int POOL_SIZE=10;
	private String url="jdbc:derby://localhost:1527/CouponSysdb";
	private static ConnectionPool instance;
	
	public ConnectionPool() throws ConnectionPoolException {
		try {
			for (int i = 0; i < POOL_SIZE; i++) {
				Connection con=DriverManager.getConnection(url);
				connections.add(con);
				connectionsBackup.add(con);
			}
		} catch (SQLException e) {
			throw new ConnectionPoolException("connection pool initialization error", e);
		}
	}
	
	public synchronized static ConnectionPool getInstance() throws ConnectionPoolException {
		if(instance==null) {
			instance=new ConnectionPool();
		}
		return instance;
	}

	public synchronized Connection getConnection() throws ConnectionPoolException {
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new ConnectionPoolException();
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
	
	//change
	public synchronized void closeAllConnections() throws ConnectionPoolException{
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ConnectionPoolException("connection pool shutdown error", e);
			}
		}
	}

}
