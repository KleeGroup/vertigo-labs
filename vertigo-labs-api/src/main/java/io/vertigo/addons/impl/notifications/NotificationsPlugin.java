package io.vertigo.addons.impl.notifications;

import io.vertigo.addons.notifications.Notification;
import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Plugin;

import java.util.List;

/**
 * @author pchretien
 */
public interface NotificationsPlugin extends Plugin {
	void emit(NotificationEvent notificationEvent);

	List<Notification> getCurrentNotifications(URI<VUserProfile> userProfileURI);

}
