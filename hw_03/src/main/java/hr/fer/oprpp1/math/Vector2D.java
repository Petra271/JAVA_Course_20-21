package hr.fer.oprpp1.math;

import java.util.Objects;

/**
 * The class represents a model of a 2D-vector.
 * 
 * @author Petra
 *
 */
public class Vector2D {

	/**
	 * Component of the unit vector i
	 */
	private double x;

	/**
	 * Component of the unit vector j
	 */
	private double y;

	/**
	 * Creates a new 2D vector whose components are x and y.
	 * 
	 * @param x component of the unit vector i
	 * @param y component of the unit vector j
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x component of this vector.
	 * 
	 * @return the x component
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Returns the y component of this vector.
	 * 
	 * @return the y component
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * 
	 * Calculates the sum of the current vector and the given one and returns the
	 * same vector increased by the given addend.
	 * 
	 * @param offset the second addend
	 * @throws {@link NullPointerException} if the given offset is <code>null</code>
	 */
	public void add(Vector2D offset) {
		Objects.requireNonNull(offset, "The offset can't be null");
		this.x += offset.x;
		this.y += offset.y;
	}

	/**
	 * Calculates the sum of the current vector and the given one and returns the
	 * result as a new vector.
	 * 
	 * @param offset the second addend
	 * @throws {@link NullPointerException} if the given offset is <code>null</code>
	 * @return a new vector which equals the sum of the current vector and the given
	 *         one
	 * 
	 */
	public Vector2D added(Vector2D offset) {
		Objects.requireNonNull(offset, "The offset can't be null");
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}

	/**
	 * Rotates the current vector by the given angle.
	 * 
	 * @param angle the angle by which the current vector is rotated
	 */
	public void rotate(double angle) {
		double x = Math.cos(angle) * this.x - Math.sin(angle) * this.y;
		double y = Math.sin(angle) * this.x + Math.cos(angle) * this.y;
		this.x = x;
		this.y = y;
	}

	/**
	 * Rotates the current vector by the given angle and returns the result as a new
	 * vector.
	 * 
	 * @param angle the angle by which the current vector is rotated
	 * @return a new vector which equals the current vector rotated by the given
	 *         angle
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(Math.cos(angle) * this.x - Math.sin(angle) * this.y,
				Math.sin(angle) * this.x + Math.cos(angle) * this.y);

	}

	/**
	 * Scales the current vector.
	 * 
	 * @param scaler the factor by which the vector is scaled.
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}

	/**
	 * Scales the current vector and returns the result as a new vector.
	 * 
	 * @param scaler the factor by which the vector is scaled.
	 * @return a new vector which equals the current vector scaled by the given
	 *         factor
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}

	/**
	 * Returns a new copy of the current vector.
	 * 
	 * @return a new copy of the current vector.
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	@Override
	public boolean equals(Object o) {
		Vector2D v = (Vector2D) o;
		if (Math.abs(this.x - v.x) < 1e-4 && Math.abs(this.y - v.y) < 1e-4) {
			return true;
		}
		return false;
	}

}
