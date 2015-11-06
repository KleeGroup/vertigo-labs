package io.vertigo.addons.events;

import io.vertigo.lang.Assertion;

import java.util.UUID;

/**
 * @author pchretien
 */
public final class Event {
	private final UUID uuid;
	private final String payload;

	Event(final UUID uuid, final String payload) {
		Assertion.checkNotNull(uuid);
		Assertion.checkNotNull(payload);
		//-----
		this.uuid = uuid;
		this.payload = payload;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getPayload() {
		return payload;
	}
}
