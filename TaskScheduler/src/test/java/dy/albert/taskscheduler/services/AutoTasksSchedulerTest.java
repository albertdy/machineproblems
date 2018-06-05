package dy.albert.taskscheduler.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dy.albert.taskscheduler.models.SchedulableTask;
import dy.albert.taskscheduler.models.Task;
import dy.albert.taskscheduler.utils.DateCalculator;
import dy.albert.taskscheduler.utils.SimpleDateCalculator;
import dy.albert.taskscheduler.utils.TaskByDependenciesComparator;

public class AutoTasksSchedulerTest {

	private AutoTasksScheduler tasksScheduler = null;
	private DateCalculator dateCalc = null;
	private Comparator<Task> sorter = null;
	private List<Task> tasks = null;

	@Before
	public void setup() {
		tasks = new ArrayList<>();
		dateCalc = new SimpleDateCalculator();
		sorter = new TaskByDependenciesComparator();
		tasksScheduler = new AutoTasksScheduler(dateCalc, sorter);
	}

	@After
	public void tearDown() {
		tasks.clear();
		tasks = null;
		sorter = null;
		dateCalc = null;
		sorter = null;
	}

	@Test
	public void testScheduleTask() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 5, 5, 0, 0, 0);
		Date startDate = cal.getTime();

		long ids = 0L;
		SchedulableTask task1 = new SchedulableTask(++ids);
		task1.setDuration(1);
		tasks.add(task1);
		SchedulableTask task2 = new SchedulableTask(++ids);
		task2.setDuration(2);
		tasks.add(task2);
		SchedulableTask task3 = new SchedulableTask(++ids);
		task3.setDuration(3);
		tasks.add(task3);
		SchedulableTask task4 = new SchedulableTask(++ids);
		task4.setDuration(4);
		tasks.add(task4);
		SchedulableTask task5 = new SchedulableTask(++ids);
		task5.setDuration(5);
		tasks.add(task5);

		task1.addDependency(task2);
		task3.addDependency(task4);

		tasksScheduler.schedule(tasks, startDate);

		cal.set(2018, 5, 7, 0, 0 ,0);
		Date endDate2 = cal.getTime();
		assertEquals(startDate, task2.getStartDate());
		assertEquals(endDate2, task2.getEndDate());

		cal.set(2018, 5, 8, 0, 0 ,0);
		Date startDate4 = cal.getTime();
		cal.set(2018, 5, 12, 0, 0 ,0);
		Date endDate4 = cal.getTime();
		assertEquals(startDate4, task4.getStartDate());
		assertEquals(endDate4, task4.getEndDate());

		cal.set(2018, 5, 13, 0, 0 ,0);
		Date startDate5 = cal.getTime();
		cal.set(2018, 5, 18, 0, 0 ,0);
		Date endDate5 = cal.getTime();
		assertEquals(startDate5, task5.getStartDate());
		assertEquals(endDate5, task5.getEndDate());

		cal.set(2018, 5, 19, 0, 0 ,0);
		Date startDate1 = cal.getTime();
		cal.set(2018, 5, 20, 0, 0 ,0);
		Date endDate1 = cal.getTime();
		assertEquals(startDate1, task1.getStartDate());
		assertEquals(endDate1, task1.getEndDate());

		cal.set(2018, 5, 21, 0, 0 ,0);
		Date startDate3 = cal.getTime();
		cal.set(2018, 5, 24, 0, 0 ,0);
		Date endDate3 = cal.getTime();
		assertEquals(startDate3, task3.getStartDate());
		assertEquals(endDate3, task3.getEndDate());
	}


	@Test
	public void testScheduleTask2() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 5, 5, 0, 0, 0);
		Date startDate = cal.getTime();

		long ids = 0L;
		SchedulableTask task1 = new SchedulableTask(++ids);
		task1.setDuration(1);
		tasks.add(task1);
		SchedulableTask task2 = new SchedulableTask(++ids);
		task2.setDuration(2);
		tasks.add(task2);
		SchedulableTask task3 = new SchedulableTask(++ids);
		task3.setDuration(3);
		tasks.add(task3);
		SchedulableTask task4 = new SchedulableTask(++ids);
		task4.setDuration(4);
		tasks.add(task4);
		SchedulableTask task5 = new SchedulableTask(++ids);
		task5.setDuration(5);
		tasks.add(task5);

		task2.addDependency(task1);
		task2.addDependency(task5);
		task3.addDependency(task2);
		task3.addDependency(task4);
		task3.addDependency(task5);

		tasksScheduler.schedule(tasks, startDate);

		cal.set(2018, 5, 6, 0, 0 ,0);
		Date endDate1 = cal.getTime();
		assertEquals(startDate, task1.getStartDate());
		assertEquals(endDate1, task1.getEndDate());

		cal.set(2018, 5, 7, 0, 0 ,0);
		Date startDate4 = cal.getTime();
		cal.set(2018, 5, 11, 0, 0 ,0);
		Date endDate4 = cal.getTime();
		assertEquals(startDate4, task4.getStartDate());
		assertEquals(endDate4, task4.getEndDate());

		cal.set(2018, 5, 12, 0, 0 ,0);
		Date startDate5 = cal.getTime();
		cal.set(2018, 5, 17, 0, 0 ,0);
		Date endDate5 = cal.getTime();
		assertEquals(startDate5, task5.getStartDate());
		assertEquals(endDate5, task5.getEndDate());

		cal.set(2018, 5, 18, 0, 0 ,0);
		Date startDate2 = cal.getTime();
		cal.set(2018, 5, 20, 0, 0 ,0);
		Date endDate2 = cal.getTime();
		assertEquals(startDate2, task2.getStartDate());
		assertEquals(endDate2, task2.getEndDate());

		cal.set(2018, 5, 21, 0, 0 ,0);
		Date startDate3 = cal.getTime();
		cal.set(2018, 5, 24, 0, 0 ,0);
		Date endDate3 = cal.getTime();
		assertEquals(startDate3, task3.getStartDate());
		assertEquals(endDate3, task3.getEndDate());
	}

	@Test
	public void testScheduleTask3() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 5, 5, 0, 0, 0);
		Date startDate = cal.getTime();

		long ids = 0L;
		SchedulableTask task1 = new SchedulableTask(++ids);
		task1.setDuration(1);
		tasks.add(task1);
		SchedulableTask task2 = new SchedulableTask(++ids);
		task2.setDuration(2);
		tasks.add(task2);
		SchedulableTask task3 = new SchedulableTask(++ids);
		task3.setDuration(3);
		tasks.add(task3);
		SchedulableTask task4 = new SchedulableTask(++ids);
		task4.setDuration(4);
		tasks.add(task4);
		SchedulableTask task5 = new SchedulableTask(++ids);
		task5.setDuration(5);
		tasks.add(task5);

		task1.addDependency(task4);
		task1.addDependency(task5);
		task3.addDependency(task1);
		task3.addDependency(task2);
		task3.addDependency(task5);

		tasksScheduler.schedule(tasks, startDate);

		cal.set(2018, 5, 7, 0, 0 ,0);
		Date endDate2 = cal.getTime();
		assertEquals(startDate, task2.getStartDate());
		assertEquals(endDate2, task2.getEndDate());

		cal.set(2018, 5, 8, 0, 0 ,0);
		Date startDate4 = cal.getTime();
		cal.set(2018, 5, 12, 0, 0 ,0);
		Date endDate4 = cal.getTime();
		assertEquals(startDate4, task4.getStartDate());
		assertEquals(endDate4, task4.getEndDate());

		cal.set(2018, 5, 13, 0, 0 ,0);
		Date startDate5 = cal.getTime();
		cal.set(2018, 5, 18, 0, 0 ,0);
		Date endDate5 = cal.getTime();
		assertEquals(startDate5, task5.getStartDate());
		assertEquals(endDate5, task5.getEndDate());

		cal.set(2018, 5, 19, 0, 0 ,0);
		Date startDate1 = cal.getTime();
		cal.set(2018, 5, 20, 0, 0 ,0);
		Date endDate1 = cal.getTime();
		assertEquals(startDate1, task1.getStartDate());
		assertEquals(endDate1, task1.getEndDate());

		cal.set(2018, 5, 21, 0, 0 ,0);
		Date startDate3 = cal.getTime();
		cal.set(2018, 5, 24, 0, 0 ,0);
		Date endDate3 = cal.getTime();
		assertEquals(startDate3, task3.getStartDate());
		assertEquals(endDate3, task3.getEndDate());
	}
}
