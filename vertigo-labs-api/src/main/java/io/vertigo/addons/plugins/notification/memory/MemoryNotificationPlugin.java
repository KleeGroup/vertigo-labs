package io.vertigo.addons.plugins.notification.memory;

import io.vertigo.addons.account.Account;
import io.vertigo.addons.impl.notification.NotificationEvent;
import io.vertigo.addons.impl.notification.NotificationPlugin;
import io.vertigo.addons.notification.Notification;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pchretien
 */
public final class MemoryNotificationPlugin implements NotificationPlugin {
	private final Map<URI<Account>, List<Notification>> notificationsByUserId = new ConcurrentHashMap<>();

	@Override
	public void emit(final NotificationEvent notificationEvent) {
		Assertion.checkNotNull(notificationEvent);
		//-----
		//0 - Remplir la pile des événements

		//1 - Dépiler les événemnts en asynchrone FIFO
		for (final URI<Account> userProfileURI : notificationEvent.getToUserProfileURIs()) {
			obtainNotifications(userProfileURI).add(notificationEvent.getNotification());
		}

		//2 - gestion globale async des erreurs
	}

	@Override
	public List<Notification> getCurrentNotifications(final URI<Account> userProfileURI) {
		Assertion.checkNotNull(userProfileURI);
		//-----
		final List<Notification> notifications = notificationsByUserId.get(userProfileURI);
		if (notifications == null) {
			return Collections.emptyList();
		}
		return notifications;
	}

	private List<Notification> obtainNotifications(final URI<Account> userPofileURI) {
		Assertion.checkNotNull(userPofileURI);
		//-----
		List<Notification> notifications = notificationsByUserId.get(userPofileURI);
		if (notifications == null) {
			notifications = new ArrayList<>();
			notificationsByUserId.put(userPofileURI, notifications);
		}
		return notifications;
	}

}