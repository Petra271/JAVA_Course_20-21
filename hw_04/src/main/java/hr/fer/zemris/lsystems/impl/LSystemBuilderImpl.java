package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import hr.fer.oprpp1.custom.collections.Dictionary;
import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

/**
 * The class implements {@link LSystemBuilder} and it is used for creating a
 * {@link LSystem}.
 * 
 * @author Petra
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle = 0;
	private String axiom = "";

	/**
	 * A dictionary used for storing productions.
	 */
	private Dictionary<Character, String> productions;

	/**
	 * A dictionary used for storing commands.
	 */
	private Dictionary<Character, Command> commands;

	/**
	 * Creates an instance of this class.
	 */
	public LSystemBuilderImpl() {
		this.productions = new Dictionary<>();
		this.commands = new Dictionary<>();
	}

	/**
	 * Build a {@link LSystem}. Generates generations based on the given depth value
	 * and draws shapes.
	 */
	@Override
	public LSystem build() {
		return new LSystem() {

			@Override
			public String generate(int d) {
				String previous = axiom;
				StringBuilder next = new StringBuilder();

				while (d > 0) {

					Stream<Character> stream = previous.chars().mapToObj(c -> (char) c);
					stream.forEach(p -> {
						String production = productions.get(p);
						if (production == null)
							next.append(p);
						else
							next.append(production);
					});
					previous = next.toString();
					next.delete(0, next.length());
					d--;
				}

				return previous;
			}

			@Override
			public void draw(int d, Painter painter) {
				Context ctx = new Context();
				TurtleState state = new TurtleState(origin, new Vector2D(1, 0).rotated(Math.toRadians(angle)),
						Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, d));
				ctx.pushState(state);

				String s = generate(d);
				Stream<Character> stream = s.chars().mapToObj(c -> (char) c);
				stream.forEach(c -> {
					Command command = commands.get(c);
					if (command != null) {
						command.execute(ctx, painter);
					}
				});
			}
		};
	}

	/**
	 * Registers commands and puts them in a dictionary.
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String value) {
		String[] elems = value.split("\\s+");

		switch (elems[0]) {
		case "draw" -> this.commands.put(symbol, new DrawCommand(commandProcessor("draw", elems)));
		case "skip" -> this.commands.put(symbol, new SkipCommand(commandProcessor("skip", elems)));
		case "scale" -> this.commands.put(symbol, new ScaleCommand(commandProcessor("scale", elems)));
		case "rotate" -> this.commands.put(symbol, new RotateCommand(commandProcessor("rotate", elems)));
		case "push" -> this.commands.put(symbol, new PushCommand());
		case "pop" -> this.commands.put(symbol, new PopCommand());
		case "color" -> {
			try {
				commands.put(symbol, new ColorCommand(Color.decode("#" + elems[1])));
			} catch (NumberFormatException ex) {
				throw new RuntimeException("The color command number can not be parsed.");
			}
		}
		default -> throw new RuntimeException("The action " + elems[0] + " is invalid");
		}

		return this;
	}

	private double commandProcessor(String name, String[] elems) {
		double step = 0;
		try {
			step = Double.parseDouble(elems[1]);
		} catch (NumberFormatException e) {
			System.err.println("The " + name + " command number can not be parsed");
			System.exit(1);
		}
		return step;
	}

	/**
	 * Registers productions and puts them in a dictionary.
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String product) {
		this.productions.put(symbol, product);
		return this;
	}

	/**
	 * Parsed each line in the given string list and sets the {@link LSystem}
	 * values.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {

		for (String line : lines) {
			if (line.isBlank())
				continue;

			line = line.replaceAll("\\s+", " ");
			String[] lineElems = line.split(" ");

			if (lineElems[0].equals("origin")) {
				if (lineElems.length != 3)
					throw new IllegalArgumentException("The origin input is invalid");

				try {
					this.origin = new Vector2D(Double.parseDouble(lineElems[1]), Double.parseDouble(lineElems[2]));
				} catch (NumberFormatException e) {
					System.err.println("The origin input is invalid");
					System.exit(1);
				}
			}

			else if (lineElems[0].equals("angle")) {
				if (lineElems.length != 2)
					throw new IllegalArgumentException("The angle input is invalid");

				try {
					this.angle = Double.parseDouble(lineElems[1]);
				} catch (NumberFormatException e) {
					System.err.println("The angle input is invalid");
					System.exit(1);
				}
			}

			else if (lineElems[0].equals("unitLength")) {
				if (lineElems.length != 2)
					throw new IllegalArgumentException("The unit length input is invalid");

				try {
					this.unitLength = Double.parseDouble(lineElems[1]);
				} catch (NumberFormatException e) {
					System.err.println("The unit length input is invalid");
					System.exit(1);
				}
			}

			else if (lineElems[0].equals("unitLengthDegreeScaler")) {
				if (lineElems.length < 2 || lineElems.length > 4)
					throw new IllegalArgumentException("The unit length degree scaler input is invalid");

				String[] values = Arrays.copyOfRange(lineElems, 1, lineElems.length);
				String valueString = String.join(" ", values);
				values = valueString.split("/");

				if (values.length != 1 && values.length != 2)
					throw new IllegalArgumentException("The unit length degree scaler input is invalid");

				try {
					double first = Double.parseDouble(values[0]);
					double second = 1.0;
					if (values.length == 2) {
						second = Double.parseDouble(values[1]);
					}
					this.unitLengthDegreeScaler = first / second;
				} catch (NumberFormatException e) {
					System.err.println("The unit length input is invalid");
					System.exit(1);
				}

			}

			else if (lineElems[0].equals("command")) {
				if (lineElems.length < 3 || lineElems.length > 4)
					throw new IllegalArgumentException("The command input is invalid");

				if (lineElems[1].length() != 1)
					throw new IllegalArgumentException("The symbol length must be equal to 1.");

				String[] s = Arrays.copyOfRange(lineElems, 2, lineElems.length);
				String elems = Arrays.stream(s).collect(Collectors.joining(" "));
				this.registerCommand(lineElems[1].charAt(0), elems);

			}

			else if (lineElems[0].equals("axiom")) {
				if (lineElems.length != 2)
					throw new IllegalArgumentException("The axiom input is invalid");
				this.axiom = lineElems[1];
			}

			else if (lineElems[0].equals("production")) {
				if (lineElems.length != 3)
					throw new IllegalArgumentException("The production input is invalid");

				if (lineElems[1].length() != 1)
					throw new IllegalArgumentException("The symbol length must be equal to 1.");

				if (productions.get(lineElems[1].charAt(0)) != null)
					throw new IllegalArgumentException(
							"Configuration can contain only for production for each symbol.");

				this.registerProduction(lineElems[1].charAt(0), lineElems[2]);

			}

			else {
				throw new IllegalArgumentException("The " + lineElems[0] + " input is invalid.");
			}

		}
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scaler) {
		this.unitLengthDegreeScaler = scaler;
		return this;
	}

}
