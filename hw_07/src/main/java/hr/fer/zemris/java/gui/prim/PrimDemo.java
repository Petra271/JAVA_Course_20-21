package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Represents a frame which contains two {@link JList} lists used for displaying
 * prime numbers.
 * 
 * @author Petra
 *
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an instance of this class.
	 */
	public PrimDemo() {
		setLocation(50, 50);
		setSize(300, 300);
		setTitle("Prime Numbers");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Used for initializing GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel m = new PrimListModel();

		JList<Integer> list1 = new JList<>(m);
		JList<Integer> list2 = new JList<>(m);

		JPanel bottom = new JPanel(new GridLayout(1, 0));

		JButton nextButton = new JButton("next");
		bottom.add(nextButton);

		PrimNumberGenerator generator = new PrimNumberGenerator();

		nextButton.addActionListener(e -> {
			m.next(generator.nextPrim());
		});

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));

		cp.add(central, BorderLayout.CENTER);
		cp.add(bottom, BorderLayout.PAGE_END);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.setSize(300, 200);
			frame.setLocation(50, 50);
			frame.setVisible(true);
		});
	}

}
