package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.exceptions.ConnectionPoolException;
import coupon.sys.core.exceptions.DbException;

/**
 * this class implements CutomerDao interface
 * 
 * @author YECHIEL.MOSHE
 * 
 */
public class CustomerDaoDb implements CustomerDao {

	private ConnectionPool connectionpool;

	/**
	 * this method create customer
	 */
	@Override
	public void createCustomer(Customer customer) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
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
			System.out.println("customer created [name: " + customer.getName() + ", id: " + customer.getId() + "]");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot create new customer[name: " + customer.getName() + "]");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method delete customer
	 */
	@Override
	public void removeCustomer(Customer customer) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String removeCustomerSql = "DELETE FROM customer WHERE id=" + customer.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCustomerSql);
			st.close();
			System.out.println("customer id: " + customer.getId() + " deleted");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot delete customer[id: " + customer.getId() + "]");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method update customer
	 */
	@Override
	public void updateCustomer(Customer customer) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String updateCustomerSql = "UPDATE customer SET customer_name='" + customer.getName() + "', password='"
					+ customer.getPassword() + "' WHERE id= " + customer.getId();
			Statement st = con.createStatement();
			st.execute(updateCustomerSql);
			st.close();
			System.out.println("customer id: " + customer.getId() + " updated");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException(
					"cannot update customer[id: " + customer.getId() + ", name: " + customer.getName() + "]");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
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
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
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
			System.out.println(customer.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot get customer[id: " + customer.getId() + "]");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return customer;
	}

	/**
	 * this method get customer by name
	 */
	@Override
	public Customer getCustomer(String name) throws DbException {
		Connection con = null;
		Customer customer = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String getCustomerSql = "SELECT * FROM customer WHERE customer_name='" + name + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getCustomerSql);
			customer = new Customer();
			rs.next();
			customer.setId(rs.getLong("id"));
			customer.setName(rs.getString("customer_name"));
			customer.setPassword(rs.getString("password"));
			rs.close();
			st.close();
			System.out.println(customer.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot get customer[name: " + customer.getName() + "]");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return customer;
	}

	/**
	 * this method get all customers inside collection
	 */
	@Override
	public Collection<Customer> getAllCustomer() throws DbException {
		Connection con = null;
		ArrayList<Customer> customers = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
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
			System.out.println(customers.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return customers;
	}

	/**
	 * this method get all coupons per customer inside collection
	 */
	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws DbException {
		Connection con = null;
		ArrayList<Coupon> coupons = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			coupons = new ArrayList<Coupon>();
			String getCouponsSql = "SELECT * FROM coupon INNER JOIN customer_coupon ON id=customer_coupon.coupon_id "
					+ "WHERE customer_coupon.customer_id=" + customer.getId();
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
			System.out.println(coupons.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot get coupons");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
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
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String loginSql = "SELECT customer_name, password FROM customer WHERE customer_name='" + name + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(loginSql);
			rs.next();
			if (rs.getString("password").equals(password)) {
				loginSuccess = true;
			}
			rs.close();
			st.close();
			if (loginSuccess) {
				System.out.println("login success");
			} else {
				System.out.println("login failed");
			}
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return loginSuccess;
	}

	/**
	 * this method check if customer purchased the coupon already by customer id and
	 * coupon id with customer-coupon table
	 */
	@Override
	public boolean alreadyPurchased(long customerId, long couponId) throws DbException {
		Connection con = null;
		boolean purchased = false;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String alreadyPurchasedSql = "SELECT coupon_id FROM customer_coupon WHERE customer_id=" + customerId
					+ "AND coupon_id=" + couponId;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(alreadyPurchasedSql);
			if (rs.next()) {
				purchased = true;
			}
			rs.close();
			st.close();
			if (purchased) {
				System.out.println("already purchased");
			} else {
				System.out.println("not purchased");
			}
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return purchased;
	}

	/**
	 * this method insert the coupon purchase to customer-coupon table
	 */
	@Override
	public void insertCouponPurchase(long customerId, long couponId) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String insertCouponSql = "INSERT INTO customer_coupon (customer_id, coupon_id) VALUES(" + customerId + ", "
					+ couponId + ")";
			Statement st = con.createStatement();
			st.execute(insertCouponSql);
			st.close();
			System.out.println("created in customer-coupon");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method get coupons per customer by type inside collection
	 */
	@Override
	public Collection<Coupon> getCouponByType(Customer customer, CouponType couponType) throws DbException {
		Connection con = null;
		Collection<Coupon> couponByType = new ArrayList<>();
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String couponByTypeSql = "SELECT co.* FROM customer_coupon cc, coupon co WHERE cc.coupon_id=co.id "
					+ "AND cc.customer_id=" + customer.getId() + " AND co.type='" + couponType.name() + "'";
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
			System.out.println(couponByType.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return couponByType;
	}

	/**
	 * this method get coupons per customer by price inside collection
	 */
	@Override
	public Collection<Coupon> getCouponUpToPrice(Customer customer, double upToPrice) throws DbException {
		Connection con = null;
		Collection<Coupon> couponUpToPrice = new ArrayList<>();
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String couponUpToPriceSql = "SELECT coupon.* FROM customer_coupon, coupon WHERE customer_coupon.coupon_id=coupon.id "
					+ "AND customer_coupon.customer_id=" + customer.getId() + " AND coupon.price<=" + upToPrice;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(couponUpToPriceSql);
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
				couponUpToPrice.add(coupon);
			}
			rs.close();
			st.close();
			System.out.println(couponUpToPrice.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return couponUpToPrice;
	}

	/**
	 * this method check if customer already exist in db
	 */
	@Override
	public boolean checkIfExist(Customer customer) throws DbException {
		Connection con = null;
		boolean exist = false;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String checkSql = "SELECT customer_name FROM customer WHERE customer_name='" + customer.getName() + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			if (rs.next()) {
				exist = true;
			}
			rs.close();
			st.close();
			if (exist) {
				System.out.println("customer already exist");
			} else {
				System.out.println("customer not exist");
			}
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
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
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String removeCustomerCouponSql = "DELETE FROM customer_coupon WHERE customer_id=" + customer.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCustomerCouponSql);
			st.close();
			System.out.println("deleted");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

}
