package dy.albert.taskscheduler.models;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SchedulableTask implements Task {

	private long id;
	private String name;
	private int duration;
	private Date startDate;
	private Date endDate;
	private Set<Task> dependencies = new HashSet<Task>();

	public SchedulableTask(long id) {
		this.id = id;
	}

	public SchedulableTask(long id, String name) {
		this(id);
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		if (endDate != null && startDate.after(endDate)) {
			throw new IllegalArgumentException("Start Date should be before the End Date.");
		}
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		if (startDate != null && endDate.before(startDate)) {
			throw new IllegalArgumentException("End Date should be after the Start Date.");
		}
		this.endDate = endDate;
	}

	@Override
	public Set<Task> getDependencies() {
		if (dependencies.isEmpty()) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(dependencies);
	}

	@Override
	public void addDependency(Task t) throws CyclicTaskDependencyException {
		if (this.equals(t) || t.isDependentOn(this)) {
			throw new CyclicTaskDependencyException();
		}
		this.dependencies.add(t);
	}
	
	@Override
	public void clearDependencies() {
		this.dependencies.clear();
	}

	@Override
	public boolean isDependentOn(Task t) {
		if (dependencies.isEmpty()) {
			return false;
		} else if (dependencies.contains(t)) {
			return true;
		} else {
			for (Task dependent : dependencies) {
				if (dependent.isDependentOn(t)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchedulableTask other = (SchedulableTask) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
