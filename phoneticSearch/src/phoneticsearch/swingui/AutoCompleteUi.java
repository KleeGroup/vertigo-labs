package phoneticsearch.swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import phoneticsearch.MessageOutput;
import phoneticsearch.Person;
import phoneticsearch.SearchHandler;

public class AutoCompleteUi extends JFrame implements MessageOutput {

	private static final long serialVersionUID = 3277131006759672639L;
	//private final JPanel criteriaPanel = new JPanel();
	private final JTextField inputField = new JTextField(20);
	private final JPanel resultPanel;

	private final ImageIcon maleIcon;
	private final ImageIcon femaleIcon;
	private final ImageIcon unknownIcon;
	private final Image background;
	// private final JList<String> resultList = new JList<>();

	private final SearchHandler searchHandler;

	// ------------------------------------------------------------------------------
	public AutoCompleteUi(final SearchHandler searchHandler) throws IOException {
		this.searchHandler = searchHandler;
		setSize(413 + 5, 808 + 28);

		//UIManager.put("ProgressBar.background", Color.LIGHT_GRAY);
		UIManager.put("ProgressBar.foreground", new Color(170, 221, 0));
		UIManager.put("Panel.background", Color.WHITE);

		maleIcon = loadImageIcon("phoneticsearch/swingui/male-icon.png");
		femaleIcon = loadImageIcon("phoneticsearch/swingui/female-icon.png");
		unknownIcon = loadImageIcon("phoneticsearch/swingui/klee-icon.png");
		background = loadImage("phoneticsearch/swingui/androidBackground.png");
		final JPanel backgroundPanel = new JPanelWithBackground(background);
		backgroundPanel.setBorder(new EmptyBorder(126, 32, 131, 32));
		backgroundPanel.setLayout(new BorderLayout());
		setContentPane(backgroundPanel);

		final JPanel inputContainer = new JPanel();
		inputContainer.add(inputField);
		resultPanel = new JPanel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));
		final JPanel container = new JPanel(new BorderLayout());
		container.add(resultPanel, BorderLayout.NORTH);

		add(inputContainer, BorderLayout.NORTH);
		final JScrollPane jScrollPane = new JScrollPane(container);
		jScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		jScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
		jScrollPane.setBorder(null);
		add(jScrollPane, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputField.addKeyListener(new ChangeHandler());
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = screenSize.width / 3 - getWidth() / 2;
		final int y = screenSize.height / 2 - getHeight() / 2;
		setBounds(x, y, getWidth(), getHeight());
		setResizable(false);

		inputContainer.setBackground(Color.GRAY);
		inputContainer.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		jScrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

		setVisible(true);
	}

	private Image loadImage(final String imagePath) throws IOException {
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(imagePath)) {
			final BufferedImage image = ImageIO.read(is);
			return image;
		}
	}

	private ImageIcon loadImageIcon(final String imagePath) throws IOException {
		return new ImageIcon(loadImage(imagePath));
	}

	// ------------------------------------------------------------------------------
	public void initiateSearch(final String lookFor) {
		final List<Person> searchResult = searchHandler.search(lookFor);
		int index = 0;
		for (final Person person : searchResult) {
			if (resultPanel.getComponentCount() > 3 && person.getScore() < 80 || person.getScore() < 30) {
				resultPanel.add(new JLabel("Encore " + (searchResult.size() - index) + " résultats"));
				break;
			}
			final JPanel personPanel = createPanel(person);
			//personPanel.setSize(0, 0);
			resultPanel.add(personPanel);
			index++;
		}
		if (searchResult.size() == 0) {
			resultPanel.add(new JLabel("Aucun resultat pertinent"));
		}
		//resultPanel.pack();
		revalidate();
		this.repaint();
	}

	/** {@inheritDoc} */
	public void displayMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//clearResult();
				resultPanel.add(new JLabel(message));
				revalidate();
				repaint();
			}
		});
	}

	private JPanel createPanel(final Person person) {

		final JPanel iconedpanel = new JPanel(new GridBagLayout());
		final JLabel iconLabel = new JLabel("F".equals(person.getSexe()) ? femaleIcon : "M".equals(person.getSexe()) ? maleIcon : unknownIcon);
		iconLabel.setSize(35, 35);

		final String birthdayString = person.getBirthday() != null ? new SimpleDateFormat("dd/MM/yyyy").format(person.getBirthday()) : "";
		final String fullName = person.getName() + " " + person.getFirstname() + (birthdayString.isEmpty() ? "" : " (" + birthdayString + ")");
		final JLabel fullNameLabel = new JLabel(fullName);

		final JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(person.getScore());
		progressBar.setPreferredSize(new Dimension(30, 10));
		iconedpanel.add(progressBar, new GridBagConstraints(1, 0, //gridx, gridy
				1, 1, //gridwidth, gridheight
				0d, 0d, //weightx, weighty
				GridBagConstraints.LINE_START, //anchor 
				GridBagConstraints.NONE, //fill
				new Insets(0, 0, 0, 0), //insets
				0, 0 //ipadx, ipady
				));

		iconedpanel.add(fullNameLabel, new GridBagConstraints(2, 0, //gridx, gridy
				1, 1, //gridwidth, gridheight
				1d, 0d, //weightx, weighty
				GridBagConstraints.LINE_START, //anchor 
				GridBagConstraints.HORIZONTAL, //fill
				new Insets(0, 5, 0, 0), //insets
				0, 0 //ipadx, ipady
				));

		final Font miniFont = new Font(fullNameLabel.getFont().getName(), Font.PLAIN, fullNameLabel.getFont().getSize() - 2);
		int gridy = 1;
		JLabel jlabel;
		if (!person.getAddress().isEmpty()) {
			jlabel = new JLabel(person.getAddress());
			jlabel.setFont(miniFont);
			iconedpanel.add(jlabel, new GridBagConstraints(1, gridy++, //gridx, gridy
					2, 1, //gridwidth, gridheight
					1d, 0d, //weightx, weighty
					GridBagConstraints.LINE_START, //anchor
					GridBagConstraints.HORIZONTAL, //fill
					new Insets(0, 0, 0, 0), //insets
					0, 0 //ipadx, ipady
					));
		}
		if (!person.getZipcode().isEmpty() || !person.getCity().isEmpty()) {
			jlabel = new JLabel(person.getZipcode() + " " + person.getCity());
			jlabel.setFont(miniFont);
			iconedpanel.add(jlabel, new GridBagConstraints(1, gridy++, //gridx, gridy
					2, 1, //gridwidth, gridheight
					1d, 0d, //weightx, weighty
					GridBagConstraints.LINE_START, //anchor 
					GridBagConstraints.HORIZONTAL, //fill
					new Insets(0, 0, 0, 0), //insets
					0, 0 //ipadx, ipady
					));
		}
		if (!person.getPhone().isEmpty()) {
			jlabel = new JLabel("Tel: " + person.getPhone());
			jlabel.setFont(miniFont);
			iconedpanel.add(jlabel, new GridBagConstraints(1, gridy++, //gridx, gridy
					2, 1, //gridwidth, gridheight
					1d, 0d, //weightx, weighty
					GridBagConstraints.LINE_START, //anchor 
					GridBagConstraints.HORIZONTAL, //fill
					new Insets(0, 0, 0, 0), //insets
					0, 0 //ipadx, ipady
					));
		}
		iconedpanel.add(iconLabel, new GridBagConstraints(0, 0, //gridx, gridy
				1, gridy, //gridwidth, gridheight
				0d, 0d, //weightx, weighty
				GridBagConstraints.CENTER, //anchor 
				GridBagConstraints.BOTH, //fill
				new Insets(0, 0, 0, 5), //insets
				0, 0 //ipadx, ipady
				));

		iconedpanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		return iconedpanel;
	}

	class ChangeHandler implements KeyListener {
		@Override
		public void keyTyped(final KeyEvent e) {
			// rien
		}

		@Override
		public void keyPressed(final KeyEvent e) {
			// rien
		}

		private String previous;

		@Override
		public void keyReleased(final KeyEvent e) {
			final String input = inputField.getText().trim();
			if (!input.equals(previous)) {
				previous = input;
				clearResult();
				if (input.length() >= 3) {
					final Thread queryThread = new Thread() {
						@Override
						public void run() {
							initiateSearch(input);
						}
					};
					queryThread.start();
				}
			}
		}
	}

	// ------------------------------------------------------------------------------

	public final void clearResult() {
		resultPanel.removeAll();
		//pack();
		this.repaint();
	}
}
