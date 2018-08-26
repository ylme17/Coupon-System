package coupon.sys.core.beans;

import java.util.Collection;

public class Company {

	private long id;
	private String name;
	private String password;
	private String email;
	private Collection<Coupon> coupons;
	
	public Company(long id, String name,String password, String email) {
		super();
		this.id=id;
		this.name=name;
		this.password=password;
		this.email=email;
	}
	
	public Company() {
	}
	
	public Company(String name) {
		setName(name);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
		return "Company [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + "]";
	}

}
