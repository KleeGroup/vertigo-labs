package io.vertigo.addons;

import io.vertigo.AbstractTestCaseJU4;
import io.vertigo.addons.comments.Comment;
import io.vertigo.addons.comments.CommentBuilder;
import io.vertigo.addons.comments.CommentsManager;
import io.vertigo.addons.notifications.Notification;
import io.vertigo.addons.notifications.NotificationBuilder;
import io.vertigo.addons.notifications.NotificationsManager;
import io.vertigo.addons.users.VUserProfile;
import io.vertigo.addons.users.VUserProfileBuilder;
import io.vertigo.addons.users.VUsersManager;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

public class AddonsTest extends AbstractTestCaseJU4 {
	@Inject
	private NotificationsManager notificationsManager;

	@Inject
	private VUsersManager usersManager;

	@Inject
	private CommentsManager commentsManager;

	@Test
	public void testUsers() {
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
	}

	@Test
	public void testNotifications() throws InterruptedException {
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(VUserProfile.class);
		final URI<VUserProfile> user0 = new URI<>(dtDefinition, "0");
		final URI<VUserProfile> user1 = new URI<>(dtDefinition, "1");
		final URI<VUserProfile> user2 = new URI<>(dtDefinition, "2");
		//-----
		final Notification notification = new NotificationBuilder()
				.withSender(user0)
				.withTitle("news")
				.withMsg("discover this amazing app !!")
				.withTTLinSeconds(2)
				.build();

		for (int i = 0; i < 10; i++) {
			notificationsManager.send(notification, user1);
		}

		Assert.assertEquals(0, notificationsManager.getCurrentNotifications(user0).size());
		Assert.assertEquals(10, notificationsManager.getCurrentNotifications(user1).size());
		Assert.assertEquals(0, notificationsManager.getCurrentNotifications(user2).size());
		Thread.sleep(3000);
		Assert.assertEquals(0, notificationsManager.getCurrentNotifications(user1).size());
	}

	@Test
	public void testComments() {
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(VUserProfile.class);
		final DtDefinition movieDefinition = DtObjectUtil.findDtDefinition(Movie.class);
		final URI<VUserProfile> user0 = new URI<>(dtDefinition, "0");
		final URI<Movie> movieURI = new URI<>(movieDefinition, "1");
		//-----
		final Comment comment = new CommentBuilder()
				.withAuthor(user0)
				.withMsg("Tu as bien fait de partir, Arthur Rimbaud! Tes dix-huit ans réfractaires à l'amitié, à la malveillance, à la sottise des poètes de Paris ainsi qu'au ronronnement d'abeille stérile de ta famille ardennaise un peu folle, tu as bien fait de les éparpiller aux vents du large..")
				.build();

		commentsManager.publish(comment, movieURI);

		Assert.assertEquals(1, commentsManager.getComments(movieURI).size());
	}
}
