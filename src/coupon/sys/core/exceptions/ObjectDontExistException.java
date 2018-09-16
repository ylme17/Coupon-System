package coupon.sys.core.exceptions;

/**
 * this exception for cases where object not exist
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class ObjectDontExistException extends CouponSystemException {

	private static final long serialVersionUID = 1L;

	public ObjectDontExistException() {
		super("object not exist");
	}

	public ObjectDontExistException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ObjectDontExistException(String msg) {
		super(msg);
	}

}
