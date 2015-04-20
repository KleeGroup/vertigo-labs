package io.vertigo.notifications.users;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;

public final class VUserProfileBuilder implements Builder<VUserProfile> {
	private String myId;
	private String myDisplayName;

	public VUserProfileBuilder withId(final String id) {
		Assertion.checkArgument(myId == null, "idalready set");
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
