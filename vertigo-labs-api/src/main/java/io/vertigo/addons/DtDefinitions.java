package io.vertigo.addons;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author pchretien
 */
public final class DtDefinitions implements Iterable<Class<?>> {
	@Override
	public Iterator<Class<?>> iterator() {
		return Arrays.asList(new Class<?>[] {
				Movie.class
		}).iterator();
	}
}
