package dy.albert.taskscheduler.utils;

import java.util.Comparator;

import dy.albert.taskscheduler.models.Task;

public class TaskByDependenciesComparator implements Comparator<Task> {

	@Override
	public int compare(Task task1, Task task2) {

		boolean task1HasNoDependencies = task1.getDependencies().isEmpty();
		boolean task2HasNoDependencies = task2.getDependencies().isEmpty();

		if (!task1HasNoDependencies && task2HasNoDependencies) {
			return 1;
		}

		if (task1HasNoDependencies && !task2HasNoDependencies) {
			return -1;
		}

		if (!task1HasNoDependencies && !task2HasNoDependencies) {
			// check if o1 is dependent on o2
			if (task1.isDependentOn(task2)) {
				return 1;
			}

			// check if o2 is dependent on o1
			if (task2.isDependentOn(task1)) {
				return -1;
			}
		}

		// compare by id
		return new Long(task1.getId()).compareTo(new Long(task2.getId()));
	}

}
