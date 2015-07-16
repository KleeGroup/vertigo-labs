package io.vertigo.addons.notification;

import io.vertigo.addons.account.Account;
import io.vertigo.addons.account.AccountGroup;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.lang.Component;

import java.util.List;

/**
 * @author pchretien
 */
public interface NotificationManager extends Component {
	void send(Notification notification, URI<AccountGroup> groupURI);

	//	void emit(NotificationEvent notificationEvent);

	List<Notification> getCurrentNotifications(URI<Account> accountURI);

	//	void acquit (VUserProfile userProfile, Notification)

}
