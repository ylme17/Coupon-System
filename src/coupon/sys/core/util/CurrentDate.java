package coupon.sys.core.util;

import java.util.Calendar;
import java.util.Date;

/**
 * this class for current date method
 * 
 * @author YECHIEL.MOSHE
 *
 */
public class CurrentDate {

	/**
	 * this method get current date
	 * 
	 * @return date
	 */
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

}
