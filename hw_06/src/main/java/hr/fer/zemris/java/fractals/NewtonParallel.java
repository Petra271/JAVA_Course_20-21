package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * The class is used for displaying Newton-Raphson fractals using
 * parallelization.
 * 
 * @author Petra
 *
 */
public class NewtonParallel {

	public static void main(String[] args) {

		int w = Runtime.getRuntime().availableProcessors();
		int t = 4 * Runtime.getRuntime().availableProcessors();
		if (args.length > 2)
			throw new IllegalArgumentException("Number of parameters is two at most.");

		if (args.length == 2) {
			if ((args[0].startsWith("--workers") || args[0].startsWith("-w"))
					&& (args[1].startsWith("--workers") || args[1].startsWith("-w"))
					|| (args[0].startsWith("--tracks") || args[0].startsWith("-t"))
							&& (args[1].startsWith("--tracks") || args[1].startsWith("-t")))
				throw new IllegalArgumentException("Duplicate arguments are not allowed.");
		}

		for (String s : args) {
			if (!s.startsWith("--workers") && !s.startsWith("-w") && !s.startsWith("--tracks") && !s.startsWith("-t"))
				throw new IllegalArgumentException("Invalid input.");
			w = NewtonParallel.parse(w, s, "--worker", "-w");
			t = NewtonParallel.parse(t, s, "--tracks", "-t");
		}

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
		FractalViewer.show(new NewtonProducer(p, w, t));

	}

	private static int parse(int num, String s, String s1, String s2) {
		if (s.startsWith(s1) || s.startsWith(s2))
			try {
				num = Integer.parseInt(s.trim().substring(s.indexOf('=') + 1, s.length()));
			} catch (NumberFormatException e) {
				System.err.println("Inavlid input");
				System.exit(1);
			}
		return num;
	}

	/**
	 * The class represents a job executed by a worker, ie. a thread.
	 * 
	 * @author Petra
	 *
	 */
	public static class Job implements Runnable {
		/**
		 * Minimum real display value
		 */
		private double reMin;
		/**
		 * Maximum real display value
		 */
		private double reMax;

		/**
		 * Minimum imaginary display value
		 */
		private double imMin;

		/**
		 * Maximum imaginary display value
		 */
		private double imMax;

		/**
		 * Screen width
		 */
		private int width;

		/**
		 * Screen height
		 */
		private int height;

		/**
		 * Minimum imaginary value
		 */
		private int yMin;

		/**
		 * Maximum imaginary value
		 */
		private int yMax;

		/**
		 * Number of iterations
		 */
		private int m;

		/**
		 * Array used for storing data
		 */
		private short[] data;

		/**
		 * A flag
		 */
		private AtomicBoolean cancel;

		/**
		 * Complex polynomial
		 */
		private ComplexPolynomial polynomial;

		/**
		 * Complex rooted polynomial
		 */
		private ComplexRootedPolynomial polynomialRooted;

		/**
		 * Convergence treshold
		 */
		private double treshold;

		/**
		 * Distance from root treshold
		 */
		private double rootTreshold;

		/**
		 * Creates an instance of this class.
		 */
		public final static Job NO_JOB = new Job();

		/**
		 * Creates an instance of this class.
		 */
		private Job() {
		}

		/**
		 * Creates an instance of this class with the given values.
		 */
		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				int m, short[] data, AtomicBoolean cancel, ComplexPolynomial polynomial,
				ComplexRootedPolynomial polynomialRooted, double treshold, double rootTreshold) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.polynomial = polynomial;
			this.polynomialRooted = polynomialRooted;
			this.treshold = treshold;
			this.rootTreshold = rootTreshold;

		}

		/**
		 * Calculates information needed for displaying Newton-Raphson fractals.
		 */
		@Override
		public void run() {
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
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
		}
	}

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
		private int workers;
		private int tracks;

		/**
		 * Creates an instance of this class with the given values.
		 * 
		 * @param polynomial complex rooted polynomial
		 * @param w          number of workers
		 * @param t          number of tracks
		 */
		public NewtonProducer(ComplexRootedPolynomial polynomial, int w, int t) {
			this.polynomialRooted = polynomial;
			this.polynomial = polynomial.toComplexPolynom();
			this.workers = w;
			this.tracks = t;
		}

		/**
		 * Handles thread parallelization.
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			final int brojTraka = this.tracks;
			int brojYPoTraci = height / brojTraka;

			final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();

			Thread[] radnici = new Thread[this.workers];
			for (int i = 0; i < radnici.length; i++) {
				radnici[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							Job p = null;
							try {
								p = queue.take();
								if (p == Job.NO_JOB)
									break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for (int i = 0; i < radnici.length; i++) {
				radnici[i].start();
			}

			for (int i = 0; i < brojTraka; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;
				if (i == brojTraka - 1) {
					yMax = height - 1;
				}
				Job posao = new Job(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, polynomial,
						polynomialRooted, treshold, rootTreshold);
				while (true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for (int i = 0; i < radnici.length; i++) {
				while (true) {
					try {
						queue.put(Job.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			for (int i = 0; i < radnici.length; i++) {
				while (true) {
					try {
						radnici[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
		}
	}

}
