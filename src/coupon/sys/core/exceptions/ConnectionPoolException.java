package coupon.sys.core.exceptions;

public class ConnectionPoolException extends CouponSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConnectionPoolException() {
		super("unable to get a connection from the connection pool");
	}

}
