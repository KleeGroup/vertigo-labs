package io.vertigo.addons;

import io.vertigo.AbstractTestCaseJU4;
import io.vertigo.addons.events.Event;
import io.vertigo.addons.events.EventBuilder;
import io.vertigo.addons.events.EventListener;
import io.vertigo.addons.events.EventsManager;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

public class AddonsTest extends AbstractTestCaseJU4 {
	@Inject
	private EventsManager eventManager;

	//
	//	private URI<Account> accountURI0;
	//	private URI<Account> accountURI1;
	//	private URI<Account> accountURI2;
	//	private URI<AccountGroup> groupURI;
	//
	//	@Test
	//	public void testComments() {
	//		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(Account.class);
	//		final DtDefinition movieDefinition = DtObjectUtil.findDtDefinition(Movie.class);
	//		final URI<Account> user0 = new URI<>(dtDefinition, "0");
	//		final URI<Movie> movieURI = new URI<>(movieDefinition, "1");
	//		//-----
	//		final Comment comment = new CommentBuilder()
	//				.withAuthor(user0)
	//				.withMsg("Tu as bien fait de partir, Arthur Rimbaud! Tes dix-huit ans réfractaires à l'amitié, à la malveillance, à la sottise des poètes de Paris ainsi qu'au ronronnement d'abeille stérile de ta famille ardennaise un peu folle, tu as bien fait de les éparpiller aux vents du large..")
	//				.build();
	//		for (int i = 0; i < 10; i++) {
	//			commentManager.publish(comment, movieURI);
	//		}
	//
	//		Assert.assertEquals(10, commentManager.getComments(movieURI).size());
	//	}

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
