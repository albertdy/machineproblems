package dy.albert.taskscheduler.utils;

import java.util.Comparator;

import dy.albert.taskscheduler.models.Task;

public class TaskByStartDateComparator implements Comparator<Task> {

	@Override
	public int compare(Task task1, Task task2) {
		return task1.getStartDate().compareTo(task2.getStartDate());
	}

}
