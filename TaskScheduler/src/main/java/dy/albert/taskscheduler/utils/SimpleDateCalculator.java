package dy.albert.taskscheduler.utils;

import java.util.Calendar;
import java.util.Date;

public class SimpleDateCalculator implements DateCalculator {

	@Override
	public Date calculateEndDate(Date startDate, int duration) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		cal.add(Calendar.DATE, duration);

		return cal.getTime();
	}

}
