package hr.fer.zemris.math;

/**
 * Represents a complex polynomial number zn*z^n+z^(n-1)*zn-1+...+z2*z^2+z1*z+z0
 * 
 * @author Petra
 *
 */
public class ComplexPolynomial {

	/**
	 * Roots of a complex polynomial
	 */
	private Complex[] factors;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param factors complex polynomial roots
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Returns the order of the polynomial.
	 * 
	 * @return the order of the polynomial
	 */
	public short order() {
		return (short) (this.factors.length - 1);
	}

	/**
	 * Multiplies two complex polynomials.
	 * 
	 * @param p number that a complex polynomial will be multiplied with
	 * @return product of the tow complex polynomials
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] computedFactors = new Complex[this.order() + p.order() + 1];
		Complex[] pFactors = p.factors();
		for (int i = 0; i < computedFactors.length; i++)
			computedFactors[i] = Complex.ZERO;

		for (int i = 0; i <= this.order(); i++) {
			for (int j = 0; j <= p.order(); j++) {
				computedFactors[i + j] = computedFactors[i + j].add(this.factors[i].multiply(pFactors[j]));
			}
		}

		return new ComplexPolynomial(computedFactors);
	}

	private Complex[] factors() {
		return this.factors;
	}

	/**
	 * Derives a complex polynomial.
	 * 
	 * @return derived complex polynomial.
	 */
	public ComplexPolynomial derive() {
		Complex[] computedFactors = new Complex[this.order()];

		for (int i = 0; i < this.factors.length - 1; i++) {
			computedFactors[i] = this.factors[i + 1].multiply(new Complex(i + 1, 0));
		}

		return new ComplexPolynomial(computedFactors);
	}

	/**
	 * Calculates value of the complex polynomial using the given point.
	 * 
	 * @param z complex number
	 * @return complex polynomial value
	 */
	public Complex apply(Complex z) {
		Complex c = Complex.ZERO;
		for (int i = 0; i < this.factors.length; i++) {
			c = c.add(z.power(i).multiply(factors[i]));
		}
		return c;
	}

	/**
	 * Returns string representation of the complex polynomial.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = this.order(); i >= 0; i--) {
			sb.append("(").append(this.factors[i]).append(")").append("*z^").append(i).append(" + ");
		}
		String s = sb.toString();
		return s.substring(0, s.lastIndexOf('*'));
	}

}
