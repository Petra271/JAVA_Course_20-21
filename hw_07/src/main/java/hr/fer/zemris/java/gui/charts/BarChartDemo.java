package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Used for displaying {@link BarChart}.
 * 
 * @author Petra
 *
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	private BarChart barChart;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param barChart chart
	 * @param path     path to file which contains chart details
	 */
	public BarChartDemo(BarChart barChart, String path) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(50, 50);
		setSize(500, 500);
		this.barChart = barChart;
		initGUI(path);
	}

	/**
	 * Used for initializing chart GUI.
	 * 
	 * @param path path to file which contains chart details
	 */
	private void initGUI(String path) {
		Container p = getContentPane();
		p.setBackground(Color.WHITE);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		JLabel label = new JLabel(path);
		label.setAlignmentX(CENTER_ALIGNMENT);
		p.add(label);

		p.add(new BarChartComponent(this.barChart));
	}

	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.out.println("Invalid input.");
			System.exit(1);
		}

		BarChart chart = null;
		int i = 0;
		String line = null;
		List<String> values = new ArrayList<>();
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(args[0]));
			while (((line = bufferedReader.readLine()) != null) && i < 6) {
				values.add(line);
				i++;
			}

			if (values.size() != 6)
				throw new IOException();

			String[] xyArray = values.get(2).split(" ");
			List<String[]> list = Stream.of(xyArray).map(v -> v.split(",")).collect(Collectors.toList());
			List<XYValue> xyValues = list.stream().map(v -> new XYValue(Integer.parseInt(v[0]), Integer.parseInt(v[1])))
					.collect(Collectors.toList());
			chart = new BarChart(xyValues, values.get(0), values.get(1), Integer.parseInt(values.get(3)),
					Integer.parseInt(values.get(4)), Integer.parseInt(values.get(5)));

		} catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
			System.out.println("File is invalid.");
			System.exit(1);
		} finally {
			bufferedReader.close();
		}

		BarChart bc = chart;

		SwingUtilities.invokeLater(() -> {
			BarChartDemo prozor = new BarChartDemo(bc, args[0]);
			prozor.setVisible(true);
		});
	}

}
