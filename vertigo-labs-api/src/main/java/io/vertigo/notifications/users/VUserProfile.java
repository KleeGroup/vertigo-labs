package io.vertigo.notifications.users;

import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.lang.Assertion;

@DtDefinition
public final class VUserProfile implements DtObject {
	private static final long serialVersionUID = 7509030642946579907L;

	@Field(domain = "DO_ID", type = "PRIMARY_KEY", notNull = true, label = "id")
	private final String id;

	@Field(domain = "DO_NAME", label = "displayName")
	private final String displayName;

	VUserProfile(final String id, final String displayName) {
		Assertion.checkArgNotEmpty(id);
		Assertion.checkArgNotEmpty(displayName);
		//-----
		this.displayName = displayName;
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}
}
