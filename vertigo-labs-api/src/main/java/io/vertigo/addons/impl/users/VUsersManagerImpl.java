package io.vertigo.addons.impl.users;

import io.vertigo.addons.users.VUserProfile;
import io.vertigo.addons.users.VUsersManager;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

import javax.inject.Inject;

public final class VUsersManagerImpl implements VUsersManager {
	private final VUsersPlugin usersPlugin;

	@Inject
	public VUsersManagerImpl(final VUsersPlugin usersPlugin) {
		Assertion.checkNotNull(usersPlugin);
		//-----
		this.usersPlugin = usersPlugin;
	}

	@Override
	public boolean exists(final URI<VUserProfile> userPofileURI) {
		Assertion.checkNotNull(userPofileURI);
		//-----
		return usersPlugin.exists(userPofileURI);
	}

	@Override
	public void saveUserProfile(final VUserProfile userPofile) {
		Assertion.checkNotNull(userPofile);
		//-----
		usersPlugin.saveUserProfile(userPofile);
	}

	@Override
	public VUserProfile getUserProfile(final URI<VUserProfile> userPofileURI) {
		Assertion.checkNotNull(userPofileURI);
		//-----
		return usersPlugin.getUserProfile(userPofileURI);
	}
}
