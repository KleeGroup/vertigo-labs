package domain;

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
						domain.document.DocumentCriteria.class, //
						domain.document.DocumentIndexed.class, //
						domain.document.DocumentResult.class, //
				}).iterator();
	}
}
