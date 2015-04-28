package io.vertigo.addons.impl.notifications;

import io.vertigo.addons.notifications.Notification;
import io.vertigo.addons.notifications.NotificationsManager;
import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public final class NotificationsManagerImpl implements NotificationsManager {
	private final NotificationsPlugin notificationsPlugin;

	@Inject
	public NotificationsManagerImpl(final NotificationsPlugin notificationsPlugin) {
		Assertion.checkNotNull(notificationsPlugin);
		//-----
		this.notificationsPlugin = notificationsPlugin;
	}

	@Override
	public void send(final Notification notification, final URI<VUserProfile> userProfileURI) {
		final NotificationEvent notificationEvent = new NotificationEvent(notification, Collections.singletonList(userProfileURI));
		notificationsPlugin.emit(notificationEvent);
	}

	@Override
	public List<Notification> getCurrentNotifications(final URI<VUserProfile> userProfileURI) {
		Assertion.checkNotNull(userProfileURI);
		//-----
		return notificationsPlugin.getCurrentNotifications(userProfileURI);
	}
}
