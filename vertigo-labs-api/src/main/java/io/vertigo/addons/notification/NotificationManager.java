package io.vertigo.addons.notification;

import io.vertigo.addons.account.Account;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

import java.util.List;

/**
 * @author pchretien
 */
public interface NotificationManager extends Component {
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
