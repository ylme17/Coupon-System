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
import coupon.sys.core.exceptions.CouponSystemException;

public class CouponDaoDb implements CouponDao {
	
	private ConnectionPool connectionpool;

	@Override
	public void createCoupon(Coupon coupon) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con= connectionpool.getConnection();
			String createCouponSql="INSERT INTO coupon VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst=con.prepareStatement(createCouponSql);
			pst.setLong(1, coupon.getId());
			pst.setString(2, coupon.getTitle());
			pst.setDate(3, (java.sql.Date) coupon.getStartDate());
			pst.setDate(4, (java.sql.Date) coupon.getEndDate());
			pst.setInt(5, coupon.getAmount());
			pst.setString(6, coupon.getType().name());
			pst.setString(7, coupon.getMessage());
			pst.setDouble(8, coupon.getPrice());
			pst.setString(9, coupon.getImage());
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to create a new coupon[coupon title: "+coupon.getTitle()+" coupon id: "+coupon.getId()+
					"] or get a new DB connection", e);
		}finally {
			connectionpool.returnConnection(con);
		}

	}

	@Override
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String removeCustomerCouponSql="DELETE FROM customer_coupon WHERE coupon_id="+coupon.getId();
			String removeCompanyCouponSql="DELETE FROM company_coupon WHERE coupon_id="+coupon.getId();
			String removeCouponSql="DELETE FROM coupon WHERE id="+coupon.getId();
			Statement st=con.createStatement();
			st.executeUpdate(removeCustomerCouponSql);
			st.executeUpdate(removeCompanyCouponSql);
			st.executeUpdate(removeCouponSql);
			st.close();
			System.out.println(coupon.toString()+" was deleted");			
		} catch (SQLException e) {
			throw new CouponSystemException("unable to delete coupon "+coupon.toString(), e);
		}finally {
			connectionpool.returnConnection(con);
		}

	}

	@Override//change to prepared statement
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String updateCouponSql="UPDATE coupon SET title='"+coupon.getTitle()+"', start_date='"+coupon.getStartDate()+
					"', end_date='"+coupon.getEndDate()+"', amount="+coupon.getAmount()+", type='"+coupon.getType()+"', "
							+ "message='"+coupon.getMessage()+"', price="+coupon.getPrice()+", image='"+coupon.getImage()+"'"
									+ " WHERE id= "+coupon.getId();
			Statement st=con.createStatement();
			st.execute(updateCouponSql);
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to update coupon "+coupon.toString(), e);
		}finally {
			connectionpool.returnConnection(con);
		}

	}

	@Override
	public Coupon getCoupon(long id) throws CouponSystemException {
		Connection con=null;
		Coupon coupon;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String getCouponSql="SELECT * FROM coupon WHERE id="+id;
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(getCouponSql);
			coupon=new Coupon();
			rs.next();
			coupon.setId(rs.getLong("id"));
			coupon.setTitle(rs.getString("title"));
			coupon.setStartDate(rs.getDate("start_date"));
			coupon.setEndDate(rs.getDate("end_date"));
			coupon.setAmount(rs.getInt("amount"));
			coupon.setType(CouponType.valueOf(rs.getString("type")));
			coupon.setMessage(rs.getString("message"));
			coupon.setPrice(rs.getDouble("price")	);
			coupon.setImage(rs.getString("image"));
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("coupon not selected", e);
		}finally {
			connectionpool.returnConnection(con);
		}
		return coupon;
	}

	@Override
	public Collection<Coupon> getAllCoupon() throws CouponSystemException {
		Connection con=null;
		Collection<Coupon> coupons;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String getAllCouponsSql="SELECT * FROM coupon";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(getAllCouponsSql);
			coupons=new HashSet<>();
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
		} catch (SQLException e) {
			throw new CouponSystemException(e);
		}finally {
			connectionpool.returnConnection(con);
		}
		return coupons;
	}

	@Override
	public Collection<Coupon> getCouponByType(CouponType type) throws CouponSystemException {
		Connection con=null;
		Collection<Coupon> coupons;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String getCouponsByTypeSql="SELECT * FROM coupon WHERE type='"+type.name()+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(getCouponsByTypeSql);
			coupons=new HashSet<>();
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

	@Override
	public void removeCouponByCompany(Company company) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String removeCouponSql="DELETE FROM coupon cou WHERE EXIST (SELECT 1 FROM company com, company_coupon coc "
					+ "WHERE com.id=coc.company_id AND coc.coupon_id=cou.id AND com.id="+company.getId();
			Statement st=con.createStatement();
			st.executeUpdate(removeCouponSql);
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to delete company coupons");
		}finally {
			connectionpool.returnConnection(con);
		}
		
	}
	
	@Override
	public void removeCustomerCoupon(Company company) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String removeCustomerCouponSql="DELETE FROM customer_coupon cuc WHERE EXIST (SELECT 1 FROM company com, company_coupon coc, "
					+ "coupon cou WHERE com.id=coc.company_id AND coc.coupon_id=cou.id AND cou.id=cuc.coupon_id AND com.id="+company.getId();
			Statement st=con.createStatement();
			st.executeUpdate(removeCustomerCouponSql);
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to delete company "+company.toString(), e);
		}finally {
			connectionpool.returnConnection(con);
		}

		
	}

	@Override
	public boolean checkIfExist(Coupon coupon) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String checkIfExistSql="SELECT * FROM coupon WHERE title='"+coupon.getTitle()+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(checkIfExistSql);
			if(rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new CouponSystemException("couldn't connect db");
		}finally {
			connectionpool.returnConnection(con);
		}
	}

	@Override
	public Collection<Coupon> getCouponUpToDate(Date date) throws CouponSystemException {
		Connection con=null;
		Collection<Coupon> CouponUpToDate;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String couponUpToDateSql="SELECT * FROM coupon WHERE end_date<='"+new Date(date.getTime())+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(couponUpToDateSql);
			CouponUpToDate=new HashSet<>();
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
				CouponUpToDate.add(coupon);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException();
		}finally {
			connectionpool.returnConnection(con);
		}
		return CouponUpToDate;
	}

}
