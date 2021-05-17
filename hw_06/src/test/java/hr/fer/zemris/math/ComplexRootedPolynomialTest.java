package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexRootedPolynomialTest {

	@Test
	public void testApply() {
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(new Complex(3, 5), new Complex(4, 5),
				new Complex(-4, 8), new Complex(123, 11));
		assertEquals(new Complex(-41860, 56940).toString(), p.apply(new Complex(6, -2)).toString());
	}

	@Test
	public void toComplexPolynomTest() {
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(new Complex(3, 5), new Complex(4, 5),
				new Complex(-4, 8), new Complex(123, 11));

		Complex e1 = new Complex(25360, 32520);
		Complex e2 = new Complex(-8652, 3838);
		Complex e3 = new Complex(-249, -687);
		Complex e4 = new Complex(3, 5);
		ComplexPolynomial ex = new ComplexPolynomial(e1, e2, e3, e4);

		assertEquals(ex.toString(), p.toComplexPolynom().toString());

	}

	@Test
	public void toStringTest() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		assertEquals(crp.toString(), "(2.0+i0.0)*(z-(1.0+i0.0))*(z-(-1.0+i0.0))*(z-(0.0+i1.0))*(z-(0.0-i1.0))");
		assertEquals(cp.toString(), "(2.0+i0.0)*z^4 + (0.0+i0.0)*z^3 + (0.0+i0.0)*z^2 + (0.0+i0.0)*z^1 + (-2.0+i0.0)");
		assertEquals(cp.derive().toString(), "(8.0+i0.0)*z^3 + (0.0+i0.0)*z^2 + (0.0+i0.0)*z^1 + (0.0+i0.0)");
	}

}
