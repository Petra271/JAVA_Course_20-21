package hr.fer.zemris.java.gui.prim;

/**
 * The class represents a prime number generator.
 * 
 * @author Petra
 *
 */
public class PrimNumberGenerator {

	private int current = 2;

	/**
	 * Checks if a number is prime.
	 * 
	 * @param num value to be checked
	 * @return <code>true</code> if the given number is prime and <code>false</code>
	 *         otherwise.
	 */
	private boolean primNum(int num) {
		boolean prim = true;
		for (int i = 2; i <= num / 2; i++) {
			if (num % i == 0)
				return !prim;
		}
		return prim;
	}

	/**
	 * Returns the next prime number.
	 * 
	 * @return next prime number
	 */
	public int nextPrim() {
		while (!primNum(current))
			primNum(current++);
		return current++;
	}

}
