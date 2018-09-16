package coupon.sys.core.beans;

import java.util.Collection;

/**
 * this class for create customer object
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class Customer {

	private long id;
	private String name;
	private String password;
	private Collection<Coupon> coupons;

	/**
	 * construct customer object and get id, name and password
	 * 
	 * @param id       id of customer - generated automatically from DB
	 * @param name     name of customer
	 * @param password password of customer
	 */
	public Customer(long id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

	/**
	 * construct customer object and get only id
	 * 
	 * @param id
	 */
	public Customer(long id) {
		super();
		this.id = id;
	}

	/**
	 * construct customer object and get only name
	 * 
	 * @param name
	 */
	public Customer(String name) {
		super();
		this.name = name;
	}

	/**
	 * empty constructor
	 */
	public Customer() {
	}

	/**
	 * get id method
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * set id method
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * get name method
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set name method
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get password method
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * set password method
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * get coupons for customer in collection
	 * 
	 * @return all coupons
	 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * set coupons for customer in collection
	 * 
	 * @param coupons
	 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	/**
	 * to string method
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", password=" + password + "] \n";
	}

}
