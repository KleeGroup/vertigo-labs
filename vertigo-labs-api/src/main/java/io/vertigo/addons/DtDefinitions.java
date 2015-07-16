package io.vertigo.addons;

import io.vertigo.addons.account.Account;
import io.vertigo.addons.account.AccountGroup;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author pchretien
 */
public final class DtDefinitions implements Iterable<Class<?>> {
	@Override
	public Iterator<Class<?>> iterator() {
		return Arrays.asList(new Class<?>[] {
				Account.class,
				AccountGroup.class,
				Movie.class
		}).iterator();
	}
}
