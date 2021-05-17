package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * The class demonstrates the usage of {@link ObjectStack} by calculating an
 * expression in postfix representation given through command line.
 * 
 * @author Petra
 *
 */
public class StackDemo {

	/**
	 * Main method.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String... args) {

		if (args.length != 1) {
			System.out.println("Check your input. The expression has to be enclosed into quotation marks.");
			System.exit(1);
		}

		String line = args[0].trim().replaceAll(" +", " ");
		String[] elements = line.split(" ");

		ObjectStack stack = new ObjectStack();
		for (String element : elements) {
			try {
				switch (element) {
				case "*", "/", "+", "-", "%" -> {
					int second = (int) stack.pop();
					int first = (int) stack.pop();
					if (element.equals("*"))
						stack.push(first * second);
					if (element.equals("/"))
						stack.push(first / second);
					if (element.equals("+"))
						stack.push(first + second);
					if (element.equals("-"))
						stack.push(first - second);
					if (element.equals("%"))
						stack.push(first % second);
				}
				default -> {
					try {
						stack.push(Integer.parseInt(element));
					} catch (NumberFormatException e) {
						System.err.println("The element '" + element + "' is neither a number nor an operator.");
					}
				}
				}

			} catch (EmptyStackException | ArithmeticException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
		}

		if (stack.size() != 1) {
			System.err.println("The expression is invalid.");
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}

	}

}
