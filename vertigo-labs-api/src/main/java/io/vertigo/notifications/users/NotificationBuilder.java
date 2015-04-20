package io.vertigo.notifications.users;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;

public final class NotificationBuilder implements Builder<Notification> {
	private String myTitle;
	private String myMsg;
	private URI<VUserProfile> mySender;

	public NotificationBuilder withSender(final URI<VUserProfile> sender) {
		Assertion.checkArgument(mySender == null, "sender already set");
		Assertion.checkNotNull(sender);
		//-----
		this.mySender = sender;
		return this;
	}

	public NotificationBuilder withTitle(final String title) {
		Assertion.checkArgument(myTitle == null, "title already set");
		Assertion.checkArgNotEmpty(title);
		//-----
		this.myTitle = title;
		return this;
	}

	public NotificationBuilder withMsg(final String msg) {
		Assertion.checkArgument(myMsg == null, "msg already set");
		Assertion.checkArgNotEmpty(msg);
		//-----
		this.myMsg = msg;
		return this;
	}

	@Override
	public Notification build() {
		return new Notification(mySender, myTitle, myMsg);
	}
}
