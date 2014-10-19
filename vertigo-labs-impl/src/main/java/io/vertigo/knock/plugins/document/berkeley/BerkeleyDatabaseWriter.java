package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.knock.document.model.Document;
import io.vertigo.lang.Assertion;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;

/**
 * Objet d'acc�s en �criture � la base Berkeley.
 *
 * @author pchretien
 * @version $Id: BerkeleyDatabaseWriter.java,v 1.5 2011/08/17 12:15:58 npiedeloup Exp $
 */
final class BerkeleyDatabaseWriter {

	//	private final int putCounter = 1;
	private final EntryBinding uuidBinding;
	private final TupleBinding processBinding = new DocumentBinding(false);
	private final BerkeleyDatabase database;

	BerkeleyDatabaseWriter(final BerkeleyDatabase database) {
		Assertion.checkNotNull(database);
		//======================================================================
		this.database = database;
		uuidBinding = TupleBinding.getPrimitiveBinding(String.class);
	}

	/**
	 * Ajout d'un nouvel objet.
	 * @param document Document � inserer
	 */
	void put(final Document document) {
		// DatabaseEntries used for loading records
		final DatabaseEntry theKey = new DatabaseEntry();
		final DatabaseEntry theData = new DatabaseEntry();

		//final UUID key = UUID.randomUUID();
		final String key = document.getDocumentVersion().getKey();
		uuidBinding.objectToEntry(key.toString(), theKey);
		processBinding.objectToEntry(document, theData);
		database.put(theKey, theData);
		//tout les 50 put, on rouvre la BDD
		/*synchronized (this) {
			putCounter++;
			if (putCounter >= 50) {
				database.close();
				database.open(true);
				putCounter = 0;
			}
		}*/
	}
}
