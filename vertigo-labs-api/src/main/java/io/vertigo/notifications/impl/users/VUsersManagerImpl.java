package io.vertigo.notifications.impl.users;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Assertion;
import io.vertigo.notifications.users.Notification;
import io.vertigo.notifications.users.VUserProfile;
import io.vertigo.notifications.users.VUsersManager;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public final class VUsersManagerImpl implements VUsersManager {
	private final VUsersPlugin usersPlugin;
	private final NotificationsPlugin notificationsPlugin;

	@Inject
	public VUsersManagerImpl(final VUsersPlugin usersPlugin, final NotificationsPlugin notificationsPlugin) {
		Assertion.checkNotNull(usersPlugin);
		Assertion.checkNotNull(notificationsPlugin);
		//-----
		this.usersPlugin = usersPlugin;
		this.notificationsPlugin = notificationsPlugin;
	}

	@Override
	public boolean exists(final URI<VUserProfile> userPofileURI) {
		Assertion.checkNotNull(userPofileURI);
		//-----
		return usersPlugin.exists(userPofileURI);
	}

	@Override
	public void saveUserProfile(final VUserProfile userPofile) {
		Assertion.checkNotNull(userPofile);
		//-----
		usersPlugin.saveUserProfile(userPofile);
	}

	@Override
	public VUserProfile getUserProfile(final URI<VUserProfile> userPofileURI) {
		Assertion.checkNotNull(userPofileURI);
		//-----
		return usersPlugin.getUserProfile(userPofileURI);
	}

	@Override
	public void send(final Notification notification, final URI<VUserProfile> userProfileURI) {
		Assertion.checkNotNull(notification);
		Assertion.checkNotNull(userProfileURI);
		//-----
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
