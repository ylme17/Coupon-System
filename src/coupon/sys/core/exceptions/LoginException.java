package coupon.sys.core.exceptions;

public class LoginException extends CouponSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LoginException() {
		super("wrong user name or password, could not login. please try again");
	}
	
	public LoginException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
