package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.core.lang.Assertion;
import io.vertigo.knock.document.model.Document;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;

/**
 * Objet d'acc�s en lecture � la base Berkeley.
 *
 * @author npiedeloup
 * @version $Id: BerkeleyDatabaseCursor.java,v 1.1 2011/08/17 12:15:58 npiedeloup Exp $
 */
final class BerkeleyDatabaseCursor {
	private final EntryBinding uuidBinding = TupleBinding.getPrimitiveBinding(String.class);
	private final TupleBinding processBinding = new DocumentBinding(false);
	private final BerkeleyDatabase database;
	private final DatabaseEntry lastKey = new DatabaseEntry();

	/**
	 * Constructeur.
	 * @param database Base � parcourir
	 */
	BerkeleyDatabaseCursor(final BerkeleyDatabase database) {
		Assertion.checkNotNull(database);
		//======================================================================
		this.database = database;
	}

	/**
	 * Lecture d'un objet de la base.
	 * @return Document suivant ou null s'il n'y en a pas de nouveau.
	 */
	Document next() {
		// DatabaseEntries used for loading records
		final DatabaseEntry theData = database.next(lastKey);
		if (theData == null) {
			//pas de suivant
			return null;
		}
		final Document document = (Document) processBinding.entryToObject(theData);
		//on garde la derni�re Url
		uuidBinding.objectToEntry(document.getDocumentVersion().getKey(), lastKey);
		if (document.getDocumentVersion().getUrl().contains("Clients -  reseau")) {
			System.out.println("FOUND : " + document.getDocumentVersion().getUrl() + "(" + document.getSize() / 1024 + "Ko)");
		}
		//System.out.println("BerkeleyDatabaseReader : " + document.getDocumentVersion().getUrl() + "(" + document.getSize() / 1024 + "Ko)");
		return document;
	}
}
