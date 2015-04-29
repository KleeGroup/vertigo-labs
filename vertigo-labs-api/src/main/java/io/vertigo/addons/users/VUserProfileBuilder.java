package io.vertigo.addons.users;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;

/**
 * @author pchretien
 */
public final class VUserProfileBuilder implements Builder<VUserProfile> {
	private String myId;
	private String myDisplayName;

	public VUserProfileBuilder withId(final String id) {
		Assertion.checkArgument(myId == null, "id already set");
		Assertion.checkArgNotEmpty(id);
		//-----
		this.myId = id;
		return this;
	}

	public VUserProfileBuilder withDisplayName(final String displayName) {
		Assertion.checkArgument(myDisplayName == null, "displayName already set");
		Assertion.checkArgNotEmpty(displayName);
		//-----
		this.myDisplayName = displayName;
		return this;
	}

	@Override
	public VUserProfile build() {
		return new VUserProfile(myId, myDisplayName);
	}
}
