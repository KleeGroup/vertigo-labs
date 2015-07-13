package io.vertigo.addons.account;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

/**
 * @author pchretien
 */
public interface AccountManager extends Component {
	boolean exists(URI<Account> userPofileURI);

	Account getUserProfile(URI<Account> userPofileURI);

	void saveUserProfile(Account userPofile);
}
