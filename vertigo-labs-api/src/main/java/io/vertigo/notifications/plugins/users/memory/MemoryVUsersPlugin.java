package io.vertigo.notifications.plugins.users.memory;

import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.lang.Assertion;
import io.vertigo.notifications.impl.users.VUsersPlugin;
import io.vertigo.notifications.users.VUserProfile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MemoryVUsersPlugin implements VUsersPlugin {
	private final Map<URI<VUserProfile>, VUserProfile> userProfiles = new ConcurrentHashMap<>();

	@Override
	public boolean exists(final URI<VUserProfile> userPofileURI) {
		Assertion.checkNotNull(userPofileURI);
		//-----
		return userProfiles.containsKey(userPofileURI);
	}

	@Override
	public void saveUserProfile(final VUserProfile userPofile) {
		Assertion.checkNotNull(userPofile);
		//-----
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(userPofile);
		final URI<VUserProfile> uri = new URI<>(dtDefinition, userPofile.getId());
		userProfiles.put(uri, userPofile);
	}

	@Override
	public VUserProfile getUserProfile(final URI<VUserProfile> userPofileURI) {
		Assertion.checkNotNull(userPofileURI);
		//-----
		return userProfiles.get(userPofileURI);
	}
}
