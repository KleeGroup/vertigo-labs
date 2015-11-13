package allocine.q;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;

public class Queue {
	/**
	 * Berkley DB environment
	 */
	private final Environment dbEnv;

	/**
	 * Berkley DB instance for the queue
	 */
	private final Database queueDatabase;

	/**
	 * Queue cache size - number of element operations it is allowed to loose in case of system crash.
	 */
	private final int cacheSize;

	/**
	 * This queue name.
	 */
	private final String queueName;

	/**
	 * Queue operation counter, which is used to sync the queue database to disk periodically.
	 */
	private int opsCounter;

	/**
	 * Creates instance of persistent queue.
	 *
	 * @param queueEnvPath   queue database environment directory path
	 * @param queueName      descriptive queue name
	 * @param cacheSize      how often to sync the queue to disk
	 */
	public Queue(final String queueEnvPath,
			final String queueName,
			final int cacheSize) {
		// Create parent dirs for queue environment directory
		new File(queueEnvPath).mkdirs();

		// Setup database environment
		final EnvironmentConfig dbEnvConfig = new EnvironmentConfig()
				.setTransactional(false)
				.setAllowCreate(true);

		dbEnv = new Environment(new File(queueEnvPath), dbEnvConfig);

		// Setup non-transactional deferred-write queue database
		final DatabaseConfig dbConfig = new DatabaseConfig()
				.setTransactional(false)
				.setAllowCreate(true)
				//.setDeferredWrite(true)
				.setBtreeComparator(new KeyComparator());
		queueDatabase = dbEnv.openDatabase(null, queueName, dbConfig);
		this.queueName = queueName;
		this.cacheSize = cacheSize;
		opsCounter = 0;
	}

	/**
	 * Retrieves and returns element from the head of this queue.
	 *
	 * @return element from the head of the queue or null if queue is empty
	 *
	 * @throws IOException in case of disk IO failure
	 */
	public String poll() throws IOException {
		final DatabaseEntry key = new DatabaseEntry();
		final DatabaseEntry data = new DatabaseEntry();
		try (Cursor cursor = queueDatabase.openCursor(null, null)) {
			cursor.getFirst(key, data, LockMode.RMW);
			if (data.getData() == null) {
				return null;
			}
			final String res = new String(data.getData(), "UTF-8");
			cursor.delete();
			opsCounter++;
			if (opsCounter >= cacheSize) {
				queueDatabase.sync();
				opsCounter = 0;
			}
			return res;
		}
	}

	/**
	 * Pushes element to the tail of this queue.
	 *
	 * @param element element
	 *
	 * @throws IOException in case of disk IO failure
	 */
	public synchronized void push(final String element) throws IOException {
		final DatabaseEntry key = new DatabaseEntry();
		final DatabaseEntry data = new DatabaseEntry();
		try (final Cursor cursor = queueDatabase.openCursor(null, null)) {
			cursor.getLast(key, data, LockMode.RMW);

			final BigInteger prevKeyValue;
			if (key.getData() == null) {
				prevKeyValue = BigInteger.valueOf(-1);
			} else {
				prevKeyValue = new BigInteger(key.getData());
			}
			final BigInteger newKeyValue = prevKeyValue.add(BigInteger.ONE);

			final DatabaseEntry newKey = new DatabaseEntry(newKeyValue.toByteArray());
			final DatabaseEntry newData = new DatabaseEntry(element.getBytes("UTF-8"));
			queueDatabase.put(null, newKey, newData);

			opsCounter++;
			if (opsCounter >= cacheSize) {
				queueDatabase.sync();
				opsCounter = 0;
			}
		}
	}

	/**
	  * Returns the size of this queue.
	  *
	  * @return the size of the queue
	  */
	public long size() {
		return queueDatabase.count();
	}

	/**
	 * Returns this queue name.
	 *
	 * @return this queue name
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * Closes this queue and frees up all resources associated to it.
	 */
	public void close() {
		queueDatabase.close();
		dbEnv.close();
	}
}
