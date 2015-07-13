package io.vertigo.addons.notifications;

import io.vertigo.addons.account.Account;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

import java.util.List;

/**
 * @author pchretien
 */
public interface NotificationsManager extends Component {
	//	void attach(URI<VUserProfile> userPofileURI, URI<VUserGroup> userGroupURI);
	//
	//	void detach(URI<VUserProfile> userPofileURI, URI<VUserGroup> userGroupURI);
	//
	//	List<VUserGroup> getGroups();

	//
	//-----
	void send(Notification notification, URI<Account> userPofileURI);

	//	void emit(NotificationEvent notificationEvent);

	List<Notification> getCurrentNotifications(URI<Account> userProfileURI);

	//	void acquit (VUserProfile userProfile, Notification)

}
