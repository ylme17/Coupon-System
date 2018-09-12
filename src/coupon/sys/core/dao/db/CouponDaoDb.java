package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.exceptions.ConnectionPoolException;
import coupon.sys.core.exceptions.DbException;

/**
 * this class implements CouponDao interface
 * 
 * @author YECHIEL
 * 
 */
public class CouponDaoDb implements CouponDao {

	private ConnectionPool connectionpool;

	/**
	 * this method create coupon
	 */
	@Override
	public void createCoupon(Coupon coupon) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String createCouponSql = "INSERT INTO coupon (title, start_date, end_date, amount, type, message, price, image) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(createCouponSql, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, coupon.getTitle());
			pst.setDate(2, new java.sql.Date(coupon.getStartDate().getTime()));
			pst.setDate(3, new java.sql.Date(coupon.getEndDate().getTime()));
			pst.setInt(4, coupon.getAmount());
			pst.setString(5, coupon.getType().name());
			pst.setString(6, coupon.getMessage());
			pst.setDouble(7, coupon.getPrice());
			pst.setString(8, coupon.getImage());
			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			rs.next();
			coupon.setId(rs.getLong(1));
			rs.close();
			pst.close();

			System.out.println("coupon created [title: " + coupon.getTitle() + ", id: " + coupon.getId() + "]");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot create a new coupon[title: " + coupon.getTitle() + "]");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method remove coupon by id from coupon table and inner-join tables first
	 * customer-coupon table, second company-coupon table and finally coupon table
	 */
	@Override
	public void removeCoupon(Coupon coupon) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String removeCustomerCouponSql = "DELETE FROM customer_coupon WHERE coupon_id=" + coupon.getId();
			String removeCompanyCouponSql = "DELETE FROM company_coupon WHERE coupon_id=" + coupon.getId();
			String removeCouponSql = "DELETE FROM coupon WHERE id=" + coupon.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCustomerCouponSql);
			st.executeUpdate(removeCompanyCouponSql);
			st.executeUpdate(removeCouponSql);
			st.close();
			System.out.println("coupon id: " + coupon.getId() + " was deleted");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("unable to delete coupon " + coupon.toString(), e);
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method update the coupon
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String updateCouponSql = "UPDATE coupon SET title=?, start_date=?, end_date=?, amount=?, type=?, message=?, price=?, image=? WHERE id= "
					+ coupon.getId();
			PreparedStatement pst = con.prepareStatement(updateCouponSql);
			pst.setString(1, coupon.getTitle());
			pst.setDate(2, new java.sql.Date(coupon.getStartDate().getTime()));
			pst.setDate(3, new java.sql.Date(coupon.getEndDate().getTime()));
			pst.setInt(4, coupon.getAmount());
			pst.setString(5, coupon.getType().name());
			pst.setString(6, coupon.getMessage());
			pst.setDouble(7, coupon.getPrice());
			pst.setString(8, coupon.getImage());
			pst.executeUpdate();
			pst.close();
			System.out.println("coupon id: " + coupon.getId() + ", was updated");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("unable to update coupon " + coupon.getId());
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method get coupon by id
	 */
	@Override
	public Coupon getCoupon(long id) throws DbException {
		Connection con = null;
		Coupon coupon = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String getCouponSql = "SELECT * FROM coupon WHERE id=" + id;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getCouponSql);
			coupon = new Coupon();
			rs.next();
			coupon.setId(rs.getLong("id"));
			coupon.setTitle(rs.getString("title"));
			coupon.setStartDate(rs.getDate("start_date"));
			coupon.setEndDate(rs.getDate("end_date"));
			coupon.setAmount(rs.getInt("amount"));
			coupon.setType(CouponType.valueOf(rs.getString("type")));
			coupon.setMessage(rs.getString("message"));
			coupon.setPrice(rs.getDouble("price"));
			coupon.setImage(rs.getString("image"));
			rs.close();
			st.close();
			System.out.println(coupon.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return coupon;
	}

	/**
	 * this method get all coupons
	 */
	@Override
	public Collection<Coupon> getAllCoupon() throws DbException {
		Connection con = null;
		Collection<Coupon> coupons = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String getAllCouponsSql = "SELECT * FROM coupon";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getAllCouponsSql);
			coupons = new HashSet<>();
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
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return coupons;
	}

	/**
	 * this method get coupons by type
	 */
	@Override
	public Collection<Coupon> getCouponByType(CouponType type) throws DbException {
		Connection con = null;
		Collection<Coupon> coupons = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String getCouponsByTypeSql = "SELECT * FROM coupon WHERE type='" + type.name() + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getCouponsByTypeSql);
			coupons = new HashSet<>();
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
			throw new DbException("unable to get coupons");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return coupons;
	}

	/**
	 * this method remove coupons by company id, this method help to delete company
	 */
	@Override
	public void removeCouponByCompany(Company company) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String removeCouponSql = "DELETE FROM coupon WHERE coupon.id IN (SELECT company_coupon.coupon_Id FROM company_coupon "
					+ "WHERE company_coupon.company_id =" + company.getId() + ")";
			Statement st = con.createStatement();
			st.executeUpdate(removeCouponSql);
			st.close();
			System.out.println("deleted");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("unable to delete company coupons");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method remove coupons from customer-coupon table by company id this
	 * method help to delete company
	 */
	@Override
	public void removeCustomerCoupon(Company company) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String removeCustomerCouponSql = "DELETE FROM customer_coupon WHERE EXIST (SELECT 1 FROM company, company_coupon, "
					+ "coupon WHERE company.id=company_coupon.company_id AND company_coupon.coupon_id=coupon.id AND coupon.id=customer_coupon.coupon_id AND company.id="
					+ company.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCustomerCouponSql);
			st.close();
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
	 * this method check if coupon exist by title to create one
	 */
	@Override
	public boolean checkIfExist(Coupon coupon) throws DbException {
		Connection con = null;
		boolean exist = false;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String checkIfExistSql = "SELECT * FROM coupon WHERE title='" + coupon.getTitle() + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(checkIfExistSql);
			if (rs.next()) {
				exist = true;
			}
			rs.close();
			st.close();
			if (exist) {
				System.out.println("coupon already exist");
			} else {
				System.out.println("coupon not exist");
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
	 * this method get coupons by date inside collection
	 */
	@Override
	public Collection<Coupon> getCouponByDate(Date date) throws DbException {
		Connection con = null;
		Collection<Coupon> CouponByDate = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String couponByDateSql = "SELECT * FROM coupon WHERE end_date<='" + new java.sql.Date(date.getTime()) + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(couponByDateSql);
			CouponByDate = new HashSet<>();
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
				CouponByDate.add(coupon);
			}
			rs.close();
			st.close();
			System.out.println(CouponByDate.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return CouponByDate;
	}

}
