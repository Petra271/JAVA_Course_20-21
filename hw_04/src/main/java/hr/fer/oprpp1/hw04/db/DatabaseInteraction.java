package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * This class is used for database interaction.
 * 
 * @author Petra
 *
 */
public class DatabaseInteraction {

	public static void main(String[] args) {

		List<String> rows = null;
		try {
			rows = Files.readAllLines(Paths.get("src/test/resources/database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("An error occurred while reading from the database.");
			System.exit(1);
		}

		StudentDB db = new StudentDB(rows);
		Scanner scanner = new Scanner(System.in);
		String row;

		while (true) {
			System.out.print("> ");
			row = scanner.nextLine();

			if (row.trim().equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}

			if (!row.trim().startsWith("query")) {
				System.out.println("The query must start with key word 'query'.");
				continue;
			}

			QueryParser parser;
			try {
				parser = new QueryParser(row.trim().replaceFirst("query", ""));
			} catch (QueryParserException ex) {
				System.out.println("The input is invalid.");
				continue;
			}

			List<StudentRecord> records;
			if (parser.isDirectQuery()) {
				System.out.println("Using index for fast retrieval.");
				records = new ArrayList<>();
				StudentRecord record = db.forJMBAG(parser.getQueriedJMBAG());
				if (record != null)
					records.add(record);
			} else
				records = db.filter(new QueryFilter(parser.getQuery()));

			if (records.size() > 0) {
				Optional<String> maxFirstName = records.stream().map(r -> r.getFirstName())
						.max(Comparator.comparingInt(String::length));
				int maxFirstNameLen = maxFirstName.get().length();
				Optional<String> maxLastName = records.stream().map(r -> r.getLastName())
						.max(Comparator.comparingInt(String::length));
				int maxLastNameLen = maxLastName.get().length();

				char[] charArrayName = new char[maxFirstNameLen + 2];
				Arrays.fill(charArrayName, '=');
				String str1 = new String(charArrayName);

				char[] charArrayLastName = new char[maxLastNameLen + 2];
				Arrays.fill(charArrayLastName, '=');
				String str2 = new String(charArrayLastName);

				System.out.format("+%s+%s+%s+%s+\n", "============", str1, str2, "===");
				records.stream().forEach(r -> {
					System.out.format("| %-1s | %-" + maxLastNameLen + "s | %-" + maxFirstNameLen + "s | %-1s |\n",
							r.getJmbag(), r.getLastName(), r.getFirstName(), r.getFinalGrade());
				});
				System.out.format("+%s+%s+%s+%s+\n", "============", str1, str2, "===");

			}
			System.out.println("Records selected: " + records.size() + "\n");
		}

		scanner.close();
	}

}
