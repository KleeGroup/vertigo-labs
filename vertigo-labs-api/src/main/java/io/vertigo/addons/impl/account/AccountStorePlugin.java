package io.vertigo.addons.impl.account;

import io.vertigo.addons.account.Account;
import io.vertigo.addons.account.AccountGroup;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Plugin;

import java.util.Collection;
import java.util.Set;

/**
 * @author pchretien
 */
public interface AccountStorePlugin extends Plugin {
	boolean exists(URI<Account> accountURI);

	Account getAccount(URI<Account> accountURI);

	//il est possible de proposer tous les groupes mais pas tous les accounts ?
	Collection<Account> getAllAccounts();

	void createAccount(Account account);

	void updateAccount(Account account);

	//-----
	//il est possible de proposer tous les groupes mais pas tous les accounts ?
	Collection<AccountGroup> getAllGroups();

	AccountGroup getGroup(URI<AccountGroup> groupURI);

	void createGroup(AccountGroup group);

	//-----
	void attach(URI<Account> accountURI, URI<AccountGroup> groupURI);

	void detach(URI<Account> accountURI, URI<AccountGroup> groupURI);

	Collection<AccountGroup> getGroups(URI<Account> accountURI);

	Set<URI<Account>> getAccountURIs(URI<AccountGroup> groupURI);
}
