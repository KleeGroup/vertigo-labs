package io.vertigo.addons.account;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

/**
 * @author pchretien
 */
public interface AccountManager extends Component {
	boolean exists(URI<Account> accountURI);

	Account getAccount(URI<Account> accountURI);

	void saveAccount(Account account);
}
