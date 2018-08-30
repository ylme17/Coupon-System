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
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * this class implements CompanyDao interface
 * @author YECHIEL
 * 
 */
public class CompanyDaoDb implements CompanyDao {

	private ConnectionPool connectionpool;

	// get connection from pool
	// create an sql string
	// get a statement from the connection
	// execute the sql command
	// return connection to pool

	/**
	 * this method create a company
	 */
	@Override
	public void createCompany(Company company) throws CouponSystemException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String createCompanySql = "INSERT INTO company VALUES(?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(createCompanySql);
			pst.setString(2, company.getName());
			pst.setString(3, company.getPassword());
			pst.setString(4, company.getEmail());
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to create a new company[company name: " + company.getName()
					+ "] or get a new DB connection");
		} finally {
			connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method remove the company
	 */
	@Override
	public void removeCompany(Company company) throws CouponSystemException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String removeCompanySql = "DELETE FROM company WHERE id=" + company.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCompanySql);
			st.close();
			System.out.println(company.toString() + " was deleted");
		} catch (SQLException e) {
			throw new CouponSystemException("unable to delete company " + company.toString());
		} finally {
			connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method update the company
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String updateCompanySql = "UPDATE company SET company_name='" + company.getName() + "', password='"
					+ company.getPassword() + "', email='" + company.getEmail() + "' WHERE id= " + company.getId();
			Statement st = con.createStatement();
			st.execute(updateCompanySql);
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to update company " + company.toString(), e);
		} finally {
			connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method gets a specific company by id
	 */
	@Override
	public Company getCompany(long id) throws CouponSystemException {
		Connection con = null;
		Company company;
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
		} catch (SQLException e) {
			throw new CouponSystemException("company not selected", e);
		} finally {
			connectionpool.returnConnection(con);
		}
		return company;
	}
	
	/**
	 * this method gets a specific company by name
	 */
	@Override
	public Company getCompany(String name) throws CouponSystemException {
		Connection con = null;
		Company company;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String getCompanySql = "SELECT * FROM company WHERE company_name='" + name+"'";
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
		} catch (SQLException e) {
			throw new CouponSystemException("company not selected", e);
		} finally {
			connectionpool.returnConnection(con);
		}
		return company;
	}

	/**
	 * this method gets all companies
	 */
	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		Connection con = null;
		ArrayList<Company> companies;
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
				System.out.println(companies.toString());
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException(e);
		} finally {
			connectionpool.returnConnection(con);
		}
		return companies;
	}

	@Override
	public Collection<Coupon> getCoupons(Company company) throws CouponSystemException {
		Connection con = null;
		ArrayList<Coupon> coupons;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			coupons = new ArrayList<Coupon>();
			String getCouponsSql = "SELECT * FROM (company_coupon INNERJOIN coupon ON company_coupon.coupon_id=coupon.id) "
					+ "WHERE comapny_id=" + company.getId();
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
		} catch (SQLException e) {
			throw new CouponSystemException("company not selected", e);
		} finally {
			connectionpool.returnConnection(con);
		}
		return coupons;
	}

	/**
	 * this method uses to login for company client
	 */
	@Override
	public boolean login(String name, String password) throws CouponSystemException {
		Connection con = null;
		boolean loginSuccess = false;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String loginSql = "SELECT company_name, password FROM company WHERE company_name='" + name + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(loginSql);
			if (rs.getString("password").equals(password)) {
				loginSuccess = true;
			}
			rs.close();
			st.close();
			return loginSuccess;
		} catch (SQLException e) {
			throw new CouponSystemException("company not found", e);
		} finally {
			connectionpool.returnConnection(con);
		}
	}

	/**
	 * this method checks if specific company exist in db
	 */
	@Override
	public boolean checkIfExist(Company company) throws CouponSystemException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String checkSql = "SELECT * FROM company WHERE company_name='" + company.getName() + "'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(checkSql);
			if (rs.next()) {
				return true;
			}
			rs.close();
			st.close();
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException();
		} finally {
			connectionpool.returnConnection(con);
		}
	}

	/**
	 * this method remove the coupon from company-coupon table
	 */
	@Override
	public void removeCompanyCoupon(Company company) throws CouponSystemException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String removeCompanySql = "DELETE FROM company_coupon WHERE id=" + company.getId();
			Statement st = con.createStatement();
			st.executeUpdate(removeCompanySql);
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to delete company " + company.toString());
		} finally {
			connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method insert coupon to company-coupon table when coupon created 
	 */
	@Override
	public void insertCouponCreation(long companyId, long couponId) throws CouponSystemException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String insertCouponSql = "INSERT INTO company_coupon (company_id, coupon_id) VALUES(" + companyId + ", "
					+ couponId + ")";
			Statement st = con.createStatement();
			st.executeQuery(insertCouponSql);
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException();
		} finally {
			connectionpool.returnConnection(con);
		}

	}

	/**
	 * this method checks if a coupon belong to specific company
	 */
	@Override
	public boolean couponBelongComapny(long companyId, long couponId) throws CouponSystemException {
		Connection con = null;
		try {
			connectionpool = ConnectionPool.getInstance();
			con = connectionpool.getConnection();
			String couponBelongsSql="SELECT * FROM company_coupon WHERE company_id="+companyId+" AND coupon_id="+couponId;
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(couponBelongsSql);
			if(rs.next()) {
				return true;
			}
			rs.close();
			st.close();
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("could not get a connection to db");
		}finally {
			connectionpool.returnConnection(con);
		}
	}
	
	/**
	 * this method gets the coupon by type and company
	 */
	@Override
	public Collection<Coupon> getCouponByType(Company company, CouponType type) throws CouponSystemException {
		Connection con=null;
		ArrayList<Coupon> coupons;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String getCouponsByTypeSql="SELECT c.* FROM coupon c, company_coupon cc WHERE cc.coupon_id=c.id AND cc.company_id="+company.getId()+" AND c.type='"+type.name()+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(getCouponsByTypeSql);
			coupons=new ArrayList<Coupon>();
			while(rs.next()) {
				Coupon coupon=new Coupon();
				coupon.setId(rs.getLong("id"));
				coupon.setTitle(rs.getString("title"));
				coupon.setStartDate(rs.getDate("start_date"));
				coupon.setEndDate(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setType(CouponType.valueOf(rs.getString("type")));
				coupon.setMessage(rs.getString("message"));
				coupon.setPrice(rs.getDouble("price")	);
				coupon.setImage(rs.getString("image"));
				coupons.add(coupon);
				System.out.println(coupons.toString());
			}
			rs.close();
			st.close();
			return coupons;
		} catch (SQLException e) {
			throw new CouponSystemException(e);
		}finally {
			connectionpool.returnConnection(con);
		}
	}

}
