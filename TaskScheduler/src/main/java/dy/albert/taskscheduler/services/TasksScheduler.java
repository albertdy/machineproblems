package dy.albert.taskscheduler.services;

import java.util.Collection;
import java.util.Date;

import dy.albert.taskscheduler.models.Task;

public interface TasksScheduler {

	Collection<Task> schedule(Collection<Task> tasks, Date startDate);
}
