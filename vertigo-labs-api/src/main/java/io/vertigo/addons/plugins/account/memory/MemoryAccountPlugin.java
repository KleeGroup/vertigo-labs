package io.vertigo.addons.plugins.account.memory;

import io.vertigo.addons.account.Account;
import io.vertigo.addons.impl.account.AccountPlugin;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.lang.Assertion;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pchretien
 */
public final class MemoryAccountPlugin implements AccountPlugin {
	private final Map<URI<Account>, Account> userProfiles = new ConcurrentHashMap<>();

	@Override
	public boolean exists(final URI<Account> userPofileURI) {
		Assertion.checkNotNull(userPofileURI);
		//-----
		return userProfiles.containsKey(userPofileURI);
	}

	@Override
	public void saveAccount(final Account account) {
		Assertion.checkNotNull(account);
		//-----
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(account);
		final URI<Account> uri = new URI<>(dtDefinition, account.getId());
		userProfiles.put(uri, account);
	}

	@Override
	public Account getAccount(final URI<Account> userPofileURI) {
		Assertion.checkNotNull(userPofileURI);
		//-----
		return userProfiles.get(userPofileURI);
	}
}
