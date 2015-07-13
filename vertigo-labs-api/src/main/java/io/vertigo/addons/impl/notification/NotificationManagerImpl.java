package io.vertigo.addons.impl.notification;

import io.vertigo.addons.account.Account;
import io.vertigo.addons.notification.Notification;
import io.vertigo.addons.notification.NotificationManager;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * @author pchretien
 */
public final class NotificationManagerImpl implements NotificationManager {
	private final NotificationPlugin notificationsPlugin;

	@Inject
	public NotificationManagerImpl(final NotificationPlugin notificationsPlugin) {
		Assertion.checkNotNull(notificationsPlugin);
		//-----
		this.notificationsPlugin = notificationsPlugin;
	}

	@Override
	public void send(final Notification notification, final URI<Account> userProfileURI) {
		final NotificationEvent notificationEvent = new NotificationEvent(notification, Collections.singletonList(userProfileURI));
		notificationsPlugin.emit(notificationEvent);
	}

	@Override
	public List<Notification> getCurrentNotifications(final URI<Account> userProfileURI) {
		Assertion.checkNotNull(userProfileURI);
		//-----
		return notificationsPlugin.getCurrentNotifications(userProfileURI);
	}
}
