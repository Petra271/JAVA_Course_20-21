package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

public class StudentDBTest {

	@Test
	public void filterAlwaysFalse() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		StudentDB db = new StudentDB(lines);
		List<StudentRecord> filtered = db.filter(record -> false);
		assertEquals(0, filtered.size());
	}

	@Test
	public void filterAlwaysTrue() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		StudentDB db = new StudentDB(lines);
		List<StudentRecord> filtered = db.filter(record -> true);
		assertEquals(63, filtered.size());
	}

}
