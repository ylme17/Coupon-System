package coupon.sys.core.exceptions;

public class ObjectAlreadyExistException extends CouponSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectAlreadyExistException() {
		super("object already exist in db");
	}


}
