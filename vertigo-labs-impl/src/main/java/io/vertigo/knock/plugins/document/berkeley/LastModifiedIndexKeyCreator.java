package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.knock.document.model.Document;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryKeyCreator;

final class LastModifiedIndexKeyCreator implements SecondaryKeyCreator {
	private final TupleBinding documentVersionBinding = new DocumentVersionWriter();
	private final TupleBinding documentBinding = new DocumentBinding(true);

	/** {@inheritDoc} */
	public boolean createSecondaryKey(final SecondaryDatabase secondary, final DatabaseEntry key, final DatabaseEntry data, final DatabaseEntry result) throws DatabaseException {
		final Document document = (Document) documentBinding.entryToObject(data);
		documentVersionBinding.objectToEntry(document.getDocumentVersion(), result);
		//System.out.println("updateIndex : " + document.getDocumentVersion().getKey() + " " + document.getDocumentVersion().getLastModified().getTime() + "  Hex:" + result.toString());
		return true;
	}
}
