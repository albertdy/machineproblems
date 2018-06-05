package dy.albert.taskscheduler.app;

public enum CommandOptions {

	ADD("1"), UPDATE("2"), LIST("3"), CALC("4"), QUIT("5"), INVALID(null);
	
	private String cmd;
	private CommandOptions(String cmd) {
		this.cmd = cmd;
	}

	public static CommandOptions parse(String cmd) {
		for (CommandOptions option : values()) {
			if (cmd.equals(option.cmd)) {
				return option;
			}
		}
		
		return INVALID;
	}
}
