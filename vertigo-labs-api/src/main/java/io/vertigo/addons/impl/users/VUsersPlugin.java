package io.vertigo.addons.impl.users;

import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Plugin;

public interface VUsersPlugin extends Plugin {
	boolean exists(URI<VUserProfile> userPofileURI);

	VUserProfile getUserProfile(URI<VUserProfile> userPofileURI);

	void saveUserProfile(VUserProfile userPofile);
}
