package dy.albert.taskscheduler.models;

public class CyclicTaskDependencyException extends Exception {

	/**	 */
	private static final long serialVersionUID = 1L;

	
	public CyclicTaskDependencyException() {
		super();
	}

	public CyclicTaskDependencyException(String message) {
		super(message);
	}
	
}
