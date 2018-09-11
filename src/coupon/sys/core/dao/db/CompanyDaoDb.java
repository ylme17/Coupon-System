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
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.exceptions.ConnectionPoolException;
import coupon.sys.core.exceptions.DbException;

/**
 * this class implements CompanyDao interface
 * 
 * @author YECHIEL.MOSHE
 * 
 */
public class CompanyDaoDb implements CompanyDao {

	private ConnectionPool connectionpool;

	/**
	 * this method create a company
	 */
	@Override
	public void createCompany(Company company) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String createCompanySql = "INSERT INTO company (company_name, password, email) VALUES(?,?,?)";
			PreparedStatement pst = con.prepareStatement(createCompanySql, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, company.getName());
			pst.setString(2, company.getPassword());
			pst.setString(3, company.getEmail());
			pst.executeUpdate();

			ResultSet rs = pst.getGeneratedKeys();
			rs.next();
			company.setId(rs.getLong(1));
			rs.close();
			pst.close();
			System.out.println("created [id: " + company.getId() + ", name: " + company.getName() + "]");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot create new company[name: " + company.getName() + "]");
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method remove the company
	 */
	@Override
	public void removeCompany(Company company) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String removeCompanySql = "DELETE FROM company WHERE id=" + company.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCompanySql);
			st.close();
			System.out.println(company.toString() + " was deleted");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot delete company " + company.toString());
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method update the company
	 */
	@Override
	public void updateCompany(Company company) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String updateCompanySql = "UPDATE company SET company_name='" + company.getName() + "', password='"
					+ company.getPassword() + "', email='" + company.getEmail() + "' WHERE id= " + company.getId();
			Statement st = con.createStatement();
			st.execute(updateCompanySql);
			st.close();
			System.out.println("updated");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("cannot update company " + company.toString(), e);
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method get specific company by id
	 */
	@Override
	public Company getCompany(long id) throws DbException {
		Connection con = null;
		Company company = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String getCompanySql = "SELECT * FROM company WHERE id=" + id;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getCompanySql);
			company = new Company();
			rs.next();
			company.setId(rs.getLong("id"));
			company.setName(rs.getString("company_name"));
			company.setPassword(rs.getString("password"));
			company.setEmail(rs.getString("email"));
			rs.close();
			st.close();
			System.out.println(company.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return company;
	}

	/**
	 * this method get specific company by name
	 */
	@Override
	public Company getCompany(String name) throws DbException {
		Connection con = null;
		Company company = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String getCompanySql = "SELECT * FROM company WHERE company_name='" + name + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getCompanySql);
			company = new Company();
			rs.next();
			company.setId(rs.getLong("id"));
			company.setName(rs.getString("company_name"));
			company.setPassword(rs.getString("password"));
			company.setEmail(rs.getString("email"));
			rs.close();
			st.close();
			System.out.println(company.toString());
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return company;
	}

	/**
	 * this method get all companies
	 */
	@Override
	public Collection<Company> getAllCompanies() throws DbException {
		Connection con = null;
		ArrayList<Company> companies = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String getAllCompaniesSql = "SELECT * FROM company";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getAllCompaniesSql);
			companies = new ArrayList<Company>();
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getLong("id"));
				company.setName(rs.getString("company_name"));
				company.setPassword(rs.getString("password"));
				company.setEmail(rs.getString("email"));
				companies.add(company);
			}
			rs.close();
			st.close();
			System.out.println(companies);
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return companies;
	}

	/**
	 * this method get all coupons by company
	 */
	@Override
	public Collection<Coupon> getCoupons(Company company) throws DbException {
		Connection con = null;
		ArrayList<Coupon> coupons = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			System.out.println("connected");
			coupons = new ArrayList<Coupon>();
			String getCouponsSql = "SELECT * FROM coupon INNER JOIN company_coupon ON id=company_coupon.coupon_id "
					+ "WHERE company_coupon.company_id=" + company.getId();
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
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return coupons;
	}

	/**
	 * this method use to login for company client
	 */
	@Override
	public boolean login(String name, String password) throws DbException {
		Connection con = null;
		boolean loginSuccess = false;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String loginSql = "SELECT company_name, password FROM company WHERE company_name='" + name + "'";
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
	 * this method check if specific company exist in db by name
	 */
	@Override
	public boolean checkIfExist(Company company) throws DbException {
		Connection con = null;
		boolean exist = false;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String checkSql = "SELECT * FROM company WHERE company_name='" + company.getName() + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			if (rs.next()) {
				exist = true;
			}
			rs.close();
			st.close();
			if (exist) {
				System.out.println("company already exist");
			} else {
				System.out.println("company not exist");
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
	 * this method remove the coupon from company-coupon table
	 */
	@Override
	public void removeCompanyCoupon(Company company) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String removeCompanySql = "DELETE FROM company_coupon WHERE company_id=" + company.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCompanySql);
			st.close();
			System.out.println("deleted from company-coupon");
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException("unable to delete from company-coupon company id: " + company.getId());
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method insert coupon to company-coupon table when coupon created
	 */
	@Override
	public void insertCouponCreation(long companyId, long couponId) throws DbException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String insertCouponSql = "INSERT INTO company_coupon (company_id, coupon_id) VALUES(" + companyId + ", "
					+ couponId + ")";
			Statement st = con.createStatement();
			st.execute(insertCouponSql);
			st.close();
			System.out.println("created in company-coupon");
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
	 * this method check if coupon belong to specific company
	 */
	@Override
	public boolean couponBelongComapny(long companyId, long couponId) throws DbException {
		Connection con = null;
		boolean belong = false;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String couponBelongsSql = "SELECT * FROM company_coupon WHERE company_id=" + companyId + " AND coupon_id="
					+ couponId;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(couponBelongsSql);
			if (rs.next()) {
				belong = true;
			}
			rs.close();
			st.close();
			if (belong) {
				System.out.println("coupon belong to company");
			} else {
				System.out.println("coupon not belong to company");
			}
		} catch (ConnectionPoolException e) {
			System.out.println(e);
		} catch (SQLException e) {
			throw new DbException();
		} finally {
			if (con != null)
				connectionpool.returnConnection(con);
		}
		return belong;
	}

	/**
	 * this method get the coupon by type and company
	 */
	@Override
	public Collection<Coupon> getCouponByType(Company company, CouponType type) throws DbException {
		Connection con = null;
		ArrayList<Coupon> coupons = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String getCouponsByTypeSql = "SELECT coupon.* FROM coupon, company_coupon WHERE company_coupon.coupon_id=coupon.id "
					+ "AND company_coupon.company_id=" + company.getId() + " AND coupon.type='" + type.name() + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(getCouponsByTypeSql);
			coupons = new ArrayList<Coupon>();
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

}
