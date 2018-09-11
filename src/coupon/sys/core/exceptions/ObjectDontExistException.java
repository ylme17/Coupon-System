package coupon.sys.core.exceptions;

public class ObjectDontExistException extends CouponSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ObjectDontExistException() {
		super("object dont exist in db");
	}
	
	public ObjectDontExistException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
