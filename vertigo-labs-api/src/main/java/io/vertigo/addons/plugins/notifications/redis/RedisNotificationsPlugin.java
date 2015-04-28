package io.vertigo.addons.plugins.notifications.redis;

import io.vertigo.addons.impl.notifications.NotificationEvent;
import io.vertigo.addons.impl.notifications.NotificationsPlugin;
import io.vertigo.addons.notifications.Notification;
import io.vertigo.addons.notifications.NotificationBuilder;
import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

/**
 * @author pchretien
 */
public final class RedisNotificationsPlugin implements NotificationsPlugin, Activeable {
	private static final int CONNECT_TIMEOUT = 2000;
	private final JedisPool jedisPool;

	//	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	//private final int readTimeout;

	@Inject
	public RedisNotificationsPlugin(final @Named(
			"host") String redisHost,
			final @Named("port") int redisPort,
			final @Named("password") Option<String> passwordOption) {
		//			final @Named("timeoutSeconds") int timeoutSeconds) {
		Assertion.checkArgNotEmpty(redisHost);
		Assertion.checkNotNull(passwordOption);
		//-----
		//this.readTimeout = timeoutSeconds;
		final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//jedisPoolConfig.setMaxActive(10);
		if (passwordOption.isDefined()) {
			jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort, CONNECT_TIMEOUT, passwordOption.get());
		} else {
			jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort, CONNECT_TIMEOUT);
		}
		//test
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.ping();
			jedis.flushAll();
		}
	}

	@Override
	public void start() {
		//
	}

	@Override
	public void stop() {
		jedisPool.destroy();
	}

	@Override
	public void emit(final NotificationEvent notificationEvent) {
		try (final Jedis jedis = jedisPool.getResource()) {
			final Notification notification = notificationEvent.getNotification();
			final String uuid = UUID.randomUUID().toString();
			final Transaction tx = jedis.multi();

			final Map<String, String> data = new HashMap<>();
			data.put("sender", notification.getSender().getKey().toString());
			data.put("title", notification.getTitle());
			data.put("msg", notification.getMsg());
			tx.hmset("notif:" + uuid, data);
			if (notification.getTTLInSeconds() > 0) {
				tx.expire("notif:" + uuid, notification.getTTLInSeconds());
			}
			for (final URI<VUserProfile> userProfileURI : notificationEvent.getToUserProfileURIs()) {
				//On publie la notif
				tx.lpush("notifs:" + userProfileURI.getKey(), uuid);
			}
			tx.exec();
		}

	}

	@Override
	public List<Notification> getCurrentNotifications(final URI<VUserProfile> userProfileURI) {
		//		final List<String> notificationsAsJson;
		//		try (final Jedis jedis = jedisPool.getResource()) {
		//			notificationsAsJson = jedis.lrange("notifs:" + userProfileURI.getKey(), 0, -1);
		//		}
		final List<Notification> notifications = new ArrayList<>();
		//		for (final String notificationAsJson : notificationsAsJson) {
		//			notifications.add(GSON.fromJson(notificationAsJson, Notification.class));
		//		}
		//		return notifications;
		final List<String> notificationsAsUUID;
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(VUserProfile.class);

		try (final Jedis jedis = jedisPool.getResource()) {
			notificationsAsUUID = jedis.lrange("notifs:" + userProfileURI.getKey(), 0, -1);
			for (final String notificationAsUUID : notificationsAsUUID) {
				final Map<String, String> data = jedis.hgetAll("notif:" + notificationAsUUID);
				if (!data.isEmpty()) {
					final Notification notification = new NotificationBuilder()
							.withMsg(data.get("msg"))
							.withTitle(data.get("title"))
							.withSender(new URI<VUserProfile>(dtDefinition, data.get("sender")))
							.build();

					notifications.add(notification);
				}
			}
		}
		return notifications;

	}
}
