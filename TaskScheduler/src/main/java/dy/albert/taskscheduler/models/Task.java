package dy.albert.taskscheduler.models;

import java.util.Date;
import java.util.Set;

public interface Task {

	long getId();

	String getName();

	void setName(String name);

	Set<Task> getDependencies();

	void addDependency(Task t) throws CyclicTaskDependencyException;

	void clearDependencies();

	boolean isDependentOn(Task t);

	int getDuration();

	void setDuration(int duration);

	Date getStartDate();

	void setStartDate(Date startDate);

	Date getEndDate();

	void setEndDate(Date endDate);
}
