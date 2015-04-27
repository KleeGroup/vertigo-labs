package io.vertigo.notifications.users;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

public final class Notification {
	private final URI<VUserProfile> sender;
	private final String title;
	private final String msg;
	private final int ttlInSeconds;

	//	private final Date creationDate;

	public Notification(final URI<VUserProfile> sender, final String title, final String msg, final int ttlInSeconds) {
		Assertion.checkNotNull(sender);
		Assertion.checkArgNotEmpty(title);
		Assertion.checkArgNotEmpty(msg);
		Assertion.checkArgument(ttlInSeconds == -1 || ttlInSeconds > 0, "ttl must be positive or undefined (-1).");
		//-----
		this.sender = sender;
		this.title = title;
		this.msg = msg;
		this.ttlInSeconds = ttlInSeconds;
	}

	public URI<VUserProfile> getSender() {
		return sender;
	}

	//	public Date getCreationDate() {
	//		return creationDate;
	//	}

	public String getTitle() {
		return title;
	}

	public String getMsg() {
		return msg;
	}

	public int getTTLInSeconds() {
		return ttlInSeconds;
	}
}
