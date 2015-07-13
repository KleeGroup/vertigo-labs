package io.vertigo.addons.impl.account;

import io.vertigo.addons.account.Account;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Plugin;

/**
 * @author pchretien
 */
public interface AccountPlugin extends Plugin {
	boolean exists(URI<Account> userPofileURI);

	Account getAccount(URI<Account> userPofileURI);

	void saveAccount(Account userPofile);
}
