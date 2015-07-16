package io.vertigo.addons;

import io.vertigo.AbstractTestCaseJU4;
import io.vertigo.addons.account.Account;
import io.vertigo.addons.account.AccountBuilder;
import io.vertigo.addons.account.AccountGroup;
import io.vertigo.addons.account.AccountManager;
import io.vertigo.addons.comment.Comment;
import io.vertigo.addons.comment.CommentBuilder;
import io.vertigo.addons.comment.CommentManager;
import io.vertigo.addons.events.Event;
import io.vertigo.addons.events.EventBuilder;
import io.vertigo.addons.events.EventListener;
import io.vertigo.addons.events.EventsManager;
import io.vertigo.addons.notification.Notification;
import io.vertigo.addons.notification.NotificationBuilder;
import io.vertigo.addons.notification.NotificationManager;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

public class AddonsTest extends AbstractTestCaseJU4 {
	@Inject
	private NotificationManager notificationsManager;

	@Inject
	private AccountManager usersManager;

	@Inject
	private CommentManager commentsManager;

	@Inject
	private EventsManager eventsManager;

	@Test
	public void testAccounts() {
		final Account account0 = new AccountBuilder()
				.withId("0")
				.withDisplayName("zeus")
				.build();
		usersManager.createAccount(account0);

		final Account account1 = new AccountBuilder()
				.withId("1")
				.withDisplayName("hector")
				.build();
		usersManager.createAccount(account1);

		final Account account2 = new AccountBuilder()
				.withId("2")
				.withDisplayName("Priam")
				.build();
		usersManager.createAccount(account2);

	}

	@Test
	public void testNotifications() throws InterruptedException {
		testAccounts();

		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(Account.class);
		final URI<Account> account0 = new URI<>(dtDefinition, "0");
		final URI<Account> account1 = new URI<>(dtDefinition, "1");
		final URI<Account> account2 = new URI<>(dtDefinition, "2");

		final URI<AccountGroup> groupURI = new URI<>(DtObjectUtil.findDtDefinition(AccountGroup.class), "all");

		final AccountGroup group = new AccountGroup("all", "all groups");
		usersManager.createGroup(group);
		usersManager.attach(account0, groupURI);
		usersManager.attach(account2, groupURI);

		Assert.assertEquals(2, usersManager.getAccountURIs(groupURI));

		//-----
		final Notification notification = new NotificationBuilder()
				.withSender(account0)
				.withTitle("news")
				.withMsg("discover this amazing app !!")
				.withTTLinSeconds(2)
				.build();

		for (int i = 0; i < 10; i++) {
			notificationsManager.send(notification, groupURI);
		}

		Assert.assertEquals(10, notificationsManager.getCurrentNotifications(account0).size());
		Assert.assertEquals(10, notificationsManager.getCurrentNotifications(account1).size());
		Assert.assertEquals(10, notificationsManager.getCurrentNotifications(account2).size());
		Thread.sleep(3000);
		Assert.assertEquals(0, notificationsManager.getCurrentNotifications(account1).size());
	}

	@Test
	public void testComments() {
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(Account.class);
		final DtDefinition movieDefinition = DtObjectUtil.findDtDefinition(Movie.class);
		final URI<Account> user0 = new URI<>(dtDefinition, "0");
		final URI<Movie> movieURI = new URI<>(movieDefinition, "1");
		//-----
		final Comment comment = new CommentBuilder()
				.withAuthor(user0)
				.withMsg("Tu as bien fait de partir, Arthur Rimbaud! Tes dix-huit ans réfractaires à l'amitié, à la malveillance, à la sottise des poètes de Paris ainsi qu'au ronronnement d'abeille stérile de ta famille ardennaise un peu folle, tu as bien fait de les éparpiller aux vents du large..")
				.build();
		for (int i = 0; i < 10; i++) {
			commentsManager.publish(comment, movieURI);
		}

		Assert.assertEquals(10, commentsManager.getComments(movieURI).size());
	}

	@Test
	public void testEvents() throws InterruptedException {
		final AtomicBoolean flag = new AtomicBoolean(false);
		eventsManager.register("news", new EventListener() {
			@Override
			public void onEvent(final Event event) {
				System.out.println("OK");
				Assert.assertEquals("ping", event.getPayload());
				flag.set(true);
			}
		});

		final Event event = new EventBuilder()
				.withPayload("ping")
				.build();
		eventsManager.fire("news", event);
		Thread.sleep(1000);
		Assert.assertTrue(flag.get());

	}

}
