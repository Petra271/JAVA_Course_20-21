package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.oprpp1.math.Vector2D;

/**
 * Represents the current state of a "turtle" used for drawing Lindenmayer
 * systems.
 * 
 * @author Petra
 *
 */
public class TurtleState {

	/**
	 * Current position of the turtle
	 */
	private Vector2D position;

	/**
	 * Current direction in which the turtle is headed
	 */
	private Vector2D direction;

	/**
	 * The colour used for drawing
	 */
	private Color colour;

	/**
	 * Given a number, the turtle will be shifted by value which is equal to the
	 * product of the given number and the effective shift value.
	 */
	private double effectiveShiftValue;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param position            turtle's position
	 * @param direction           direction in which the turtle is headed
	 * @param color               colour used for drawing
	 * @param effectiveShiftValue product of effective shift value and a given
	 *                            number is the value by which the turtle will be
	 *                            shifted
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveShiftValue) {
		this.position = position;
		this.direction = direction;
		this.colour = color;
		this.effectiveShiftValue = effectiveShiftValue;
	}

	/**
	 * Returns the turtle's position.
	 * 
	 * @return the turtle's position
	 */
	public Vector2D getPosition() {
		return this.position;
	}

	/**
	 * Returns the direction in which the turtle is headed.
	 * 
	 * @return the direction in which the turtle is headed
	 */
	public Vector2D getDirection() {
		return this.direction;
	}

	/**
	 * Returns the colour used for drawing.
	 * 
	 * @return the colour used for drawing
	 */
	public Color getColor() {
		return this.colour;
	}

	/**
	 * Returns the effective shift value.
	 * 
	 * @return the effective shift value
	 */
	public double getEffectiveShiftValue() {
		return this.effectiveShiftValue;
	}

	/**
	 * Returns a new copy of this turtle.
	 * 
	 * @return a new copy of this turtle
	 */
	public TurtleState copy() {
		return new TurtleState(this.position, this.direction, this.colour, this.effectiveShiftValue);
	}

	/**
	 * Turtle position setter
	 * 
	 * @param position new turtle position
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Turtle direction setter
	 * 
	 * @param direction new turtle direction
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Turtle colour setter
	 * 
	 * @param colour new colour used for drawing
	 */
	public void setColor(Color colour) {
		this.colour = colour;
	}

	/**
	 * Turtle effective shift value setter
	 * 
	 * @param effective shift value new turtle effective shift value
	 */
	public void setEffectiveShiftValue(double effectiveShiftValue) {
		this.effectiveShiftValue = effectiveShiftValue;
	}

}
