package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizableJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;

/**
 * The class represents a text editor.
 * 
 * @author Petra
 *
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	private DefaultMultipleDocumentModel model;
	private JLabel length = new JLabel();
	private JLabel stats = new JLabel();
	private JLabel time = new JLabel();
	private int selected;
	private JMenuItem toUpperCase;
	private JMenuItem toLowerCase;
	private JMenuItem invertCase;
	private ILocalizationProvider provider;

	private Action createBlankDocumentAction;
	private Action openDocumentAction;
	private Action saveDocumentAction;
	private Action saveAsDocumentAction;
	private Action closeDocumentAction;
	private Action cutAction;
	private Action copyAction;
	private Action pasteAction;
	private Action statisticsAction;
	private Action exitAction;
	private Action englishAction;
	private Action croatianAction;
	private Action germanAction;
	private Action toUpperCaseAction;
	private Action toLowerCaseAction;
	private Action invertCaseAction;
	private Action ascendingAction;
	private Action descendingAction;
	private Action uniqueAction;

	/**
	 * Constructor.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1000, 700);
		this.provider = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		initGUI();
		setLocationRelativeTo(null);
	}

	/**
	 * Initializes graphical user interface.
	 */
	private void initGUI() {

		DefaultMultipleDocumentModel model = new DefaultMultipleDocumentModel();
		this.model = model;

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(model, BorderLayout.CENTER);

		initActions();
		createActions();
		createMenus();
		createToolbars();

		this.model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				String path = model.getTitleAt(model.getSelectedIndex());
				setTitle(path + " - JNotepad++");
			}
		});

		this.model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {

				currentModel.getTextComponent().addCaretListener(e -> updateState());
				if(model.getCurrentDocument()!=null)
					updateState();

			}

		});

		new Timer(1000, e -> {
			LocalDateTime now = LocalDateTime.now();

			int y = now.getYear();
			int m = now.getMonth().getValue();
			int d = now.getDayOfMonth();

			int hour = now.getHour();
			int min = now.getMinute();
			int sec = now.getSecond();

			this.time.setText(String.format(" %d/%02d/%02d %02d:%02d:%02d ", y, d, m, hour, min, sec));

		}).start();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}

			@Override
			public void windowOpened(WindowEvent e) {
				createBlankDocumentAction.actionPerformed(null);
				if (model.getNumberOfDocuments() == 1)
					model.setSelectedIndex(0);
			}

		});

	}

	/**
	 * Initalizes actions.
	 */
	private void initActions() {

		createBlankDocumentAction = new LocalizableAction("new", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
				updateState();
			}
		};

		openDocumentAction = new LocalizableAction("open", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Open file");

				if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION)
					return;

				File fileName = fc.getSelectedFile();
				Path filePath = fileName.toPath();

				String[] options = new String[] { provider.getString("ok") };
				try {
					model.loadDocument(filePath);
				} catch (RuntimeException ex) {
					JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("openerror"), "Error",
							JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					return;
				}

				updateState();
			}
		};

		saveDocumentAction = new LocalizableAction("save", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (model.getCurrentDocument().getFilePath() == null) {
					saveAsDocumentAction.actionPerformed(e);
					return;
				}

				String[] options = new String[] { provider.getString("ok") };
				try {
					model.saveDocument(model.getCurrentDocument(), null);
				} catch (RuntimeException ex) {

					JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("saveerror"),
							provider.getString("error"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,
							options, options[0]);
					return;
				}

				JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("savesuccess"),
						provider.getString("info"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
						options, options[0]);
			}

		};

		saveAsDocumentAction = new LocalizableAction("saveas", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle(provider.getString("savedialogtitle"));

				String[] options = new String[] { provider.getString("ok") };
				if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("notsaved"),
							provider.getString("warning"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
							null, options, options[0]);
					return;
				}
				Path openedFilePath = jfc.getSelectedFile().toPath();

				String[] optionsYesNo = new String[] { provider.getString("yes"), provider.getString("no") };
				if (Files.exists(openedFilePath)) {
					int response = JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("replacefile"),
							provider.getString("confirm"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, optionsYesNo, optionsYesNo[0]);
					if (response != 0)
						return;
				}

				try {
					model.saveDocument(model.getCurrentDocument(), openedFilePath);
				} catch (RuntimeException ex) {
					ex.printStackTrace();
					JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("saveerror"),
							provider.getString("error"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,
							options, options[0]);
					return;
				}

				JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("savesuccess"),
						provider.getString("info"), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
						options, options[0]);
				updateState();
			}
		};

		closeDocumentAction = new LocalizableAction("close", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!model.getCurrentDocument().isModified()) {
					model.closeDocument(model.getCurrentDocument());
					return;
				}

				String[] optionsYesNoCancel = new String[] { provider.getString("yes"), provider.getString("no"),
						provider.getString("cancel") };
				int response = JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("closesave"),
						provider.getString("closing"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						optionsYesNoCancel, optionsYesNoCancel[0]);

				if (response == 2)
					return;

				if (response == 0)
					saveDocumentAction.actionPerformed(e);

				model.closeDocument(model.getCurrentDocument());

				updateState();
			}

		};

		cutAction = new LocalizableAction("cut", this.provider) {

			private static final long serialVersionUID = 1L;

			Action action = new DefaultEditorKit.CutAction();

			@Override
			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(e);
			}

		};

		copyAction = new LocalizableAction("copy", this.provider) {

			private static final long serialVersionUID = 1L;

			Action action = new DefaultEditorKit.CopyAction();

			@Override
			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(e);
			}
		};

		pasteAction = new LocalizableAction("paste", this.provider) {

			private static final long serialVersionUID = 1L;

			Action action = new DefaultEditorKit.PasteAction();

			@Override
			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(e);
			}
		};

		exitAction = new LocalizableAction("exit", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				int res;
				for (int i = 0; i < model.getNumberOfDocuments(); i++) {

					model.setSelectedIndex(i);
					updateState();
					String[] optionsYesNoCancel = new String[] { provider.getString("yes"), provider.getString("no"),
							provider.getString("cancel") };
					if (model.getDocument(i).isModified()) {
						res = JOptionPane.showOptionDialog(JNotepadPP.this, provider.getString("exitsave"),
								provider.getString("exiting"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
								null, optionsYesNoCancel, optionsYesNoCancel[0]);

						if (res == JOptionPane.CANCEL_OPTION)
							return;

						if (res == JOptionPane.YES_OPTION)
							saveDocumentAction.actionPerformed(e);

						updateState();
					}
				}
				dispose();

			}
		};

		toUpperCaseAction = new LocalizableAction("upper", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCase(s -> s.toUpperCase());
			}
		};

		toLowerCaseAction = new LocalizableAction("lower", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCase(s -> s.toLowerCase());
			}
		};

		invertCaseAction = new LocalizableAction("invert", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				changeCase(s -> invertCase(s));
			}

			private String invertCase(String text) {
				char[] znakovi = text.toCharArray();
				for (int i = 0; i < znakovi.length; i++) {
					char c = znakovi[i];
					if (Character.isLowerCase(c)) {
						znakovi[i] = Character.toUpperCase(c);
					} else if (Character.isUpperCase(c)) {
						znakovi[i] = Character.toLowerCase(c);
					}
				}
				return new String(znakovi);
			}
		};

		ascendingAction = new LocalizableAction("asc", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sortAction(true);
			}
		};

		descendingAction = new LocalizableAction("desc", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				sortAction(false);
			}
		};

		statisticsAction = new LocalizableAction("stats", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextArea textArea = model.getCurrentDocument().getTextComponent();

				int numOfLines = textArea.getLineCount();
				int totalNumberOfChars = textArea.getText().length();
				int withoutBlankChars = textArea.getText().replaceAll("\\s+", "").length();

				String stats = "Your document has " + totalNumberOfChars + " characters, " + withoutBlankChars
						+ " non-blank characters and " + numOfLines + " lines.";

				String[] options = new String[] { provider.getString("ok") };
				JOptionPane.showOptionDialog(JNotepadPP.this, stats, "Info", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			}
		};

		croatianAction = new LocalizableAction("hr", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
				updateState();
			}
		};

		englishAction = new LocalizableAction("en", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
				updateState();
			}
		};

		germanAction = new LocalizableAction("de", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
				updateState();
			}
		};

		uniqueAction = new LocalizableAction("unique", this.provider) {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextArea editor = model.getCurrentDocument().getTextComponent();
				Document doc = editor.getDocument();

				int[] values = calculateSelectedTextLenght(editor);
				if (values == null)
					return;

				int len = values[0];
				int offset = values[1];

				try {
					offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
					len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));

					String text = doc.getText(offset, len - offset);

					Set<String> lines = new TreeSet<>(Arrays.asList(text.split("\\r?\\n")));

					StringBuilder sb = new StringBuilder();
					lines.stream().forEach(l -> sb.append(l).append("\n"));
					doc.remove(offset, len);
					doc.insertString(offset, sb.toString(), null);
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		};

	}

	/**
	 * Changes casing.
	 * 
	 * @param type upper or lower case
	 */
	private void changeCase(Function<String, String> type) {
		SingleDocumentModel document = model.getCurrentDocument();
		JTextArea editor = document.getTextComponent();
		Document doc = editor.getDocument();

		int[] values = calculateSelectedTextLenght(editor);
		if (values == null)
			return;

		int offset = values[1];
		int len = values[0];

		try {
			String text = doc.getText(offset, len);
			text = type.apply(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sorts text in ascending or descending order.
	 * 
	 * @param asc type of sorting
	 */
	private void sortAction(boolean asc) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();

		int[] values = calculateSelectedTextLenght(editor);
		if (values == null)
			return;

		int len = values[0];
		int offset = values[1];

		try {
			offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
			len = editor.getLineEndOffset(editor.getLineOfOffset(len + offset));

			String text = doc.getText(offset, len - offset);

			doc.remove(offset, len);
			doc.insertString(offset, sort(text, asc), null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sorts text in ascending or descending order based on the given boolean value.
	 * 
	 * @param s   text which will be sorted
	 * @param asc type of sorting
	 * @return sorted text
	 */
	private String sort(String s, boolean asc) {

		StringBuilder sb = new StringBuilder();
		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
		Collator col = Collator.getInstance(locale);

		List<String> lines = new ArrayList<>(Arrays.asList(s.split("\\r?\\n")));
		if (asc)
			lines.sort(col);
		else
			lines.sort(col.reversed());

		lines.stream().forEach(l -> sb.append(l).append("\n"));

		return sb.toString();
	}

	/**
	 * Updates editor state.
	 */
	private void updateState() {
		SingleDocumentModel current = model.getCurrentDocument();
		JTextArea textArea = current.getTextComponent();

		String path = model.getTitleAt(model.getSelectedIndex());
		setTitle(path + " - JNotepad++");

		this.length.setText(String.format(" %s:%d", this.provider.getString("length"), textArea.getText().length()));

		int col = textArea.getCaretPosition();
		int ln = 0;
		this.selected = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		try {
			ln = textArea.getLineOfOffset(col);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		this.stats.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.LIGHT_GRAY));
		this.stats.setText(String.format(" %s:%d  %s:%d  %s:%d", provider.getString("ln"), ++ln,
				provider.getString("col"), ++col, provider.getString("sel"), selected));

		if (this.selected != 0) {
			this.toUpperCase.setEnabled(true);
			this.toLowerCase.setEnabled(true);
			this.invertCase.setEnabled(true);
		}

		else {
			this.toUpperCase.setEnabled(false);
			this.toLowerCase.setEnabled(false);
			this.invertCase.setEnabled(false);
		}

	}

	/**
	 * Creates toolbars.
	 */
	private void createToolbars() {
		JToolBar bar = new JToolBar(this.provider.getString("tools"));
		bar.setFloatable(true);

		bar.add(new JButton(createBlankDocumentAction));
		bar.add(new JButton(openDocumentAction));
		bar.add(new JButton(saveDocumentAction));
		bar.add(new JButton(saveAsDocumentAction));

		bar.addSeparator();

		bar.add(new JButton(closeDocumentAction));
		bar.add(new JButton(exitAction));

		bar.addSeparator();

		bar.add(new JButton(cutAction));
		bar.add(new JButton(copyAction));
		bar.add(new JButton(pasteAction));

		bar.addSeparator();

		bar.add(new JButton(statisticsAction));

		this.getContentPane().add(bar, BorderLayout.PAGE_START);

		JToolBar statusBar = new JToolBar();
		statusBar.setFloatable(true);
		statusBar.setLayout(new GridLayout(1, 0));

		statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));

		this.time.setHorizontalAlignment(SwingConstants.RIGHT);
		this.stats.setHorizontalAlignment(SwingConstants.LEFT);
		this.length.setHorizontalAlignment(SwingConstants.LEFT);

		statusBar.add(this.length);
		statusBar.add(this.stats);
		statusBar.add(this.time);

		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);
	}

	/**
	 * Creates menus.
	 */
	private void createMenus() {
		JMenuBar menu = new JMenuBar();

		JMenu file = new LocalizableJMenu("file", this.provider);
		menu.add(file);

		file.add(new JMenuItem(createBlankDocumentAction));
		file.add(new JMenuItem(openDocumentAction));
		file.add(new JMenuItem(saveDocumentAction));
		file.add(new JMenuItem(saveAsDocumentAction));
		file.addSeparator();
		file.add(new JMenuItem(closeDocumentAction));
		file.add(new JMenuItem(exitAction));

		JMenu edit = new LocalizableJMenu("edit", this.provider);
		menu.add(edit);

		edit.add(new JMenuItem(cutAction));
		edit.add(new JMenuItem(copyAction));
		edit.add(new JMenuItem(pasteAction));

		JMenu tools = new LocalizableJMenu("tools", this.provider);

		JMenu changeCase = new LocalizableJMenu("case", this.provider);
		JMenu sort = new LocalizableJMenu("sort", this.provider);
		tools.add(changeCase);
		tools.add(sort);
		menu.add(tools);

		JMenu view = new LocalizableJMenu("view", this.provider);
		menu.add(edit);
		view.add(new JMenuItem(statisticsAction));
		menu.add(view);

		this.toUpperCase = new JMenuItem(toUpperCaseAction);
		this.toLowerCase = new JMenuItem(toLowerCaseAction);
		this.invertCase = new JMenuItem(invertCaseAction);
		this.toUpperCase.setEnabled(false);
		this.toLowerCase.setEnabled(false);
		this.invertCase.setEnabled(false);
		changeCase.add(this.toUpperCase);
		changeCase.add(this.toLowerCase);
		changeCase.add(this.invertCase);

		sort.add(new JMenuItem(ascendingAction));
		sort.add(new JMenuItem(descendingAction));

		tools.add(new JMenuItem(uniqueAction));

		JMenu lang = new LocalizableJMenu("languages", this.provider);
		lang.add(new JMenuItem(croatianAction));
		lang.add(new JMenuItem(englishAction));
		lang.add(new JMenuItem(germanAction));
		menu.add(lang);

		this.setJMenuBar(menu);
	}

	/**
	 * Creates actions.
	 */
	private void createActions() {

		createBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));

		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));

		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));

		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F"));

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));

		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));

		statisticsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));

		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));

		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));

		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));

		ascendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt A"));

		descendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt D"));

		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt Q"));

		croatianAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt C"));

		englishAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt E"));

		germanAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt G"));

	}

	/**
	 * Calculates length of selected text.
	 * 
	 * @param editor editor text area
	 * @return length of selected text
	 */
	private int[] calculateSelectedTextLenght(JTextArea editor) {
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = 0;
		if (len != 0)
			offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		else
			return null;
		return new int[] { len, offset };
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});

	}

}
