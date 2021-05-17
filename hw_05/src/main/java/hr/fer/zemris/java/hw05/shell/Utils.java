package hr.fer.zemris.java.hw05.shell;

/**
 * Utility class for {@linkplain MyShell} used for parsing input.
 * 
 * @author Petra
 *
 */
public class Utils {

	/**
	 * Returns command name.
	 * 
	 * @param line console input
	 * @return command name
	 */
	public static String getCommandName(String line) {
		String[] parts = line.strip().split(" ");
		return parts[0].strip();
	}

	/**
	 * Parses the given file path.
	 * 
	 * @param line console input
	 * @return parsed file path
	 */
	public static String parsePathString(String line) {

		if (line.startsWith("\"")) {
			StringBuilder sb = new StringBuilder();
			int i;

			for (i = 1; i < line.length(); i++) {
				if (i == line.length())
					throw new ShellIOException("Invalid input.");

				if (line.charAt(i) == '\\') {
					i++;
					if (i > line.length())
						throw new ShellIOException("An escapeing error occurred.");

					if (line.charAt(i) == '\"' || line.charAt(i) == '\\')
						sb.append(line.charAt(i));
					else {
						sb.append("\\");
						sb.append(line.charAt(i));
					}

				} else if (line.charAt(i) == '\"') {
					i++;
					break;
				} else
					sb.append(line.charAt(i));
			}

			if (i == line.length()) {
				return sb.toString();
			} else {
				throw new ShellIOException("Invalid input.");
			}

		}

		String[] parts = line.strip().split(" ");
		return parts[0].strip();
	}

}
