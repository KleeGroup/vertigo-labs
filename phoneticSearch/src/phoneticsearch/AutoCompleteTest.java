package phoneticsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import phoneticsearch.lucene.LuceneIndexPlugin;
import phoneticsearch.lucene.LuceneIndexPlugin.IndexType;
import phoneticsearch.swingui.AutoCompleteUi;
import au.com.bytecode.opencsv.CSVReader;

public class AutoCompleteTest {

	//private static final String DATAS_PATH = "phoneticsearch/datas/actors.list;phoneticsearch/datas/actresses.list";
	private static final String ACTORS_DATAS_PATH = "phoneticsearch/datas/actors.csv";
	private static final String KLEE_DATAS_PATH = "phoneticsearch/datas/klee.csv";

	private static final int NAME_COL = 0;
	private static final int FIRSTNAME_COL = 1;
	private static final int SEXE_COL = 2;
	private static final int BIRTHDAY_COL = 3;
	private static final int ADDRESS_COL = 4;
	private static final int ZIPCODE_COL = 5;
	private static final int CITY_COL = 6;
	private static final int PHONE_COL = 7;

	private AutoCompleteUi autoCompleteUi;

	private final List<Person> datas = new ArrayList<>();
	private final LuceneIndexPlugin indexPlugin = new LuceneIndexPlugin(IndexType.PHONETIC);

	// ------------------------------------------------------------------------------
	public static void main(final String[] args) throws IOException, ParseException {
		new AutoCompleteTest().init();
	}

	private void loadDatas(final String datasPaths, final MessageOutput out) throws IOException, ParseException {
		for (final String datasPath : datasPaths.split(";")) {
			try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(datasPath)) {
				final Reader reader = new InputStreamReader(is);
				if (datasPath.endsWith(".csv")) {
					readCsv(reader, out);
				} else if (datasPath.endsWith(".list")) { //imdb
					readImdbList(reader, out);
				}
			}
		}
	}

	private void readCsv(final Reader reader, final MessageOutput out) throws IOException, ParseException {
		try (final CSVReader csvReader = new CSVReader(reader, ';')) {
			String[] row;
			while ((row = csvReader.readNext()) != null) {
				for (int i = 0; i < row.length; i++) {
					row[i] = row[i].trim();
				}
				final Person newPerson;
				if (row.length > 3) {
					final Date birthday = row[BIRTHDAY_COL].isEmpty() ? null : new SimpleDateFormat("dd/MM/yyyy").parse(row[BIRTHDAY_COL]);
					newPerson = new Person(row[NAME_COL].toUpperCase(), row[FIRSTNAME_COL], row[SEXE_COL], birthday, row[ADDRESS_COL], row[ZIPCODE_COL], row[CITY_COL], row[PHONE_COL]);
				} else {
					newPerson = new Person(row[NAME_COL].toUpperCase(), row[FIRSTNAME_COL], row[SEXE_COL], null, "", "", "", "");
				}
				datas.add(newPerson);
				if (datas.size() % 5000 == 0) {
					out.displayMessage("load " + datas.size());
				}
			}
			out.displayMessage("load " + datas.size() + " done");
		}
	}

	private void readImdbList(final Reader reader, final MessageOutput out) throws IOException {
		final BufferedReader br = new BufferedReader(reader);
		String strLine;
		//Read File Line By Line
		Person newPerson = null;
		int nbMovies = 0;
		while ((strLine = br.readLine()) != null) {
			if (strLine.indexOf("(20") > 10) {
				if (strLine.indexOf('\t') > 0) {
					final String fullName = strLine.substring(0, strLine.indexOf('\t'));
					final int commaIndex = fullName.indexOf(',');
					if (commaIndex > 0 && fullName.indexOf('\'') == -1 && fullName.indexOf('(') == -1) {
						if (nbMovies > 40 && newPerson != null) {
							if (datas.size() % 5000 == 0) {
								out.displayMessage("load " + datas.size());
							}
							datas.add(newPerson);
							//System.out.println(newPerson.getName() + ";" + newPerson.getFirstname() + ";");
						}

						newPerson = new Person(fullName.substring(0, commaIndex).toUpperCase(), fullName.substring(commaIndex + 1, fullName.length()), null, null, "", "", "", "");
						nbMovies = 0;

					} else {
						//on en a assez
						//newPerson = new Person(fullName, "", null, "", "", "", "");
					}
				}
				nbMovies++;
			}
		}
		out.displayMessage("load " + datas.size() + " done");
	}

	// ------------------------------------------------------------------------------
	public AutoCompleteTest() {
		//rien
	}

	public void init() throws IOException, ParseException {
		autoCompleteUi = new AutoCompleteUi(new SearchHandler() {
			public List<Person> search(final String lookFor) {
				if (lookFor.startsWith("index:")) {
					try {
						if ("index:actors".equals(lookFor)) {
							loadDatas(ACTORS_DATAS_PATH, autoCompleteUi);
							indexPlugin.indexDatas(datas, autoCompleteUi);
						} else if ("index:klee".equals(lookFor)) {
							loadDatas(KLEE_DATAS_PATH, autoCompleteUi);
							indexPlugin.indexDatas(datas, autoCompleteUi);
						} else if ("index:clear".equals(lookFor)) {
							datas.clear();
							indexPlugin.indexDatas(datas, autoCompleteUi);
						} else {
							autoCompleteUi.displayMessage(lookFor + " : commande inconnue");
							return Collections.emptyList();
						}
						return Collections.emptyList();
					} catch (IOException | ParseException e) {
						datas.clear();
						autoCompleteUi.displayMessage(lookFor + " : erreur : " + e.getMessage());
						return Collections.emptyList();
					}
				}

				return indexPlugin.getCollection(lookFor);
			}
		});
		loadDatas(KLEE_DATAS_PATH, autoCompleteUi);
		indexPlugin.indexDatas(datas, autoCompleteUi);
		datas.clear();
		autoCompleteUi.displayMessage("Recherche phonétique prète : taper un nom");
	}
}
