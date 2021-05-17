package hr.fer.oprpp1.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Vector2DTest {

	@Test
	public void createNewVectorTest() {
		Vector2D v = new Vector2D(8, 9);
		assertEquals(8, v.getX());
		assertEquals(9, v.getY());
	}

	@Test
	public void addTest() {
		Vector2D v = new Vector2D(8, 9);
		v.add(new Vector2D(2, 6));
		assertEquals(new Vector2D(10, 15), v);
	}

	@Test
	public void addNullTestShouldThrow() {
		Vector2D v = new Vector2D(8, 9);
		assertThrows(NullPointerException.class, () -> v.add(null));
	}

	@Test
	public void addedTest() {
		Vector2D v = new Vector2D(8, 9);
		assertEquals(new Vector2D(10, 15), v.added(new Vector2D(2, 6)));
	}

	@Test
	public void addedNullTestShouldThrow() {
		Vector2D v = new Vector2D(8, 9);
		assertThrows(NullPointerException.class, () -> v.added(null));
	}

	@Test
	public void rotateTest() {
		Vector2D v = new Vector2D(3, 4);
		v.rotate(Math.PI / 3);
		assertEquals(new Vector2D((3 - 4 * Math.sqrt(3)) / 2, (4 + 3 * Math.sqrt(3)) / 2), v);
	}

	@Test
	public void rotatedTest() {
		Vector2D v = new Vector2D(3, 4);
		assertEquals(new Vector2D((3 - 4 * Math.sqrt(3)) / 2, (4 + 3 * Math.sqrt(3)) / 2), v.rotated(Math.PI / 3));
	}

	@Test
	public void scaleTest() {
		Vector2D v = new Vector2D(4, 9);
		v.scale(5);
		assertEquals(new Vector2D(20, 45), v);
	}

	@Test
	public void scaledTest() {
		Vector2D v = new Vector2D(4, 9);
		assertEquals(new Vector2D(20, 45), v.scaled(5));
	}

	@Test
	public void copyTest() {
		Vector2D v = new Vector2D(5, 15);
		assertEquals(new Vector2D(5, 15), v.copy());
	}

}
