package coupon.sys.core.exceptions;

/**
 * this exception for all DaoDb methods
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class DbException extends CouponSystemException {

	private static final long serialVersionUID = 1L;

	public DbException() {
		super("db error");
	}

	public DbException(String msg) {
		super(msg);
	}

	public DbException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
