package coupon.sys.core.beans;

import java.util.Collection;

/**
 * this class for create company object
 * 
 * @author YECHIEL
 *
 */
public class Company {

	private long id;
	private String name;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	/**
	 * construct company object and gets id, name, password and email
	 * 
	 * @param id       id of the company - its generated automatically from DB
	 * @param name     name of the company
	 * @param password password of the company for client login
	 * @param email    email of the company
	 */
	public Company(long id, String name, String password, String email) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	/**
	 * an empty constructor to construct company object
	 */
	public Company() {
	}

	/**
	 * construct company object and gets only name
	 * 
	 * @param name name of the company
	 */
	public Company(String name) {
		setName(name);
	}

	/**
	 * construct company object and gets only id
	 * 
	 * @param id company id
	 */
	public Company(long id) {
		setId(id);
	}

	/**
	 * get id method
	 * 
	 * @return the id of current company
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
	 * @return the name of current company
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + "] \n";
	}

}
