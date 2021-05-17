package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ComplexTest {

	@Test
	public void testModule() {
		assertEquals((double) 8.60, (double) Math.round((new Complex(5, 7).module()) * 100) / 100);
	}

	@Test
	public void testAdd() {
		assertEquals(new Complex(21, 15).toString(), new Complex(10, 2).add(new Complex(11, 13)).toString());
	}

	@Test
	public void testSub() {
		assertEquals(new Complex(-1, -5).toString(), new Complex(10, 8).sub(new Complex(11, 13)).toString());
	}

	@Test
	public void testMul() {
		assertEquals(new Complex(4, 17).toString(), new Complex(2, 1).multiply(new Complex(5, 6)).toString());
	}

	@Test
	public void testDiv() {
		assertEquals(new Complex(-0.15, -2.77).toString(), new Complex(8, -6).divide(new Complex(2, 3)).toString());
	}

	@Test
	public void testPower() {
		assertEquals(new Complex(154, 414).toString(), new Complex(7, 3).power(3).toString());
	}

	@Test
	public void testNegate() {
		assertEquals(new Complex(-13, 123).toString(), new Complex(13, -123).negate().toString());
	}

	@Test
	public void testRoot() {
		String[] numbers = { new Complex(1.9, 0.26).toString(), new Complex(-1.20, 1.56).toString(),
				new Complex(-0.7, -1.82).toString() };
		List<Complex> roots = new Complex(7, 3).root(3);
		String[] rootsString = new String[3];
		int i = 0;
		for (Complex c : roots) {
			rootsString[i] = c.toString();
			i++;
		}
		assertArrayEquals(numbers, rootsString);
	}

}
