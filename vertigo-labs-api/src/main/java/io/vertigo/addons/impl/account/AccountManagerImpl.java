package io.vertigo.addons.impl.account;

import io.vertigo.addons.account.Account;
import io.vertigo.addons.account.AccountGroup;
import io.vertigo.addons.account.AccountManager;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

/**
 * @author pchretien
 */
public final class AccountManagerImpl implements AccountManager {
	private final AccountStorePlugin accountPlugin;

	@Inject
	public AccountManagerImpl(final AccountStorePlugin accountPlugin) {
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
	public void createAccount(final Account account) {
		Assertion.checkNotNull(account);
		//-----
		accountPlugin.createAccount(account);
	}

	@Override
	public void updateAccount(final Account account) {
		Assertion.checkNotNull(account);
		//-----
		accountPlugin.createAccount(account);
	}

	@Override
	public Account getAccount(final URI<Account> accountURI) {
		Assertion.checkNotNull(accountURI);
		//-----
		return accountPlugin.getAccount(accountURI);
	}

	@Override
	public Collection<Account> getAllAccounts() {
		return accountPlugin.getAllAccounts();
	}

	@Override
	public Collection<AccountGroup> getAllGroups() {
		return accountPlugin.getAllGroups();
	}

	@Override
	public Collection<AccountGroup> getGroups(URI<Account> accountURI) {
		Assertion.checkNotNull(accountURI);
		//-----
		return accountPlugin.getGroups(accountURI);
	}

	@Override
	public Set<URI<Account>> getAccountURIs(URI<AccountGroup> groupURI) {
		Assertion.checkNotNull(groupURI);
		//-----
		return accountPlugin.getAccountURIs(groupURI);
	}

	@Override
	public void createGroup(AccountGroup group) {
		Assertion.checkNotNull(group);
		//-----
		accountPlugin.createGroup(group);
	}

	@Override
	public void attach(URI<Account> accountURI, URI<AccountGroup> groupURI) {
		Assertion.checkNotNull(accountURI);
		Assertion.checkNotNull(groupURI);
		//-----
		accountPlugin.attach(accountURI, groupURI);
	}

	@Override
	public void detach(URI<Account> accountURI, URI<AccountGroup> groupURI) {
		Assertion.checkNotNull(accountURI);
		Assertion.checkNotNull(groupURI);
		//-----
		accountPlugin.detach(accountURI, groupURI);
	}

	@Override
	public AccountGroup getGroup(URI<AccountGroup> groupURI) {
		Assertion.checkNotNull(groupURI);
		//-----
		return accountPlugin.getGroup(groupURI);
	}
}
