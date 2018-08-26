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
import coupon.sys.core.exceptions.CouponSystemException;

public class CustomerDaoDb implements CustomerDao {
	
	private ConnectionPool connectionpool;

	@Override
	public void createCustomer(Customer customer) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con= connectionpool.getConnection();
			String createCustomerSql="INSERT INTO customer VALUES(?,?,?)";
			PreparedStatement pst=con.prepareStatement(createCustomerSql);
			pst.setLong(1, customer.getId());
			pst.setString(2, customer.getName());
			pst.setString(3, customer.getPassword());
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to create a new customer[customer name: "+customer.getName()+" customer id: "+customer.getId()+
					"] or get a new DB connection", e);
		}finally {
			connectionpool.returnConnection(con);
		}

	}

	@Override
	public void removeCustomer(Customer customer) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String removeCustomerSql="DELETE FROM customer WHERE id="+customer.getId();
			Statement st=con.createStatement();
			st.executeUpdate(removeCustomerSql);
			st.close();
			System.out.println(customer.toString()+" was deleted");			
		} catch (SQLException e) {
			throw new CouponSystemException("unable to delete customer "+customer.toString(), e);
		}finally {
			connectionpool.returnConnection(con);
		}

	}

	@Override
	public void updateCustomer(Customer customer) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String updateCustomerSql="UPDATE customer SET customer_name='"+customer.getName()+"', password='"+customer.getPassword()+
					" WHERE id= "+customer.getId();
			Statement st=con.createStatement();
			st.execute(updateCustomerSql);
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to update customer "+customer.toString(), e);
		}finally {
			connectionpool.returnConnection(con);
		}

	}

	@Override
	public Customer getCustomer(long id) throws CouponSystemException {
		Connection con=null;
		Customer customer;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String getCustomerSql="SELECT * FROM customer WHERE id="+id;
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(getCustomerSql);
			customer=new Customer();
			rs.next();
			customer.setId(rs.getLong("id"));
			customer.setName(rs.getString("customer_name"));
			customer.setPassword(rs.getString("password"));
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("customer not selected", e);
		}finally {
			connectionpool.returnConnection(con);
		}
		return customer;
	}
	
	@Override
	public Customer getCustomer(String name) throws CouponSystemException {
		Connection con=null;
		Customer customer;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String getCustomerSql="SELECT * FROM customer WHERE customer_name='"+name+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(getCustomerSql);
			customer=new Customer();
			rs.next();
			customer.setId(rs.getLong("id"));
			customer.setName(rs.getString("customer_name"));
			customer.setPassword(rs.getString("password"));
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("customer not selected", e);
		}finally {
			connectionpool.returnConnection(con);
		}
		return customer;
	}

	@Override
	public Collection<Customer> getAllCustomer() throws CouponSystemException {
		Connection con=null;
		ArrayList<Customer> customers;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String getAllCustomersSql="SELECT * FROM customer";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(getAllCustomersSql);
			customers=new ArrayList<Customer>();
			while(rs.next()) {
				Customer customer=new Customer();
				customer.setId(rs.getLong("id"));
				customer.setName(rs.getString("customer_name"));
				customer.setPassword(rs.getString("password"));
				customers.add(customer);
				System.out.println(customers.toString());
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("customer not selected", e);
		}finally {
			connectionpool.returnConnection(con);
		}
		return customers;
	}

	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws CouponSystemException {
		Connection con=null;
		ArrayList<Coupon> coupons;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			coupons=new ArrayList<Coupon>();
			String getCouponsSql="SELECT * FROM (customer_coupon cc INNERJOIN coupon c ON cc.coupon_id=c.id) "
					+ "WHERE customer_id="+customer.getId();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(getCouponsSql);
			while (rs.next()) {
				Coupon coupon=new Coupon();
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
			throw new CouponSystemException("unable to get coupons", e);
		}finally {
			connectionpool.returnConnection(con);
		}
		return coupons;
	}

	@Override
	public boolean login(String name, String password) throws CouponSystemException {
		Connection con=null;
		boolean loginSuccess=false;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String loginSql="SELECT customer_name, password FROM customer WHERE customer_name='"+name+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(loginSql);
			if(rs.getString("password").equals(password)) {
				loginSuccess=true;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("customer not found", e);
		}finally {
			connectionpool.returnConnection(con);
		}
		return loginSuccess;
	}

	@Override
	public boolean alreadyPurchased(long customerId, long couponId) throws CouponSystemException {
		Connection con=null;
		boolean purchased=false;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String alreadyPurchasedSql="SELECT coupon_id FROM customer_coupon WHERE customer_id="+customerId;
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(alreadyPurchasedSql);
			if(rs.getLong("coupon_id")==couponId) {
				purchased=true;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("customer not found", e);
		}finally {
			connectionpool.returnConnection(con);
		}
		return purchased;
	}

	@Override
	public void insertCouponPurchase(long customerId, long couponId) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String insertCouponSql="INSERT INTO customer_coupon (customer_id, coupon_id) VALUES("+customerId+", "+couponId+")";
			Statement st=con.createStatement();
			st.executeQuery(insertCouponSql);
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("unable to craete or get a db connection");
		}finally {
			connectionpool.returnConnection(con);
		}
		
	}

	@Override
	public Collection<Coupon> getCouponByType(Customer customer, CouponType couponType) throws CouponSystemException {
		Connection con=null;
		Collection<Coupon> couponByType=new ArrayList<>();
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String couponByTypeSql="SELECT co.* FROM customer_coupon cc, coupon co WHERE cc.coupon_id=co.id "
					+ "AND cc.customer_id="+customer.getId()+" AND co.type='"+couponType.name()+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(couponByTypeSql);
			while(rs.next()) {
				Coupon coupon=new Coupon();
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
		} catch (SQLException e) {
			throw new CouponSystemException("unable to get coupons");
		}finally {
			connectionpool.returnConnection(con);
		}
		return couponByType;
	}

	@Override
	public Collection<Coupon> getCouponUpToPrice(Customer customer, double upToPrice) throws CouponSystemException {
		Connection con=null;
		Collection<Coupon> couponUpToPrice=new ArrayList<>();
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String couponUpToPriceSql="SELECT co.* FROM customer_coupon cc, coupon co WHERE cc.coupon_id=co.id "
					+ "AND cc.customer_id="+customer.getId()+" AND co.price<="+upToPrice;
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(couponUpToPriceSql);
			while(rs.next()) {
				Coupon coupon=new Coupon();
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
		} catch (SQLException e) {
			throw new CouponSystemException("unable to get coupons");
		}finally {
			connectionpool.returnConnection(con);
		}
		return couponUpToPrice;
	}
	
	@Override
	public boolean checkIfExist(Customer customer) throws CouponSystemException {
		Connection con=null;
		boolean exist=false;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String checkSql="SELECT customer_name FROM customer WHERE customer_name='"+customer.getName()+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(checkSql);
			if(rs.getString("customer_name").equals(customer.getName())) {
				exist=true;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException();
		}finally {
			connectionpool.returnConnection(con);
		}
		return exist;
	}

	@Override
	public void removeCustomerCoupon(Customer customer) throws CouponSystemException {
		Connection con=null;
		try {
			connectionpool=ConnectionPool.getInstance();
			con=connectionpool.getConnection();
			String removeCustomerCouponSql="DELETE FROM customer_coupon cc WHERE cc.customer_id="+customer.getId();
			Statement st=con.createStatement();
			st.executeUpdate(removeCustomerCouponSql);
			st.close();
		} catch (SQLException e) {
			throw new CouponSystemException("customer was not deleted");
		}finally {
			connectionpool.returnConnection(con);
		}
		
	}

}
