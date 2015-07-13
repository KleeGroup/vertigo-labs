package io.vertigo.addons.account;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;

/**
 * @author pchretien
 */
public final class AccountBuilder implements Builder<Account> {
	private String myId;
	private String myDisplayName;

	public AccountBuilder withId(final String id) {
		Assertion.checkArgument(myId == null, "id already set");
		Assertion.checkArgNotEmpty(id);
		//-----
		this.myId = id;
		return this;
	}

	public AccountBuilder withDisplayName(final String displayName) {
		Assertion.checkArgument(myDisplayName == null, "displayName already set");
		Assertion.checkArgNotEmpty(displayName);
		//-----
		this.myDisplayName = displayName;
		return this;
	}

	@Override
	public Account build() {
		return new Account(myId, myDisplayName);
	}
}
