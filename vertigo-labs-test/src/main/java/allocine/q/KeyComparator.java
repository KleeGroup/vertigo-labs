package allocine.q;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Comparator;

/**
 * Key comparator for DB keys
 */
class KeyComparator implements Comparator<byte[]>, Serializable {
	private static final long serialVersionUID = -8522225840461910601L;

	/**
	 * Compares two DB keys.
	 *
	 * @param key1 first key
	 * @param key2 second key
	 *
	 * @return comparison result
	 */
	@Override
	public int compare(final byte[] key1, final byte[] key2) {
		return new BigInteger(key1).compareTo(new BigInteger(key2));
	}

}
