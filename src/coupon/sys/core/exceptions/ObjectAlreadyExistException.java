package coupon.sys.core.exceptions;

/**
 * this exception for cases where object already exist
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class ObjectAlreadyExistException extends CouponSystemException {

	private static final long serialVersionUID = 1L;

	public ObjectAlreadyExistException() {
		super("object already exist in db");
	}

	public ObjectAlreadyExistException(String msg) {
		super(msg);
	}

}
