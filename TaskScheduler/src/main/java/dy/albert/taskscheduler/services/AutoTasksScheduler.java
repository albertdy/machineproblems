package dy.albert.taskscheduler.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import dy.albert.taskscheduler.models.Task;
import dy.albert.taskscheduler.utils.DateCalculator;

public class AutoTasksScheduler implements TasksScheduler {

	private static final int ONE_DAY = 1;
	private DateCalculator dateCalculator;
	private Comparator<Task> tasksSorter;
	
	public AutoTasksScheduler(DateCalculator dateCalculator, Comparator<Task> sorter) {
		this.dateCalculator = dateCalculator;
		this.tasksSorter = sorter;
	}

	@Override
	public Collection<Task> schedule(Collection<Task> tasks, Date startDate) {
		List<Task> sortedTasks = new ArrayList<Task>(tasks);
		Collections.sort(sortedTasks, tasksSorter);
		
		for (Task task : sortedTasks) {
			task.setStartDate(startDate);
			Date endDate  = dateCalculator.calculateEndDate(startDate, task.getDuration());
			task.setEndDate(endDate);
			startDate = dateCalculator.calculateEndDate(endDate, ONE_DAY);
		}
		
		return sortedTasks;
	}

}
