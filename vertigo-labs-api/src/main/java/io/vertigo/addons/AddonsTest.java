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
	private NotificationManager notificationManager;

	@Inject
	private AccountManager accountManager;

	@Inject
	private CommentManager commentManager;

	@Inject
	private EventsManager eventManager;

	private URI<Account> accountURI0;
	private URI<Account> accountURI1;
	private URI<Account> accountURI2;
	private URI<AccountGroup> groupURI;

	private static URI<Account> createAccountURI(String id) {
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(Account.class);
		return new URI<>(dtDefinition, id);
	}

	@Override
	protected void doSetUp() throws Exception {
		accountURI0 = createAccountURI("0");
		accountURI1 = createAccountURI("1");
		accountURI2 = createAccountURI("2");
		groupURI = new URI<>(DtObjectUtil.findDtDefinition(AccountGroup.class), "all");
	}

	@Test
	public void testAccounts() {
		final Account account0 = new AccountBuilder()
				.withId("0")
				.withDisplayName("zeus")
				.build();
		accountManager.getStore().createAccount(account0);

		final Account account1 = new AccountBuilder()
				.withId("1")
				.withDisplayName("hector")
				.build();
		accountManager.getStore().createAccount(account1);

		final Account account2 = new AccountBuilder()
				.withId("2")
				.withDisplayName("Priam")
				.build();
		accountManager.getStore().createAccount(account2);

		final AccountGroup group = new AccountGroup("all", "all groups");
		accountManager.getStore().createGroup(group);
		accountManager.getStore().attach(accountURI0, groupURI);
		accountManager.getStore().attach(accountURI2, groupURI);

		Assert.assertEquals(1, accountManager.getStore().getGroups(accountURI0).size());
		Assert.assertEquals(0, accountManager.getStore().getGroups(accountURI1).size());
		Assert.assertEquals(2, accountManager.getStore().getAccountURIs(groupURI).size());
		accountManager.getStore().detach(accountURI0, groupURI);
		Assert.assertEquals(0, accountManager.getStore().getGroups(accountURI0).size());
		Assert.assertEquals(1, accountManager.getStore().getAccountURIs(groupURI).size());
		accountManager.getStore().attach(accountURI0, groupURI);
		Assert.assertEquals(1, accountManager.getStore().getGroups(accountURI0).size());
		Assert.assertEquals(2, accountManager.getStore().getAccountURIs(groupURI).size());

	}

	@Test
	public void testNotifications() throws InterruptedException {
		testAccounts();
		//-----
		final Notification notification = new NotificationBuilder()
				.withSender(accountURI0)
				.withTitle("news")
				.withMsg("discover this amazing app !!")
				.withTTLinSeconds(2)
				.build();

		for (int i = 0; i < 10; i++) {
			notificationManager.send(notification, groupURI);
		}

		Assert.assertEquals(10, notificationManager.getCurrentNotifications(accountURI0).size());
		Assert.assertEquals(0, notificationManager.getCurrentNotifications(accountURI1).size());
		Assert.assertEquals(10, notificationManager.getCurrentNotifications(accountURI2).size());
		Thread.sleep(3000);
		Assert.assertEquals(0, notificationManager.getCurrentNotifications(accountURI1).size());
	}

	@Test
	public void testNotificationsWithRemove() {
		testAccounts();
		//-----
		final Notification notification = new NotificationBuilder()
				.withSender(accountURI0)
				.withTitle("news")
				.withMsg("discover this amazing app !!")
				.build();

		Assert.assertEquals(0, notificationManager.getCurrentNotifications(accountURI0).size());
		Assert.assertEquals(0, notificationManager.getCurrentNotifications(accountURI1).size());
		Assert.assertEquals(0, notificationManager.getCurrentNotifications(accountURI2).size());

		notificationManager.send(notification, groupURI);

		Assert.assertEquals(1, notificationManager.getCurrentNotifications(accountURI0).size());
		Assert.assertEquals(0, notificationManager.getCurrentNotifications(accountURI1).size());
		Assert.assertEquals(1, notificationManager.getCurrentNotifications(accountURI2).size());

		notificationManager.remove(accountURI0, notificationManager.getCurrentNotifications(accountURI0).get(0).getUuid());

		Assert.assertEquals(0, notificationManager.getCurrentNotifications(accountURI0).size());
		Assert.assertEquals(0, notificationManager.getCurrentNotifications(accountURI1).size());
		Assert.assertEquals(1, notificationManager.getCurrentNotifications(accountURI2).size());

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
			commentManager.publish(comment, movieURI);
		}

		Assert.assertEquals(10, commentManager.getComments(movieURI).size());
	}

	@Test
	public void testEvents() throws InterruptedException {
		final AtomicBoolean flag = new AtomicBoolean(false);
		eventManager.register("news", new EventListener() {
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
		eventManager.fire("news", event);
		Thread.sleep(1000);
		Assert.assertTrue(flag.get());

	}

}
