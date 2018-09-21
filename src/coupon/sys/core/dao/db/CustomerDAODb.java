package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.connections.ConnectionPool;
import coupon.sys.core.dao.CustomerDAO;
import coupon.sys.core.exceptions.ConnectionPoolException;
import coupon.sys.core.exceptions.DbException;

/**
 * this class implements CutomerDAO interface
 * 
 * @author YECHIEL.MOSHE
 * 
 */
public class CustomerDAODb implements CustomerDAO {

	private Customer loggedInCustomer;
	private ConnectionPool connectionPool;

	/**
	 * this method create customer
	 */
	@Override
	public void createCustomer(Customer customer) throws DbException {
		Connection con = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String createCustomerSql = "INSERT INTO customer (customer_name, password) VALUES(?,?)";
			PreparedStatement pst = con.prepareStatement(createCustomerSql, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, customer.getName());
			pst.setString(2, customer.getPassword());
			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			rs.next();
			customer.setId(rs.getLong(1));
			rs.close();
			pst.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot create new customer[name: " + customer.getName() + "]");
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}

	}

	/**
	 * this method delete customer
	 */
	@Override
	public void removeCustomer(Customer customer) throws DbException {
		Connection con = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String removeCustomerSql = "DELETE FROM customer WHERE id=" + customer.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCustomerSql);
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot delete customer[id: " + customer.getId() + "]");
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}

	}

	/**
	 * this method update customer
	 */
	@Override
	public void updateCustomer(Customer customer) throws DbException {
		Connection con = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String updateCustomerSql = "UPDATE customer SET customer_name='" + customer.getName() + "', password='"
					+ customer.getPassword() + "' WHERE id= " + customer.getId();
			Statement st = con.createStatement();
			st.execute(updateCustomerSql);
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException(
					"cannot update customer[id: " + customer.getId() + ", name: " + customer.getName() + "]");
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}

	}

	/**
	 * this method get customer by id
	 */
	@Override
	public Customer getCustomer(long id) throws DbException {
		Connection con = null;
		Customer customer = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String getCustomerSql = "SELECT * FROM customer WHERE id=" + id;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getCustomerSql);
			customer = new Customer();
			rs.next();
			customer.setId(rs.getLong("id"));
			customer.setName(rs.getString("customer_name"));
			customer.setPassword(rs.getString("password"));
			rs.close();
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot get customer[id: " + customer.getId() + "]");
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}
		return customer;
	}

