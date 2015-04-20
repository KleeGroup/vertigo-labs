package io.vertigo.notifications.impl.users;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;
import io.vertigo.notifications.users.Notification;
import io.vertigo.notifications.users.VUserProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NotificationEvent {
	private final Notification notification;
	public final List<URI<VUserProfile>> toUserProfileURIs;

	//private final List<VUserGroup> toUserGroups;

	NotificationEvent(final Notification notification, final List<URI<VUserProfile>> toUserProfileURIs) {
		Assertion.checkNotNull(notification);
		Assertion.checkNotNull(toUserProfileURIs);
		//-----
		this.notification = notification;
		this.toUserProfileURIs = Collections.unmodifiableList(new ArrayList<>(toUserProfileURIs));
	}

	public Notification getNotification() {
		return notification;
	}

	public List<URI<VUserProfile>> getToUserProfileURIs() {
		return toUserProfileURIs;
	}
}
