package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a database used for storing student records.
 * 
 * @author Petra
 *
 */
public class StudentDB {

	/**
	 * Student records map
	 */
	private Map<String, StudentRecord> map;

	/**
	 * Student records list
	 */
	private List<StudentRecord> records;

	/**
	 * Creates an instance of this class. Creates a list of student records, as well
	 * as a map.
	 * 
	 * @param rows a list of student records
	 */
	public StudentDB(List<String> rows) {

		List<String[]> list = rows.stream().map(line -> line.split("\\s+")).collect(Collectors.toList());

		list.stream().forEach(r -> {
			try {
				if (Integer.parseInt(r[r.length - 1]) < 1 || Integer.parseInt(r[r.length - 1]) > 5)
					throw new IllegalArgumentException("The grade must be between 1 and 5.");
			} catch (NumberFormatException e) {
				System.err.println("The grade '" + r[3] + "' is not parseable.");
				System.exit(1);
			}

		});

		this.records = new ArrayList<>();
		for (String[] s : list) {
			if (s.length == 5) {
				this.records.add(new StudentRecord(s[0], s[1] + s[2], s[3], Integer.parseInt(s[4])));
			} else
				this.records.add(new StudentRecord(s[0], s[1], s[2], Integer.parseInt(s[3])));
		}

		try {
			this.map = this.records.stream().collect(Collectors.toMap(StudentRecord::getJmbag, r -> r));
		} catch (IllegalStateException e) {
			System.err.println("There must be no duplicate jmbags.");
			System.exit(1);
		}
	}

	/**
	 * Returns the student record which contains the given jmbag.
	 * 
	 * @param jmbag a student's jmbag
	 * @return student record whose jmbag is the given value
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (this.map.containsKey(jmbag)) {
			return this.map.get(jmbag);
		}
		return null;
	}

	/**
	 * Filters the database using the given filter.
	 * 
	 * @param filter filter used for filtering the database
	 * @return filtered database
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp;
		temp = this.records.stream().filter(filter::accepts).collect(Collectors.toList());
		return temp;
	}

}
