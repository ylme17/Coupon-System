package coupon.sys.core.exceptions;

/**
 * this exception for connection pool methods
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class ConnectionPoolException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConnectionPoolException() {
		super("cannot get connection");
	}

	public ConnectionPoolException(String msg) {
		super(msg);
	}

	public ConnectionPoolException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
