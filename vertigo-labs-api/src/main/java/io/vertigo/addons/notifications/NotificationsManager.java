package io.vertigo.addons.notifications;

import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

import java.util.List;

public interface NotificationsManager extends Component {
	//	void attach(URI<VUserProfile> userPofileURI, URI<VUserGroup> userGroupURI);
	//
	//	void detach(URI<VUserProfile> userPofileURI, URI<VUserGroup> userGroupURI);
	//
	//	List<VUserGroup> getGroups();

	//
	//-----
	void send(Notification notification, URI<VUserProfile> userPofileURI);

	//	void emit(NotificationEvent notificationEvent);

	List<Notification> getCurrentNotifications(URI<VUserProfile> userProfileURI);

	//	void acquit (VUserProfile userProfile, Notification)

}
