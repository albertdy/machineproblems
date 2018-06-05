package dy.albert.taskscheduler.app;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ConsoleUtils {

	private static final String LINE_FEED = System.getProperty("line.separator");
	private static final Console CONSOLE = System.console();

	public static String readMandatoryString(String formattedPrompt, Object...args) {
		String str = CONSOLE.readLine(formattedPrompt, args);
		while (str == null || str.isEmpty()) {
			printlnf("Mandatory field, please enter a value.");
			str = CONSOLE.readLine(formattedPrompt, args);
		};

		return str;
	}

	public static int readMandatoryInt(String formattedPrompt, Object...args) {
		String str = CONSOLE.readLine(formattedPrompt, args);
		int value = -1;

		while (str == null || str.isEmpty() || !str.matches("\\d+") || (value = Integer.parseInt(str)) <= 0) {
			printlnf("Mandatory field, please enter a valid number.");
			str = CONSOLE.readLine(formattedPrompt, args);
		};

		return value;
	}

	public static int readOptionalInt(String formattedPrompt, Object...args) {
		String str = CONSOLE.readLine(formattedPrompt, args);
		int value = -1;

		if (str == null || str.isEmpty()) {
			return -1;
		} else {
			while (!str.matches("\\d+") || (value = Integer.parseInt(str)) <= 0) {
				printlnf("Not a number, please enter a valid number.");
				str = CONSOLE.readLine(formattedPrompt, args);
			};
		}

		return value;
	}

	public static boolean readMandatoryYesNo(String formattedPrompt, Object...args) {
		String str = CONSOLE.readLine(formattedPrompt, args);
		while (str == null || str.isEmpty() || !str.matches("[yYnN]")) {
			printlnf("Mandatory field, please enter a Y or N.");
			str = CONSOLE.readLine(formattedPrompt, args);
		};

		return "Y".equalsIgnoreCase(str);
	}

	public static Date readMandatoryDate(String formattedPrompt, String dtFormat, Object...args) {
		Date date = null;
		String str = CONSOLE.readLine(formattedPrompt, args);
		boolean incorrectDateFormat = true;
		while (incorrectDateFormat) {
			if (str == null || str.isEmpty()) {
				incorrectDateFormat = true;
				printlnf("Mandatory field, please enter a valid date.");
			} else {
				try {
					SimpleDateFormat formatter = new SimpleDateFormat(dtFormat);
					incorrectDateFormat = false;
					date = formatter.parse(str);
				} catch (ParseException e) {
					incorrectDateFormat = true;
					printlnf("Incorrect date format entered, pls try again.");
				}
			}
		}

		return date;
	}

	public static String readOptionalString(String formattedPrompt, Object...args) {
		String str = CONSOLE.readLine(formattedPrompt, args);
		if (str == null || str.isEmpty()) {
			return "";
		}
		return str;
	}

	public static String readOptionalStringWithFormat(String formattedPrompt,String regex, Object...args) {
		String str = CONSOLE.readLine(formattedPrompt, args);
		if (str == null || str.isEmpty()) {
			return "";
		}
		while (!str.matches(regex)) {
			printlnf("Mandatory field, please enter a valid string");
			str = CONSOLE.readLine(formattedPrompt, args);
		};

		return str;
	}

	public static Console printlnf(String fmt, Object...args) {
		CONSOLE.printf(fmt,args);
		return CONSOLE.printf(LINE_FEED);
	}
}
