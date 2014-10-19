package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.knock.document.model.DocumentVersion;
import io.vertigo.lang.Assertion;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;

/**
 * Objet d'acc�s en lecture � la base Berkeley.
 *
 * @author npiedeloup
 * @version $Id: BerkeleyDatabaseReader.java,v 1.6 2011/10/07 13:14:34 npiedeloup Exp $
 */
final class BerkeleyDatabaseReader {
	private final TupleBinding documentVersionBinding = new DocumentVersionWriter();
	private final BerkeleyDatabase database;

	BerkeleyDatabaseReader(final BerkeleyDatabase database) {
		Assertion.checkNotNull(database);
		//======================================================================
		this.database = database;
	}

	private final EntryBinding uuidBinding = TupleBinding.getPrimitiveBinding(String.class);

	/**
	 * @param documentVersion DocumentVersion � tester
	 * @return Si la base contient d�j� un document avec cette version (key+lastModified).
	 */
	boolean contains(final DocumentVersion documentVersion) {
		final DatabaseEntry versionKey = new DatabaseEntry();
		documentVersionBinding.objectToEntry(documentVersion, versionKey);
		//System.out.println("lookForIndex : " + documentVersion.getKey() + " " + documentVersion.getLastModified().getTime() + "  Hex:" + versionKey.toString());
		final DatabaseEntry theData = database.getByIndex(versionKey, BerkeleyDatabase.Indexes.lastModified);
		if (theData == null) {
			//System.out.println("NOT_FOUND : " + documentVersion.getKey() + " " + documentVersion.getLastModified().getTime());
			final DatabaseEntry theKey = new DatabaseEntry();
			final String key = documentVersion.getKey();
			uuidBinding.objectToEntry(key.toString(), theKey);
			//final DatabaseEntry theData2 = database.get(theKey);
			//System.out.println("OTHER_TIME_FOUND : " + theData2);
		}
		return theData != null;
	}
}
