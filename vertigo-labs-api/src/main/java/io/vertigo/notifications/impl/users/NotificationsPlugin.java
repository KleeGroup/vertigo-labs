package io.vertigo.notifications.impl.users;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Plugin;
import io.vertigo.notifications.users.Notification;
import io.vertigo.notifications.users.VUserProfile;

import java.util.List;

public interface NotificationsPlugin extends Plugin {
	void emit(NotificationEvent notificationEvent);

	List<Notification> getCurrentNotifications(URI<VUserProfile> userProfileURI);

}
