package io.vertigo.knock.plugins.document.berkeley;

import io.vertigo.core.lang.Assertion;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.SecondaryConfig;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.Transaction;

final class BerkeleyDatabase {
	private final File myDbEnvPath;
	private Environment myEnv;
	private Database db;
	private static final boolean USE_INDEXES = true;
	private final Map<Indexes, SecondaryDatabase> indexMap = new HashMap<>();

	public enum Indexes {
		lastModified;
	}

	/**
	 * Constructeur.
	 */
	BerkeleyDatabase(final File myDbEnvPath) {
		Assertion.checkNotNull(myDbEnvPath);
		//----------------------------------------------------------------------
		this.myDbEnvPath = myDbEnvPath;
	}

	long count() {
		try {
			return db.count();
		} catch (final DatabaseException e) {
			throw new RuntimeException(e);
		}
	}

	void put(final DatabaseEntry key, final DatabaseEntry data) {
		try {
			final Transaction transaction = createTransaction();
			boolean committed = false;
			try {
				final OperationStatus status = db.put(transaction, key, data);
				if (!OperationStatus.SUCCESS.equals(status)) {
					throw new RuntimeException("la sauvegarde a �chou�e");
				}
				transaction.commit();
				committed = true;
			} finally {
				if (!committed) {
					transaction.abort();
				}
			}
		} catch (final DatabaseException e) {
			throw new RuntimeException(e);
		}
	}

	//	private Cursor openCursor() {
	//		try {
	//			System.out.println(">>> Open cursor : " + db.count() + " �lements");
	//			return db.openCursor(null, null); //pas de TX=readOnly et cursorConfig default
	//		} catch (final DatabaseException e) {
	//			throw new KRuntimeException(e);
	//		}
	//	}
	//
	//	private void closeCursor(final Cursor cursor) {
	//		try {
	//			cursor.close();
	//		} catch (final DatabaseException e) {
	//			throw new KRuntimeException(e);
	//		}
	//	}

	DatabaseEntry next(final DatabaseEntry lastKey) {
		try {
			// Open a cursor using a database handle
			final Cursor cursor = db.openCursor(null, null); //pas de TX=readOnly et cursorConfig default
			try {
				final DatabaseEntry theKey;
				final DatabaseEntry theData = new DatabaseEntry();

				if (lastKey.getSize() == 0) { //si lastKey est null, on veut le premier �l�ment
					theKey = new DatabaseEntry();
				} else {
					//Si on a dej� un lastKey, on repositionne le cursor dessus, puis on fait next()
					theKey = new DatabaseEntry(lastKey.getData());
					final OperationStatus status = cursor.getSearchKey(theKey, theData, null);
					Assertion.checkState(OperationStatus.SUCCESS.equals(status), "L'ancien document n'a pas �t� retrouv� : {0}", lastKey);
				}
				final OperationStatus status = cursor.getNext(theKey, theData, null);
				if (OperationStatus.SUCCESS.equals(status)) {
					return theData;
				}
				return null; //pas de suivant
			} finally {
				cursor.close();
			}
		} catch (final DatabaseException e) {
			throw new RuntimeException(e);
		}
	}

	private Transaction createTransaction() throws DatabaseException {
		return db.getEnvironment().beginTransaction(null, null);
	}

	DatabaseEntry get(final DatabaseEntry theKey) {
		try {
			final DatabaseEntry theData = new DatabaseEntry();
			final OperationStatus status = db.get(null, theKey, theData, null); //pas de TX=readOnly et cursorConfig default
			if (OperationStatus.SUCCESS.equals(status)) {
				return theData;
			}
			return null; //pas trouv�
		} catch (final DatabaseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param indexKey Cl� de l'index
	 * @param index Index � utiliser
	 * @return Donn�es de la dataBase r�cup�rer par l'index
	 */
	DatabaseEntry getByIndex(final DatabaseEntry indexKey, final Indexes index) {
		if (USE_INDEXES) {
			try {
				final DatabaseEntry theData = new DatabaseEntry();
				final OperationStatus status = indexMap.get(index).get(null, indexKey, theData, null); //pas de TX=readOnly et cursorConfig default
				if (OperationStatus.SUCCESS.equals(status)) {
					return theData;
				}
			} catch (final DatabaseException e) {
				throw new RuntimeException(e);
			}
		}
		//Pas trouv�
		return null;
	}

	void open(final boolean readOnly) {
		try {
			final EnvironmentConfig myEnvConfig = new EnvironmentConfig();
			final DatabaseConfig myDbConfig = new DatabaseConfig();

			// If the environment is read-only, then
			// make the databases read-only too.
			myEnvConfig.setReadOnly(readOnly);
			myDbConfig.setReadOnly(readOnly);

			// If the environment is opened for write, then we want to be
			// able to create the environment and databases if
			// they do not exist.
			myEnvConfig.setAllowCreate(!readOnly);
			myDbConfig.setAllowCreate(!readOnly);

			// Allow transactions if we are writing to the database
			myEnvConfig.setTransactional(!readOnly);
			myDbConfig.setTransactional(!readOnly);

			//On limite l'utilisation du cache � 20% de la m�moire globale.
			myEnvConfig.setCachePercent(20);
			//CHECKME On limite l'utilisation du cache � 200Mo
			//myEnvConfig.setCacheSize(200 * 1000 * 1000);

			// Open the environment
			myEnv = new Environment(myDbEnvPath, myEnvConfig);

			// Now open, or create and open, our databases
			// Open the vendors and inventory databases
			db = myEnv.openDatabase(null, "MyDB", myDbConfig);

			//On cr�e l'index sur le lastModified
			if (USE_INDEXES) {
				final SecondaryConfig lastModifiedIndexConfig = new SecondaryConfig();
				lastModifiedIndexConfig.setReadOnly(readOnly);
				lastModifiedIndexConfig.setAllowCreate(!readOnly);
				lastModifiedIndexConfig.setTransactional(!readOnly);
				lastModifiedIndexConfig.setAllowPopulate(!readOnly);//auto remplissage de la base si vide
				lastModifiedIndexConfig.setSortedDuplicates(true);
				lastModifiedIndexConfig.setKeyCreator(new LastModifiedIndexKeyCreator());
				final SecondaryDatabase lastModifiedIndex = myEnv.openSecondaryDatabase(null, "lastModifiedIndex", db, lastModifiedIndexConfig);
				indexMap.put(Indexes.lastModified, lastModifiedIndex);
			}
		} catch (final DatabaseException e) {
			throw new RuntimeException(e);
		}
	}

	void close() {
		if (myEnv != null) {
			try {
				db.close();
				for (final SecondaryDatabase index : indexMap.values()) {
					index.close();
				}
				// Finally, close the environment.
				myEnv.close();
			} catch (final DatabaseException dbe) {
				System.err.println("Error closing MyDbEnv: " + dbe.toString());
				System.exit(-1);
			}
		}
	}
}
