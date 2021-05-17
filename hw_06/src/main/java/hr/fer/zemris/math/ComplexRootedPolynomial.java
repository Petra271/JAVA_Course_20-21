package hr.fer.zemris.math;

/**
 * Represents a complex rooted polynomial z0*(z-z1)*(z-z2)*...*(z-zn).
 * 
 * @author Petra
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Complex rooted polynomial constant.
	 */
	private Complex constant;

	/**
	 * Complex rooted polynomial roots.
	 */
	private Complex[] roots;

	/**
	 * Creates an instant of this class.
	 * 
	 * @param constant complex rooted polynomial constant
	 * @param roots    complex rooted polynomial roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * Calculates the value of the complex rooted polynomial using the given point.
	 * 
	 * @param z complex point
	 * @return value of the complex rooted polynomial
	 */
	public Complex apply(Complex z) {
		return this.toComplexPolynom().apply(z);
	}

	/**
	 * Transforms {@link ComplexRootedPolynomial} to {@link ComplexPolynomial}.
	 * 
	 * @return {@link ComplexPolynomial}
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial res = new ComplexPolynomial(this.constant);

		for (Complex c : this.roots)
			res = res.multiply(new ComplexPolynomial(c.negate(), Complex.ONE));

		return res;
	}

	/**
	 * Returns string representation of the complex rooted polynomial.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("(").append(this.constant.toString()).append(")*");
		for (Complex c : this.roots) {
			sb.append("(z-(").append(c.toString()).append("))*");
		}
		String s = sb.toString();
		return s.substring(0, s.lastIndexOf('*'));

	}

	/**
	 * Calculates the index of the root closest to the given complex number which
	 * must be within the given treshold.
	 * 
	 * @param z        complex number
	 * @param treshold treshold
	 * @return index of the closest root and -1 if it doesn't exist
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;

		for (int i = 0; i < roots.length; i++) {
			double current = z.sub(roots[i]).module();
			if (current < treshold) {
				treshold = current;
				index = i;
			}
		}

		return index;
	}

}
