package phoneticsearch.swingui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import phoneticsearch.Person;
import phoneticsearch.lucene.LuceneIndexPlugin;

public class AutoCompleteUi extends JFrame {

	private static final long serialVersionUID = 3277131006759672639L;
	private final JPanel criteriaPanel = new JPanel();
	private final JTextField inputField = new JTextField(20);
	private final JPanel resultPanel = new JPanel();
	// private final JList<String> resultList = new JList<>();

	private final JCheckBox checkAll = new JCheckBox();
	private final JCheckBox checkName = new JCheckBox();
	private final JCheckBox checkFirstname = new JCheckBox();
	private final JCheckBox checkBirthday = new JCheckBox();
	private final JCheckBox checkAddress = new JCheckBox();
	private final JCheckBox checkZipcode = new JCheckBox();
	private final JCheckBox checkCity = new JCheckBox();
	private final JCheckBox checkPhone = new JCheckBox();
	private final LuceneIndexPlugin indexPlugin;

	// ------------------------------------------------------------------------------
	public AutoCompleteUi(final LuceneIndexPlugin indexPlugin) {
		this.indexPlugin = indexPlugin;
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		//setLayout(new GridLayout(3, 1));
		setVisible(true);

		criteriaPanel.setLayout(new GridLayout(2, 8));
		criteriaPanel.add(new JLabel("Tous"));
		criteriaPanel.add(new JLabel("Nom"));
		criteriaPanel.add(new JLabel("Prenom"));
		criteriaPanel.add(new JLabel("Date de naissance"));
		criteriaPanel.add(new JLabel("Adresse"));
		criteriaPanel.add(new JLabel("Code postal"));
		criteriaPanel.add(new JLabel("Ville"));
		criteriaPanel.add(new JLabel("Téléphone"));
		criteriaPanel.add(checkAll);
		criteriaPanel.add(checkName);
		criteriaPanel.add(checkFirstname);
		criteriaPanel.add(checkBirthday);
		criteriaPanel.add(checkAddress);
		criteriaPanel.add(checkZipcode);
		criteriaPanel.add(checkCity);
		criteriaPanel.add(checkPhone);

		add(inputField);
		//add(criteriaPanel);
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.PAGE_AXIS));

		add(resultPanel);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputField.addKeyListener(new ChangeHandler());
	}

	// ------------------------------------------------------------------------------
	public void initiateSearch(final String lookFor) {
		final List<Person> searchResult = indexPlugin.getCollection(lookFor);
		int index = 0;
		for (final Person person : searchResult) {
			if (person.getScore() < 80) {
				resultPanel.add(new JLabel("Encore " + (searchResult.size() - index) + " résultats"));
				break;
			}
			final JPanel personPanel = createPanel(person);
			resultPanel.add(personPanel);
			index++;
		}
		if (searchResult.size() == 0) {
			resultPanel.add(new JLabel("Aucun resultat pertinent"));
		}
		pack();
		this.repaint();
	}

	private JPanel createPanel(final Person person) {
		final JPanel panel = new JPanel(new GridLayout(4, 1));
		final String birthdayString = person.getBirthday() != null ? new SimpleDateFormat("dd/MM/yyyy").format(person.getBirthday()) : "";
		final String fullName = person.getName() + " " + person.getFirstname() + (birthdayString.isEmpty() ? "" : " (" + birthdayString + ")");
		panel.add(new JLabel("(" + person.getScore() + "%) " + fullName));
		panel.add(new JLabel(person.getAddress()));
		if (!person.getZipcode().isEmpty() || !person.getCity().isEmpty()) {
			panel.add(new JLabel(person.getZipcode() + " " + person.getCity()));
		}
		if (!person.getPhone().isEmpty()) {
			panel.add(new JLabel("Tel: " + person.getPhone()));
		}
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		return panel;
	}

	class ChangeHandler implements KeyListener {
		@Override
		public void keyTyped(final KeyEvent e) {
			//rien
		}

		@Override
		public void keyPressed(final KeyEvent e) {
			//rien
		}

		@Override
		public void keyReleased(final KeyEvent e) {
			clearResult();
			if (inputField.getText().length() >= 3) {
				initiateSearch(inputField.getText());
			}
		}
	}

	// ------------------------------------------------------------------------------

	public final void clearResult() {
		resultPanel.removeAll();
		pack();
		this.repaint();
	}
}
