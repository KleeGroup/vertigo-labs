package io.vertigo.notifications.impl.users;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Plugin;
import io.vertigo.notifications.users.VUserProfile;

public interface VUsersPlugin extends Plugin {
	boolean exists(URI<VUserProfile> userPofileURI);

	VUserProfile getUserProfile(URI<VUserProfile> userPofileURI);

	void saveUserProfile(VUserProfile userPofile);
}
