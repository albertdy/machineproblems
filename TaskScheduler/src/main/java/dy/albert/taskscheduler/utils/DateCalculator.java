package dy.albert.taskscheduler.utils;

import java.util.Date;

public interface DateCalculator {

	Date calculateEndDate(Date startDate, int duration);
}
