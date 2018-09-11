package coupon.sys.core.util;

import java.util.Calendar;
import java.util.Date;

public class CurrentDate {
	
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
	
	public static Date getDateInWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		return calendar.getTime();
	}
	
//	public static void main(String[] args) {
//		System.out.println(CurrentDate.getCurrentDate());
//		System.out.println(CurrentDate.getDateInWeek());
//	}
	
}
