package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.knock.document.model.Document;
import io.vertigo.knock.document.model.DocumentVersion;
import io.vertigo.knock.impl.document.DocumentStorePlugin;
import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation du Store de Document sur une BerkeleyDb.
 * @author npiedeloup
 * @version $Id: BerkeleyDocumentStorePlugin.java,v 1.8 2011/08/30 12:06:19 pchretien Exp $
 */
public final class BerkeleyDocumentStorePlugin implements DocumentStorePlugin, Activeable {

	private final BerkeleyDatabase database;
	private final BerkeleyDatabaseWriter writer;
	private final BerkeleyDatabaseReader reader;

	/**
	 * Constructeur.
	 * @param dbPath Chemin vers le r�pertoire de stockage de la base de donn�es.
	 */
	@Inject
	public BerkeleyDocumentStorePlugin(@Named("dbPath") final String dbPath) {
		Assertion.checkArgNotEmpty(dbPath);
		final File dbDirectory = new File(dbPath);
		Assertion.checkArgument(dbDirectory.exists(), "Le r�pertoire de stockage n'existe pas : {0}", dbPath);
		Assertion.checkArgument(dbDirectory.isDirectory(), "Le r�pertoire de stockage n'est pas un r�pertoire : {0}", dbPath);
		//---------------------------------------------------------------------
		database = new BerkeleyDatabase(dbDirectory);
		writer = new BerkeleyDatabaseWriter(database);
		reader = new BerkeleyDatabaseReader(database);
	}

	/** {@inheritDoc} */
	public void add(final Document document) {
		writer.put(document);
	}

	/** {@inheritDoc} */
	public Iterator<Document> iterator() {
		return new DocumentLoader(new BerkeleyDatabaseCursor(database)/*, timeout*/);
	}

	/** {@inheritDoc} */
	public boolean contains(final DocumentVersion documentVersion) {
		return reader.contains(documentVersion);
	}

	/** {@inheritDoc} */
	public long size() {
		return database.count();
	}

	/** {@inheritDoc} */
	public void start() {
		database.open(false);
	}

	/** {@inheritDoc} */
	public void stop() {
		database.close();
	}

	private static final class DocumentLoader implements Iterator<Document> {
		private final BerkeleyDatabaseCursor cursor;
		private Document current;
		private Document next;

		/**
		 * Constructeur.
		 * @param cursor Lecteur de donn�es
		 */
		public DocumentLoader(final BerkeleyDatabaseCursor cursor) {
			Assertion.checkNotNull(cursor);
			//-----------------------------------------------------------------
			this.cursor = cursor;
		}

		/** {@inheritDoc} */
		public boolean hasNext() {
			if (next == null) {
				next = cursor.next();
			}
			return next != null;
		}

		/** {@inheritDoc} */
		public Document next() {
			if (next == null) {
				throw new NoSuchElementException("Liste vide");
			}
			current = next;
			next = null;
			return current;
		}

		/** {@inheritDoc} */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
