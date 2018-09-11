package coupon.sys.core.exceptions;

public class ConnectionPoolException extends CouponSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConnectionPoolException() {
		super("cannot get connection");
	}
	
	public ConnectionPoolException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
