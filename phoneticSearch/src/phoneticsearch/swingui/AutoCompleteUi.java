package phoneticsearch.swingui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
	// private final JList<String> resultList = new JList<>();

	private final SearchHandler searchHandler;

	// ------------------------------------------------------------------------------
	public AutoCompleteUi(final SearchHandler searchHandler) throws IOException {
		this.searchHandler = searchHandler;

		maleIcon = loadImageIcon("phoneticsearch/swingui/male-icon.png");
		femaleIcon = loadImageIcon("phoneticsearch/swingui/female-icon.png");
		unknownIcon = loadImageIcon("phoneticsearch/swingui/klee-icon.png");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout());

		// setLayout(new GridLayout(3, 1));

		final JPanel inputContainer = new JPanel();
		inputContainer.add(inputField);

		add(inputContainer, BorderLayout.NORTH);
		// add(criteriaPanel);
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));
		resultPanel.setBackground(Color.WHITE);
		final JPanel container = new JPanel(new BorderLayout());
		container.setBackground(Color.WHITE);
		container.add(resultPanel, BorderLayout.NORTH);
		add(new JScrollPane(container), BorderLayout.CENTER);
		setSize(300, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputField.addKeyListener(new ChangeHandler());
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int x = screenSize.width / 4; // - getWidth() /2
		final int y = screenSize.height / 4;// - getHeight() / 2;
		setBounds(x, y, getWidth(), getHeight());
		setVisible(true);
	}

	private ImageIcon loadImageIcon(final String imagePath) throws IOException {
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(imagePath)) {
			final BufferedImage image = ImageIO.read(is);
			return new ImageIcon(image);
		}
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
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		panel.setBackground(Color.WHITE);

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
