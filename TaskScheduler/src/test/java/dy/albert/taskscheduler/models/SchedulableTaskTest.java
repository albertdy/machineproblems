package dy.albert.taskscheduler.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SchedulableTaskTest {

	@Test
	public void testAddDependency() throws Exception {
		long ids = 0L;
		SchedulableTask task1 = new SchedulableTask(++ids);
		SchedulableTask task2 = new SchedulableTask(++ids);
		SchedulableTask task3 = new SchedulableTask(++ids);
		SchedulableTask task4 = new SchedulableTask(++ids);
		
		task1.addDependency(task2);
		task2.addDependency(task3);
		task3.addDependency(task4);
		
		assertTrue(task1.isDependentOn(task2));
		assertTrue(task2.isDependentOn(task3));
		assertTrue(task3.isDependentOn(task4));
		assertFalse(task2.isDependentOn(task1));
		assertFalse(task3.isDependentOn(task2));
		assertFalse(task4.isDependentOn(task3));
		assertFalse(task4.isDependentOn(task1));
		
	}
	
	@Test(expected = CyclicTaskDependencyException.class)
	public void testExceptionAddDependency1() throws Exception {
		long ids = 0L;
		SchedulableTask task1 = new SchedulableTask(++ids);
		task1.addDependency(task1);
	}
	
	@Test(expected = CyclicTaskDependencyException.class)
	public void testExceptionAddDependency2() throws Exception {
		long ids = 0L;
		SchedulableTask task1 = new SchedulableTask(++ids);
		SchedulableTask task2 = new SchedulableTask(++ids);
		SchedulableTask task3 = new SchedulableTask(++ids);
		
		task1.addDependency(task2);
		task2.addDependency(task3);
		task3.addDependency(task1);
	}

}
