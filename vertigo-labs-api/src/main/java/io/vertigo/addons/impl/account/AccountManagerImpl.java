package io.vertigo.addons.impl.account;

import io.vertigo.addons.account.Account;
import io.vertigo.addons.account.AccountManager;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

import javax.inject.Inject;

/**
 * @author pchretien
 */
public final class AccountManagerImpl implements AccountManager {
	private final AccountPlugin accountPlugin;

	@Inject
	public AccountManagerImpl(final AccountPlugin accountPlugin) {
		Assertion.checkNotNull(accountPlugin);
		//-----
		this.accountPlugin = accountPlugin;
	}

	@Override
	public boolean exists(final URI<Account> accountURI) {
		Assertion.checkNotNull(accountURI);
		//-----
		return accountPlugin.exists(accountURI);
	}

	@Override
	public void saveUserProfile(final Account account) {
		Assertion.checkNotNull(account);
		//-----
		accountPlugin.saveAccount(account);
	}

	@Override
	public Account getUserProfile(final URI<Account> accountURI) {
		Assertion.checkNotNull(accountURI);
		//-----
		return accountPlugin.getAccount(accountURI);
	}
}