//	/**
//	 * this method get customer by name
//	 */
//	@Override
//	public Customer getCustomer() throws DbException {
//		Connection con = null;
//		Customer customer = null;
//		try {
//			connectionPool = ConnectionPool.getInstance();
//			con = connectionPool.getConnection();
//			String getCustomerSql = "SELECT * FROM customer WHERE customer_name='" + loggedInCustomer.getName() + "'";
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery(getCustomerSql);
//			customer = new Customer();
//			rs.next();
//			customer.setId(rs.getLong("id"));
//			customer.setName(rs.getString("customer_name"));
//			customer.setPassword(rs.getString("password"));
//			rs.close();
//			st.close();
//		} catch (ConnectionPoolException e) {
//			System.out.println(e);
//		} catch (SQLException e) {
//			throw new DbException("cannot get customer[name: " + customer.getName() + "]");
//		} finally {
//			if (con != null)
//				connectionPool.returnConnection(con);
//		}
//		return customer;
//	}

	/**
	 * this method get all customers inside collection
	 */
	@Override
	public Collection<Customer> getAllCustomer() throws DbException {
		Connection con = null;
		ArrayList<Customer> customers = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String getAllCustomersSql = "SELECT * FROM customer";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getAllCustomersSql);
			customers = new ArrayList<Customer>();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong("id"));
				customer.setName(rs.getString("customer_name"));
				customer.setPassword(rs.getString("password"));
				customers.add(customer);
			}
			rs.close();
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}
		return customers;
	}

	/**
	 * this method get all coupons per customer inside collection
	 */
	@Override
	public Collection<Coupon> getCoupons() throws DbException {
		Connection con = null;
		ArrayList<Coupon> coupons = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			coupons = new ArrayList<Coupon>();
			String getCouponsSql = "SELECT * FROM coupon INNER JOIN customer_coupon ON id=customer_coupon.coupon_id "
					+ "WHERE customer_coupon.customer_id=" + loggedInCustomer.getId();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getCouponsSql);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("id"));
				coupon.setTitle(rs.getString("title"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setType(CouponType.valueOf(rs.getString("type")));
				coupon.setMessage(rs.getString("message"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
			}
			rs.close();
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot get coupons");
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}
		return coupons;
	}

	/**
	 * this is a login method for customer
	 */
	@Override
	public boolean login(String name, String password) throws DbException {
		Connection con = null;
		boolean loginSuccess = false;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String loginSql = "SELECT * FROM customer WHERE customer_name='" + name + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(loginSql);
			if (rs.next()) {
				if (rs.getString("password").equals(password)) {
					loginSuccess = true;
					loggedInCustomer = new Customer(rs.getLong(1), rs.getString(2), rs.getString(3));
				}
			}
			rs.close();
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}
		return loginSuccess;
	}

	/**
	 * this method check if customer purchased the coupon already by customer id and
	 * coupon id with customer-coupon table
	 */
	@Override
	public boolean alreadyPurchased(long couponId) throws DbException {
		Connection con = null;
		boolean purchased = false;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String alreadyPurchasedSql = "SELECT coupon_id FROM customer_coupon WHERE customer_id="
					+ loggedInCustomer.getId() + "AND coupon_id=" + couponId;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(alreadyPurchasedSql);
			if (rs.next()) {
				purchased = true;
			}
			rs.close();
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}
		return purchased;
	}

	/**
	 * this method insert the coupon purchase to customer-coupon table
	 */
	@Override
	public void insertCouponPurchase(long couponId) throws DbException {
		Connection con = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String insertCouponSql = "INSERT INTO customer_coupon (customer_id, coupon_id) VALUES("
					+ loggedInCustomer.getId() + ", " + couponId + ")";
			Statement st = con.createStatement();
			st.execute(insertCouponSql);
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}

	}

	/**
	 * this method get coupons per customer by type inside collection
	 */
	@Override
	public Collection<Coupon> getCouponsByType(CouponType type) throws DbException {
		Connection con = null;
		Collection<Coupon> couponByType = new ArrayList<>();
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String couponByTypeSql = "SELECT coupon.* FROM customer_coupon, coupon WHERE customer_coupon.coupon_id=coupon.id "
					+ "AND customer_coupon.customer_id=" + loggedInCustomer.getId() + " AND coupon.type='" + type.name()
					+ "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(couponByTypeSql);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("id"));
				coupon.setTitle(rs.getString("title"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setType(CouponType.valueOf(rs.getString("type")));
				coupon.setMessage(rs.getString("message"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				couponByType.add(coupon);
			}
			rs.close();
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}
		return couponByType;
	}

	/**
	 * this method get coupons per customer by price inside collection
	 */
	@Override
	public Collection<Coupon> getCouponsByPrice(double Price) throws DbException {
		Connection con = null;
		Collection<Coupon> couponByPrice = new ArrayList<>();
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String couponByPriceSql = "SELECT coupon.* FROM customer_coupon, coupon WHERE customer_coupon.coupon_id=coupon.id "
					+ "AND customer_coupon.customer_id=" + loggedInCustomer.getId() + " AND coupon.price<=" + Price;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(couponByPriceSql);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("id"));
				coupon.setTitle(rs.getString("title"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setType(CouponType.valueOf(rs.getString("type")));
				coupon.setMessage(rs.getString("message"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				couponByPrice.add(coupon);
			}
			rs.close();
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}
		return couponByPrice;
	}

	/**
	 * this method check if customer already exist in db
	 */
	@Override
	public boolean checkIfExist(Customer customer) throws DbException {
		Connection con = null;
		boolean exist = false;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String checkSql = "SELECT customer_name FROM customer WHERE customer_name='" + customer.getName() + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			if (rs.next()) {
				exist = true;
			}
			rs.close();
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}
		return exist;
	}

	/**
	 * this method check if customer already exist with same name of company in db
	 */
	@Override
	public boolean checkIfExist(Company company) throws DbException {
		Connection con = null;
		boolean exist = false;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String checkSql = "SELECT customer_name FROM customer WHERE customer_name='" + company.getName() + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			if (rs.next()) {
				exist = true;
			}
			rs.close();
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}
		return exist;
	}

	/**
	 * this method remove all coupons from customer-coupon table by customer
	 */
	@Override
	public void removeCustomerCoupon(Customer customer) throws DbException {
		Connection con = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			con = connectionPool.getConnection();
			String removeCustomerCouponSql = "DELETE FROM customer_coupon WHERE customer_id=" + customer.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCustomerCouponSql);
			st.close();
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionPool.returnConnection(con);
		}

	}

}
