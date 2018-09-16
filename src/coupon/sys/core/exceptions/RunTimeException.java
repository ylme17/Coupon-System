package coupon.sys.core.exceptions;

/**
 * this exception for run time cases
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class RunTimeException extends CouponSystemException {

	private static final long serialVersionUID = 1L;

	public RunTimeException() {
		super("RunTime error");
	}

	public RunTimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RunTimeException(String message) {
		super(message);
	}

}
