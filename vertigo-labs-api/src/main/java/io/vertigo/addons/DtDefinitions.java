package io.vertigo.addons;

import io.vertigo.addons.users.VUserProfile;

import java.util.Arrays;
import java.util.Iterator;

public final class DtDefinitions implements Iterable<Class<?>> {
	@Override
	public Iterator<Class<?>> iterator() {
		return Arrays.asList(new Class<?>[] {
				VUserProfile.class,
				Movie.class
		}).iterator();
	}
}