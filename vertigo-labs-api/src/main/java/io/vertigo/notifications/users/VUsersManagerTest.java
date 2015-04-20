package io.vertigo.notifications.users;

import io.vertigo.AbstractTestCaseJU4;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

public class VUsersManagerTest extends AbstractTestCaseJU4 {
	@Inject
	private VUsersManager usersManager;

	@Test
	public void testSend() {
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(VUserProfile.class);
		final URI<VUserProfile> user0 = new URI<>(dtDefinition, "0");
		final URI<VUserProfile> user1 = new URI<>(dtDefinition, "1");
		final URI<VUserProfile> user2 = new URI<>(dtDefinition, "2");

		final VUserProfile userProfile0 = new VUserProfileBuilder()
				.withId("0")
				.withDisplayName("zeus")
				.build();
		usersManager.saveUserProfile(userProfile0);

		final VUserProfile userProfile1 = new VUserProfileBuilder()
				.withId("1")
				.withDisplayName("hector")
				.build();
		usersManager.saveUserProfile(userProfile1);

		final VUserProfile userProfile2 = new VUserProfileBuilder()
				.withId("2")
				.withDisplayName("Priam")
				.build();
		usersManager.saveUserProfile(userProfile2);

		//-----

		final Notification notification = new NotificationBuilder()
				.withSender(user0)
				.withTitle("news")
				.withMsg("discover this amazing app !!")
				.build();

		for (int i = 0; i < 1000; i++) {
			usersManager.send(notification, user1);
		}

		Assert.assertEquals(0, usersManager.getCurrentNotifications(user0).size());
		Assert.assertEquals(1000, usersManager.getCurrentNotifications(user1).size());
		Assert.assertEquals(0, usersManager.getCurrentNotifications(user2).size());
	}
}
