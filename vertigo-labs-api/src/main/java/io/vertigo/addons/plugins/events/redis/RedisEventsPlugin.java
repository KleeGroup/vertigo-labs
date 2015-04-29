package io.vertigo.addons.plugins.events.redis;

import io.vertigo.addons.connectors.redis.RedisConnector;
import io.vertigo.addons.events.Event;
import io.vertigo.addons.events.EventBuilder;
import io.vertigo.addons.events.EventListener;
import io.vertigo.addons.impl.events.EventsPlugin;
import io.vertigo.lang.Assertion;

import java.util.UUID;

import javax.inject.Inject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author pchretien
 */
public final class RedisEventsPlugin implements EventsPlugin {
	private final RedisConnector redisConnector;

	@Inject
	public RedisEventsPlugin(final RedisConnector redisConnector) {
		Assertion.checkNotNull(redisConnector);
		//-----
		this.redisConnector = redisConnector;
	}

	@Override
	public void emit(final String channel, final Event event) {
		Assertion.checkArgNotEmpty(channel);
		Assertion.checkNotNull(event);
		//----
		try (final Jedis jedis = redisConnector.getResource()) {
			final Transaction tx = jedis.multi();
			tx.hset("event:" + event.getUuid(), "payload", event.getPayload());
			tx.lpush("events:" + channel + ":pending", "event:" + event.getUuid());
			tx.exec();
		}

	}

	@Override
	public void register(final String channel, final EventListener eventListener) {
		Assertion.checkArgNotEmpty(channel);
		Assertion.checkNotNull(eventListener);
		//----
		new MyListener(channel, eventListener, redisConnector).start();
	}

	private static class MyListener extends Thread {
		private final String channel;
		private final RedisConnector redisConnector;
		private final EventListener eventListener;

		MyListener(final String channel, final EventListener eventListener, final RedisConnector redisConnector) {
			this.channel = channel;
			this.redisConnector = redisConnector;
			this.eventListener = eventListener;
		}

		@Override
		public void run() {
			while (!isInterrupted()) {
				try (final Jedis jedis = redisConnector.getResource()) {
					final String eventUUID = jedis.brpoplpush("events:" + channel + ":pending", "events:done", 10);
					final UUID uuid = UUID.fromString(eventUUID.substring("event:".length()));
					final Event event = new EventBuilder()
							.withUUID(uuid)
							.withPayload(jedis.hget(eventUUID, "payload"))
							.build();
					eventListener.onEvent(event);
				}
			}
		}
	}

	//	@Override
	//	public <S extends DtSubject> List<Comment> getComments(final URI<S> subjectURI) {
	//		final List<Response<Map<String, String>>> responses = new ArrayList<>();
	//		try (final Jedis jedis = redisConnector.getResource()) {
	//			final List<String> uuids = jedis.lrange("comments:" + subjectURI.getKey(), 0, -1);
	//			final Transaction tx = jedis.multi();
	//			for (final String uuid : uuids) {
	//				responses.add(tx.hgetAll("comment:" + uuid));
	//			}
	//			tx.exec();
	//		}
	//		//----- we are using tx to avoid roundtrips
	//		final List<Comment> comments = new ArrayList<>();
	//		for (final Response<Map<String, String>> response : responses) {
	//			final Map<String, String> data = response.get();
	//			if (!data.isEmpty()) {
	//				comments.add(fromMap(data));
	//			}
	//		}
	//		return comments;
	//	}
}
