package dy.albert.taskscheduler.utils;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleDateCalculatorTest {

	private SimpleDateCalculator dateCalculator = null;
	
	@Before
	public void setup() {
		dateCalculator = new SimpleDateCalculator();
	}

	@After
	public void tearDown() {
		dateCalculator = null;
	}

	@Test
	public void testCalculateEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 5, 5, 0, 0, 0);
		Date startDate = cal.getTime();

		Date endDate = dateCalculator.calculateEndDate(startDate, 3);
		
		cal.set(2018, 5, 8, 0, 0, 0);
		assertEquals(cal.getTime(), endDate);
		
		cal.set(2018, 5, 5);
		startDate = cal.getTime();

		endDate = dateCalculator.calculateEndDate(startDate, 3);
		
		cal.set(2018, 5, 8);
		assertEquals(cal.getTime(), endDate);
	}

}
