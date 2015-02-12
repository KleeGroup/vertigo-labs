package zzzz;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Attention cette classe est g�n�r�e automatiquement !
 */
public final class DtDefinitions implements Iterable<Class<?>> {
	@Override
	public Iterator<Class<?>> iterator() {
		return Arrays.asList(new Class<?>[] { //
				fr.klee.knock.domain.KxFileInfo.class, //
						DocumentCriteria.class, //
						DocumentIndexed.class, //
						DocumentResult.class, //
				}).iterator();
	}
}
