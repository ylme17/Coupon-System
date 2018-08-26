package coupon.sys.test;

import coupon.sys.core.couponSystem.CouponSystem;
import coupon.sys.core.exceptions.CouponSystemException;

public class RunnableAdmin implements Runnable{
	
	private CouponSystem couponSystem;
	private String userName;
	private String password;
	
	public RunnableAdmin(String userName, String password) throws CouponSystemException {
		this.couponSystem=CouponSystem.getInstance();
		this.userName=userName;
		this.password=password;
	}

	/**
	 * 
	 */
	@Override
	public void run() {
		
		
	}

}
