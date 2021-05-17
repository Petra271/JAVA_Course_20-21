package hr.fer.oprpp1.hw01;

import static java.lang.Math.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class represents an unmodifiable complex number.
 * 
 * @author Petra
 *
 */
public class ComplexNumber {

	/**
	 * Real part of a complex number.
	 */
	private double real;

	/**
	 * Imaginary part of a complex number.
	 */
	private double imaginary;

	/**
	 * Creates a complex number with real and imaginary part.
	 * 
	 * @param real      real part
	 * @param imaginary imaginary part
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Creates a complex number from the given real part.
	 * 
	 * @param real real part of the complex number
	 * @return created complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates a complex number from the given imaginary part.
	 * 
	 * @param imaginary imaginary part of a complex number
	 * @return created complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates a complex number from the given magnitude and angle.
	 * 
	 * @param magnitude magnitude of a complex number
	 * @angle angle of the complex number
	 * @return created complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	/**
	 * Parses a complex number from a string.
	 * 
	 * @param s complex number as a string
	 * @return created complex number
	 * @throws NumberFormatException if the input is not written properly and it can
	 *                               not be parsed.
	 */
	public static ComplexNumber parse(String s) {

		Pattern wholePattern = Pattern.compile("^([+,-]?\\d+[.]?\\d*)([+,-]?\\d*[.]?\\d*i)$");
		Pattern imaginaryPattern = Pattern.compile("^([+,-]?\\d*[.]?\\d*i)$");
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

			return new ComplexNumber(real, imaginary);
			// Check if the given number has only the imaginary part
		} else if (imaginaryMatcher.find() && !realMatcher.find()) {
			String imaginaryPart = imaginaryMatcher.group(1).replace("i", "");
			if (imaginaryPart.length() == 1 || imaginaryPart.trim().length() == 0) {
				imaginaryPart += "1";
			}

			double imaginary = Double.parseDouble(imaginaryPart);

			return fromImaginary(imaginary);
			// Check if the number has only the real part
		} else if (realMatcher.find()) {
			return fromReal(Double.parseDouble(s));
			// The numbers is not written properly
		} else {
			throw new NumberFormatException("The input is invalid. It can not be parsed.");
		}

	}

	/**
	 * Returns the real part of a complex number.
	 * 
	 * @return real part of a complex number
	 */
	public double getReal() {
		return this.real;
	}

	/**
	 * Returns the imaginary part of the complex number.
	 * 
	 * @return imaginary part of a complex number
	 */
	public double getImaginary() {
		return this.imaginary;
	}

	/**
	 * Returns the magnitude of a complex number.
	 * 
	 * @return magnitude of a complex number
	 */
	public double getMagnitude() {
		return hypot(this.real, this.imaginary);
	}

	/**
	 * Returns the angle of a complex number.
	 * 
	 * @return angle of a complex number
	 */
	public double getAngle() {
	
	    if (Double.compare(atan(this.imaginary / this.real), 0) < 0) {
			return Math.atan(this.imaginary / this.real) + 2*Math.PI;
		}
		return Math.atan(this.imaginary / this.real);
	}

	/**
	 * Adds two complex numbers.
	 * 
	 * @param c number that will be added
	 * @return sum of two complex numbers
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Substitutes two complex numbers.
	 * 
	 * @param c number that will be substituted
	 * @return difference of two complex numbers
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Multiplies two complex numbers.
	 * 
	 * @param c number that a complex number will be multiplied with
	 * @return product of two complex numbers
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
				this.real * c.imaginary + this.imaginary * c.real);
	}

	/**
	 * Divides two complex numbers.
	 * 
	 * @param c divisor
	 * @return quotient of two complex numbers
	 */
	public ComplexNumber div(ComplexNumber c) {
		double divisor = c.real * c.real + c.imaginary * c.imaginary;
		return new ComplexNumber((this.real * c.real + this.imaginary * c.imaginary) / divisor,
				(this.imaginary * c.real - this.real * c.imaginary) / divisor);
	}

	/**
	 * Raises a complex number to the given exponent
	 * 
	 * @param n exponent to which a complex number will be raised
	 * @return raised complex number
	 * @throws IllegalArgumentException if the given exponent is less than 0
	 */
	public ComplexNumber power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n has to be greater than or equal to 0");

		double real = pow(this.getMagnitude(), n) * cos(n * this.getAngle());
		double imaginary = pow(this.getMagnitude(), n) * sin(n * this.getAngle());
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Calculates the nth root of a complex number.
	 * 
	 * @param n the base
	 * @return array of the roots a complex number
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n has to be greater than 0");

		double real = 0.0;
		double imaginary = 0.0;
		ComplexNumber[] roots = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			real = pow(this.getMagnitude(), 1.0 / n) * cos((this.getAngle() + 2 * i * Math.PI) / n);
			imaginary = pow(this.getMagnitude(), 1.0 / n) * sin((this.getAngle() + 2 * i * Math.PI) / n);
			roots[i] = new ComplexNumber(real, imaginary);
		}
		return roots;
	}

	/**
	 * Return String representation of a complex number.
	 */
	@Override
	public String toString() {
		if (Double.compare(this.imaginary, 0) == 0) {
			return String.format("%.2f", this.real);
		} else if (Double.compare(this.real, 0) == 0) {
			return String.format("%.2fi", this.imaginary);
		} else {
			if (this.imaginary < 0)
				return String.format("%.2f%.2fi", this.real, this.imaginary);
			else {
				return String.format("%.2f+%.2fi", this.real, this.imaginary);
			}
		}
	}

}
