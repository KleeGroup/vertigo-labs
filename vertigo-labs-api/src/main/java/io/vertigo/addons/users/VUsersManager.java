package io.vertigo.addons.users;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

/**
 * @author pchretien
 */
public interface VUsersManager extends Component {
	boolean exists(URI<VUserProfile> userPofileURI);

	VUserProfile getUserProfile(URI<VUserProfile> userPofileURI);

	void saveUserProfile(VUserProfile userPofile);
}
