package coupon.sys.core.beans;

import java.util.Collection;

/**
 * this class for create objects of company type
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class Company {

	private long id;
	private String name;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	/**
	 * construct company object and get id, name, password and email
	 * 
	 * @param id       id of company - generated automatically from DB
	 * @param name     name of company
	 * @param password password of company for client login
	 * @param email    email of company
	 */
	public Company(long id, String name, String password, String email) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	/**
	 * empty constructor
	 */
	public Company() {
	}

	/**
	 * construct company object and get only name
	 * 
	 * @param name name of the company
	 */
	public Company(String name) {
		setName(name);
	}

	/**
	 * construct company object and get only id
	 * 
	 * @param id company id
	 */
	public Company(long id) {
		setId(id);
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
	 * get email method
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set email method
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * get coupons for company in collection
	 * 
	 * @return all coupons
	 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * set coupons for company in collection
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
		return "Company [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + "] \n";
	}

}
