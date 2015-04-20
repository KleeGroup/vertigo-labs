package io.vertigo.notifications.users;

import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

import java.util.List;

public interface VUsersManager extends Component {
	boolean exists(URI<VUserProfile> userPofileURI);

	VUserProfile getUserProfile(URI<VUserProfile> userPofileURI);

	void saveUserProfile(VUserProfile userPofile);

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
