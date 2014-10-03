package phoneticsearch.swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import phoneticsearch.Person;
import phoneticsearch.SearchHandler;

public class AutoCompleteUi extends JFrame {

	private static final long serialVersionUID = 3277131006759672639L;
	//private final JPanel criteriaPanel = new JPanel();
	private final JTextField inputField = new JTextField(20);
	private final JPanel resultPanel = new JPanel();

	private final ImageIcon maleIcon;
	private final ImageIcon femaleIcon;
	private final ImageIcon unknownIcon;
	private final Image background;
	// private final JList<String> resultList = new JList<>();

	private final SearchHandler searchHandler;

	// ------------------------------------------------------------------------------
	public AutoCompleteUi(final SearchHandler searchHandler) throws IOException {
		this.searchHandler = searchHandler;

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

		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));
		final JPanel container = new JPanel(new BorderLayout());
		container.add(resultPanel, BorderLayout.NORTH);

		add(inputContainer, BorderLayout.NORTH);
		final JScrollPane jScrollPane = new JScrollPane(container);
		jScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
		jScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
		jScrollPane.setBorder(null);
		add(jScrollPane, BorderLayout.CENTER);

		setSize(413 + 15, 808 + 38);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputField.addKeyListener(new ChangeHandler());
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = screenSize.width / 3 - getWidth() / 2;
		final int y = screenSize.height / 2 - getHeight() / 2;
		setBounds(x, y, getWidth(), getHeight());

		inputContainer.setBackground(Color.GRAY);
		inputField.setBackground(Color.WHITE);
		resultPanel.setBackground(Color.WHITE);
		container.setBackground(Color.WHITE);
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

	private JPanel createPanel(final Person person) {
		final JPanel iconedpanel = new JPanel();
		iconedpanel.setBackground(Color.WHITE);

		iconedpanel.setLayout(new BorderLayout());
		final JLabel iconLabel = new JLabel("F".equals(person.getSexe()) ? femaleIcon : "M".equals(person.getSexe()) ? maleIcon : unknownIcon);
		iconLabel.setSize(32, 32);
		iconedpanel.add(iconLabel, BorderLayout.WEST);
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		final String birthdayString = person.getBirthday() != null ? new SimpleDateFormat("dd/MM/yyyy").format(person.getBirthday()) : "";
		final String fullName = person.getName() + " " + person.getFirstname() + (birthdayString.isEmpty() ? "" : " (" + birthdayString + ")");
		panel.add(new JLabel("(" + person.getScore() + "%) " + fullName));
		if (!person.getAddress().isEmpty()) {
			panel.add(new JLabel(person.getAddress()));
		}
		if (!person.getZipcode().isEmpty() || !person.getCity().isEmpty()) {
			panel.add(new JLabel(person.getZipcode() + " " + person.getCity()));
		}
		if (!person.getPhone().isEmpty()) {
			panel.add(new JLabel("Tel: " + person.getPhone()));
		}
		panel.setBackground(Color.WHITE);
		panel.setBorder(new EmptyBorder(0, 5, 0, 0));

		iconedpanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		iconedpanel.add(panel, BorderLayout.CENTER);
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
			if (!inputField.getText().equals(previous)) {
				previous = inputField.getText();
				clearResult();
				if (inputField.getText().length() >= 3) {
					initiateSearch(inputField.getText());
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
