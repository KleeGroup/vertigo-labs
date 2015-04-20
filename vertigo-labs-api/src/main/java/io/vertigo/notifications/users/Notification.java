package io.vertigo.notifications.users;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

public final class Notification {
	private final URI<VUserProfile> sender;
	private final String title;
	private final String msg;

	//	private final Date creationDate;

	public Notification(final URI<VUserProfile> sender, final String title, final String msg) {
		Assertion.checkNotNull(sender);
		Assertion.checkArgNotEmpty(title);
		Assertion.checkArgNotEmpty(msg);
		//-----
		this.sender = sender;
		this.title = title;
		this.msg = msg;
		//	creationDate = new Date();
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
}
