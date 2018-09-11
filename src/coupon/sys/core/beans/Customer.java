package coupon.sys.core.beans;

import java.util.Collection;

public class Customer {
	
	private long id;
	private String name;
	private String password;
	private Collection<Coupon> coupons;
	
	public Customer(long id, String name, String password) {
		super();
		this.id=id;
		this.name=name;
		this.password=password;
	}
	
	public Customer(long id) {
		super();
		this.id=id;
	}
	
	public Customer(String name) {
		super();
		this.name=name;
	}
	
	public Customer() {
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

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", password=" + password + "] \n";
	}

	
}
