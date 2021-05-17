package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexPolynomialTest {

	@Test
	public void orderTest() {
		Complex z0 = new Complex(8, 9);
		Complex z1 = new Complex(-7, 7);
		Complex z2 = new Complex(5, 354);
		ComplexPolynomial p = new ComplexPolynomial(z0, z1, z2);
		assertEquals(2, p.order());
	}

	@Test
	public void multiplyTest() {
		Complex z0 = new Complex(8, 9);
		Complex z1 = new Complex(-7, 7);
		Complex z2 = new Complex(5, 354);
		ComplexPolynomial p1 = new ComplexPolynomial(z0, z1, z2);
		assertEquals(2, p1.order());

		Complex z3 = new Complex(34, 5);
		Complex z4 = new Complex(-3, -4);
		Complex z5 = new Complex(5, -45);
		ComplexPolynomial p2 = new ComplexPolynomial(z3, z4, z5);
		assertEquals(2, p2.order());

		Complex e1 = new Complex(227, 346);
		Complex e2 = new Complex(-261, 144);
		Complex e3 = new Complex(-1106, 11753);
		Complex e4 = new Complex(1681, -732);
		Complex e5 = new Complex(15955, 1545);
		ComplexPolynomial expected = new ComplexPolynomial(e1, e2, e3, e4, e5);
		assertEquals(expected.toString(), p1.multiply(p2).toString());

	}

	@Test
	public void deriveTest() {
		Complex z0 = new Complex(1, 0);
		Complex z1 = new Complex(5, 0);
		Complex z2 = new Complex(2, 0);
		Complex z3 = new Complex(7, 2);
		ComplexPolynomial p1 = new ComplexPolynomial(z0, z1, z2, z3);

		ComplexPolynomial derived = p1.derive();

		Complex e1 = new Complex(5, 0);
		Complex e2 = new Complex(4, 0);
		Complex e3 = new Complex(21, 6);
		ComplexPolynomial expected = new ComplexPolynomial(e1, e2, e3);

		assertEquals(expected.toString(), derived.toString());
	}

	@Test
	public void applyTest() {
		Complex z0 = new Complex(1, 0);
		Complex z1 = new Complex(5, 6);
		Complex z2 = new Complex(2, -456);
		Complex z3 = new Complex(7, 2);
		ComplexPolynomial p1 = new ComplexPolynomial(z0, z1, z2, z3);

		Complex res = p1.apply(new Complex(5, -23));
		Complex expected = new Complex(-181278, 286753);
		assertEquals(expected.toString(), res.toString());

	}

}
