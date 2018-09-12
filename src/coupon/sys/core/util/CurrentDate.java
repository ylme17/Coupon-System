package coupon.sys.core.util;

import java.util.Calendar;
import java.util.Date;

public class CurrentDate {
	
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
	
}
