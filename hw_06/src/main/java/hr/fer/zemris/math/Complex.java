package hr.fer.zemris.math;

import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class represents a complex number.
 * 
 * @author Petra
 *
 */
public class Complex {

	/**
	 * Real part of a complex number.
	 */
	private double re;

	/**
	 * Imaginary part of a complex number.
	 */
	private double im;

	/**
	 * Complex number with real and imaginary part equal to 0.
	 */
	public static final Complex ZERO = new Complex(0, 0);

	/**
	 * Complex number with real part equal to 1 and imaginary part equal to 0.
	 */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Complex number with real part equal to -1 and imaginary part equal to 0.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * Complex number with real part equal to 0 and imaginary part equal to 1.
	 */
	public static final Complex IM = new Complex(0, 1);

	/**
	 * Complex number with real part equal to 0 and imaginary part equal to -1.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Creates an instance of this class.
	 * 
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Basic constructor.
	 */
	public Complex() {
	}

	/**
	 * Returns the module of a complex number.
	 * 
	 * @return module of a complex number
	 */
	public double module() {
		return hypot(this.re, this.im);
	}

	/**
	 * Multiplies two complex numbers.
	 * 
	 * @param c number that a complex number will be multiplied with
	 * @return product of two complex numbers
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.re * c.re - this.im * c.im, this.re * c.im + this.im * c.re);
	}

	/**
	 * Divides two complex numbers.
	 * 
	 * @param c divisor
	 * @return quotient of two complex numbers
	 */
	public Complex divide(Complex c) {
		double divisor = c.re * c.re + c.im * c.im;
		return new Complex((this.re * c.re + this.im * c.im) / divisor, (this.im * c.re - this.re * c.im) / divisor);
	}

	/**
	 * Adds two complex numbers.
	 * 
	 * @param c number that will be added
	 * @return sum of two complex numbers
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}

	/**
	 * Substitutes two complex numbers.
	 * 
	 * @param c number that will be substituted
	 * @return difference of two complex numbers
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}

	/**
	 * Negates a complex number.
	 * 
	 * @return negated complex number
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}

	/**
	 * Raises a complex number to the given exponent
	 * 
	 * @param n exponent to which a complex number will be raised
	 * @return raised complex number
	 * @throws IllegalArgumentException if the given exponent is less than 0
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n has to be greater than or equal to 0");

		double real = pow(this.module(), n) * cos(n * this.getAngle());
		double imaginary = pow(this.module(), n) * sin(n * this.getAngle());
		return new Complex(real, imaginary);
	}

	/**
	 * Returns the angle of a complex number.
	 * 
	 * @return angle of a complex number
	 */
	private double getAngle() {
		if (Double.compare(Math.atan2(this.im, this.re), 0) < 0) {
			return Math.atan2(this.im, this.re) + 2 * Math.PI;
		}
		return Math.atan2(this.im, this.re);
	}

	/**
	 * Calculates the nth root of a complex number.
	 * 
	 * @param n the base
	 * @return list of the roots a complex number
	 */
	public List<Complex> root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n has to be greater than 0");

		double real = 0.0;
		double imaginary = 0.0;
		Complex[] roots = new Complex[n];
		for (int i = 0; i < n; i++) {
			real = pow(this.module(), 1.0 / n) * cos((this.getAngle() + 2 * i * Math.PI) / n);
			imaginary = pow(this.module(), 1.0 / n) * sin((this.getAngle() + 2 * i * Math.PI) / n);
			roots[i] = new Complex(real, imaginary);
		}
		return Arrays.asList(roots);
	}

	/**
	 * Return string representation of a complex number.
	 */
	@Override
	public String toString() {
		if (this.im < 0)
			return String.format("%.1f-i%.1f", this.re, -this.im);
		else
			return String.format("%.1f+i%.1f", this.re, this.im);

	}

	/**
	 * Parses a complex number from a string.
	 * 
	 * @param s complex number as a string
	 * @return created complex number
	 * @throws NumberFormatException if the input is not written properly and it can
	 *                               not be parsed.
	 */
	public static Complex parse(String s) {

		Pattern wholePattern = Pattern.compile("^([+,-]?\\d+[.]?\\d*)([+,-]?i\\d*[.]?\\d*)$");
		Pattern imaginaryPattern = Pattern.compile("^([+,-]?i\\d*[.]?\\d*)$");
		Pattern realPattern = Pattern.compile("^([+,-]?\\d+[.]?\\d*)");

		Matcher wholeMatcher = wholePattern.matcher(s.trim());
		Matcher imaginaryMatcher = imaginaryPattern.matcher(s.trim());
		Matcher realMatcher = realPattern.matcher(s.trim());

		// Check if the given number has both real and imaginary part
		if (wholeMatcher.find()) {
			String imaginaryPart = wholeMatcher.group(2).replace("i", "");
			if (imaginaryPart.length() == 1)
				imaginaryPart += "1";

			double real = Double.parseDouble(wholeMatcher.group(1));
			double imaginary = Double.parseDouble(imaginaryPart);

			return new Complex(real, imaginary);
			// Check if the given number has only the imaginary part
		} else if (imaginaryMatcher.find() && !realMatcher.find()) {
			String imaginaryPart = imaginaryMatcher.group(1).replace("i", "");
			if (imaginaryPart.length() == 1 || imaginaryPart.trim().length() == 0) {
				imaginaryPart += "1";
			}

			double imaginary = Double.parseDouble(imaginaryPart);

			return new Complex(0, imaginary);
			// Check if the number has only the real part
		} else if (realMatcher.find()) {
			return new Complex(Double.parseDouble(s), 0);
			// The numbers is not written properly
		} else {
			throw new NumberFormatException("The input is invalid. It can not be parsed.");
		}

	}

}
