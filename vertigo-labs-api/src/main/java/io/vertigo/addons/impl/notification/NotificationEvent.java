package io.vertigo.addons.impl.notification;

import io.vertigo.addons.account.Account;
import io.vertigo.addons.notification.Notification;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author pchretien
 */
public final class NotificationEvent {
	private final Notification notification;
	public final List<URI<Account>> toUserProfileURIs;

	//private final List<VUserGroup> toUserGroups;

	NotificationEvent(final Notification notification, final List<URI<Account>> toUserProfileURIs) {
		Assertion.checkNotNull(notification);
		Assertion.checkNotNull(toUserProfileURIs);
		//-----
		this.notification = notification;
		this.toUserProfileURIs = Collections.unmodifiableList(new ArrayList<>(toUserProfileURIs));
	}

	public Notification getNotification() {
		return notification;
	}

	public List<URI<Account>> getToUserProfileURIs() {
		return toUserProfileURIs;
	}
}
