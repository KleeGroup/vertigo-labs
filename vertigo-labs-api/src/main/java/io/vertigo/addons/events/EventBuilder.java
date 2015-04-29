package io.vertigo.addons.events;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;

import java.util.UUID;

public final class EventBuilder implements Builder<Event> {
	private UUID myUuid;
	private String myPayload;

	public EventBuilder withPayload(final String payload) {
		Assertion.checkArgument(myPayload == null, "payload already set");
		Assertion.checkArgNotEmpty(payload);
		//-----
		this.myPayload = payload;
		return this;
	}

	public EventBuilder withUUID(final UUID uuid) {
		Assertion.checkArgument(myUuid == null, "uuid already set");
		Assertion.checkNotNull(uuid);
		//-----
		this.myUuid = uuid;
		return this;
	}

	@Override
	public Event build() {
		myUuid = myUuid == null ? UUID.randomUUID() : myUuid;
		return new Event(myUuid, myPayload);
	}
}
