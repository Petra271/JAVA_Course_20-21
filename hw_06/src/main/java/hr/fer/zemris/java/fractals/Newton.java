package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * The class is used for displaying Newton-Raphson fractals sequentially.
 * 
 * @author Petra
 *
 */
public class Newton {

	/**
	 * Implementation of {@link IFractalProducer}.
	 * 
	 * @author Petra
	 *
	 */
	public static class NewtonProducer implements IFractalProducer {

		private ComplexRootedPolynomial polynomialRooted;
		private ComplexPolynomial polynomial;
		private double treshold = 1e-3;
		private double rootTreshold = 2e-3;

		/**
		 * Creates an instance of {@link NewtonProducer}.
		 * 
		 * @param polynomial complex rooted polynomial
		 */
		public NewtonProducer(ComplexRootedPolynomial polynomial) {
			this.polynomialRooted = polynomial;
			this.polynomial = polynomial.toComplexPolynom();
		}

		/**
		 * Produces all information needed for displaying the Newton-Raphson fractals.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			int offset = 0;
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];

			for (int y = 0; y < height; y++) {
				if (cancel.get())
					break;

				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;

					Complex zn = new Complex(cre, cim);

					int iters = 0;
					double module = 0;

					ComplexPolynomial derived = polynomial.derive();

					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while (module > treshold && iters < m);

					int index = polynomialRooted.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index + 1);

				}
			}
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}

		public static void main(String[] args) {
			System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\n"
					+ "Please enter at least two roots, one root per line. Enter 'done' when done.");

			Scanner sc = new Scanner(System.in);
			int count = 1;
			List<Complex> roots = new ArrayList<>();

			while (true) {
				System.out.print("Root " + count + " > ");
				String root = sc.nextLine().trim();
				if (root.equals("done"))
					break;

				try {
					Complex c = Complex.parse(root);
					roots.add(c);
				} catch (NumberFormatException e) {
					System.err.println("The input is invalid.");
					count--;
				}

				count++;
			}

			sc.close();

			System.out.println("Image of fractal will appear shortly. Thank you.");
			Complex[] rootsArray = new Complex[roots.size()];

			ComplexRootedPolynomial p = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(rootsArray));
			FractalViewer.show(new NewtonProducer(p));

		}

	}

}