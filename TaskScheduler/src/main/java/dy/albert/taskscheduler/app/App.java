package dy.albert.taskscheduler.app;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import dy.albert.taskscheduler.models.CyclicTaskDependencyException;
import dy.albert.taskscheduler.models.SchedulableTask;
import dy.albert.taskscheduler.models.Task;
import dy.albert.taskscheduler.services.AutoTasksScheduler;
import dy.albert.taskscheduler.services.TasksScheduler;
import dy.albert.taskscheduler.utils.DateCalculator;
import dy.albert.taskscheduler.utils.SimpleDateCalculator;
import dy.albert.taskscheduler.utils.TaskByDependenciesComparator;
import dy.albert.taskscheduler.utils.TaskByStartDateComparator;

public class App {

	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private List<Task> tasks = new ArrayList<Task>();
	private AtomicLong counter = new AtomicLong();
	private Console console = System.console();

	public static void main(String[] args) {

		App thisApp = new App();
		String selectedOption = thisApp.showCommands();
		CommandOptions option = CommandOptions.parse(selectedOption);

		while (CommandOptions.QUIT != option) {

			switch (option) {
			case ADD:
				thisApp.addNewTask();
				break;
			case UPDATE:
				thisApp.updateTask();
				break;
			case LIST:
				thisApp.listTasks();
				break;
			case CALC:
				thisApp.calcTaskScheds();
				break;
			case QUIT:
				System.exit(0);
				break;
			default:
				System.out.println("Option not found. Please select the correct value");
				break;
			}

			System.out.println();
			selectedOption = thisApp.showCommands();
			option = CommandOptions.parse(selectedOption);
		}
	}

	private String showCommands() {
		ConsoleUtils.printlnf("\t1 - Add new task");
		ConsoleUtils.printlnf("\t2 - Update existing task");
		ConsoleUtils.printlnf("\t3 - List all tasks");
		ConsoleUtils.printlnf("\t4 - Calculate task schedules");
		ConsoleUtils.printlnf("\t5 - Quit");
		return console.readLine("Enter selected option: ");
	}

	private void addNewTask() {
		boolean newTask = true;
		while (newTask) {
			String taskName = ConsoleUtils.readMandatoryString("Enter task name: ");
			int duration = ConsoleUtils.readMandatoryInt("Enter task duration: ");

			SchedulableTask task = new SchedulableTask(counter.incrementAndGet(), taskName);
			task.setDuration(duration);

			readTaskDependencies(task, "Enter task dependencies (comma separated task id): ");
			tasks.add(task);

			newTask = ConsoleUtils.readMandatoryYesNo("Create another task? (Y/N) ");
		}

	}

	private void readTaskDependencies(Task task, String prompt, Object... args) {
		String dependency;
		boolean invalidDep = true;
		while (invalidDep) {
			invalidDep = false;
			dependency = ConsoleUtils.readOptionalStringWithFormat(prompt, "[\\d,]+", args);
			if (dependency != null && !dependency.isEmpty()) {
				invalidDep = parseInputDependencies(task, dependency);
			}
		}
	}

	private boolean parseInputDependencies(Task task, String dependency) {
		boolean invalidDep = false;
		task.clearDependencies();
		for (String dep : dependency.split(",")) {
			if (dep != null && !dep.isEmpty()) {
				int dependencyTaskId = Integer.parseInt(dep);
				try {
					if (dependencyTaskId > tasks.size()) {
						invalidDep = true;
						ConsoleUtils.printlnf("Cannot find task with id = %d.", dependencyTaskId);
					} else {
						task.addDependency(tasks.get(dependencyTaskId - 1));
					}
				} catch (CyclicTaskDependencyException e) {
					invalidDep = true;
					ConsoleUtils.printlnf("Cannot add cyclic dependency (task Id = %d).", dependencyTaskId);
				}
			}
		}
		return invalidDep;
	}

	private void updateTask() {
		boolean updateAnotherTask = true;
		while (updateAnotherTask) {
			int taskId = ConsoleUtils.readMandatoryInt("Enter task ID to update: ");
			Task taskToUpdate = tasks.get(--taskId);

			String taskName = ConsoleUtils.readOptionalString("Update task name [%s]: ", taskToUpdate.getName());

			if (taskName != null && !taskName.isEmpty()) {
				taskToUpdate.setName(taskName);
			}

			int duration = ConsoleUtils.readOptionalInt("Update task duration [%d]: ", taskToUpdate.getDuration());
			if (duration > 0) {
				taskToUpdate.setDuration(duration);
			}

			readTaskDependencies(taskToUpdate, "Update task dependencies [%s]: ", getTaskDependencyIds(taskToUpdate));

			updateAnotherTask = ConsoleUtils.readMandatoryYesNo("Update another task? (Y/N) ");
		}
	}

	private void calcTaskScheds() {
		Date startDate = ConsoleUtils.readMandatoryDate("Enter start date [MM/DD/YYYY]: ", DATE_FORMAT);

		DateCalculator dateCalculator = new SimpleDateCalculator();
		TasksScheduler tasksScheduler = new AutoTasksScheduler(dateCalculator, new TaskByDependenciesComparator());
		tasksScheduler.schedule(tasks, startDate);

		List<Task> startDateSortedTasks = new ArrayList<>(tasks);
		Collections.sort(startDateSortedTasks, new TaskByStartDateComparator());
		listTasks(startDateSortedTasks);
	}

	private void listTasks() {
		listTasks(tasks);
	}

	private void listTasks(Collection<Task> tasks) {
		if (!tasks.isEmpty()) {
			ConsoleUtils.printlnf(" ID \t Name \t Dur. \t Start    \t End     \t Dependencies");
			for (Task task : tasks) {
				ConsoleUtils.printlnf(" %d \t %s \t %d \t %s \t %s \t %s", task.getId(), task.getName(),
						task.getDuration(), formatDate(task.getStartDate()), formatDate(task.getEndDate()),
						getTaskDependencyIds(task));
			}
		} else {
			ConsoleUtils.printlnf("No records to display.");
		}
	}

	private String getTaskDependencyIds(Task t) {
		StringBuilder sb = new StringBuilder();
		Set<Task> dependencies = t.getDependencies();
		Iterator<Task> iter = dependencies.iterator();
		while (iter.hasNext()) {
			sb.append(iter.next().getId());
			if (iter.hasNext()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private String formatDate(Date date) {
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			return format.format(date);
		}
		return "        ";
	}
}
