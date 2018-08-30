package coupon.sys.core.util;

import java.util.Calendar;
import java.util.Date;

public class CurrentDate {

	public static Date getCurrTime() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTime = new java.sql.Timestamp(now.getTime());
		return currentTime;
	}

}
