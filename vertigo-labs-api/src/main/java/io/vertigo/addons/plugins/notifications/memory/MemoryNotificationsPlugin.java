package io.vertigo.addons.plugins.notifications.memory;

import io.vertigo.addons.impl.notifications.NotificationEvent;
import io.vertigo.addons.impl.notifications.NotificationsPlugin;
import io.vertigo.addons.notifications.Notification;
import io.vertigo.addons.users.VUserProfile;
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
public final class MemoryNotificationsPlugin implements NotificationsPlugin {
	private final Map<URI<VUserProfile>, List<Notification>> notificationsByUserId = new ConcurrentHashMap<>();

	@Override
	public void emit(final NotificationEvent notificationEvent) {
		Assertion.checkNotNull(notificationEvent);
		//-----
		//0 - Remplir la pile des événements

		//1 - Dépiler les événemnts en asynchrone FIFO
		for (final URI<VUserProfile> userProfileURI : notificationEvent.getToUserProfileURIs()) {
			obtainNotifications(userProfileURI).add(notificationEvent.getNotification());
		}

		//2 - gestion globale async des erreurs
	}

	@Override
	public List<Notification> getCurrentNotifications(final URI<VUserProfile> userProfileURI) {
		Assertion.checkNotNull(userProfileURI);
		//-----
		final List<Notification> notifications = notificationsByUserId.get(userProfileURI);
		if (notifications == null) {
			return Collections.emptyList();
		}
		return notifications;
	}

	private List<Notification> obtainNotifications(final URI<VUserProfile> userPofileURI) {
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