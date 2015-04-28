package io.vertigo.addons.plugins.comments.redis;

import io.vertigo.addons.comments.Comment;
import io.vertigo.addons.comments.CommentBuilder;
import io.vertigo.addons.impl.comments.CommentEvent;
import io.vertigo.addons.impl.comments.CommentsPlugin;
import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.DtSubject;
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
public final class RedisCommentsPlugin implements CommentsPlugin, Activeable {
	private static final int CONNECT_TIMEOUT = 2000;
	private final JedisPool jedisPool;

	@Inject
	public RedisCommentsPlugin(final @Named(
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
	public void emit(final CommentEvent commentEvent) {
		try (final Jedis jedis = jedisPool.getResource()) {
			final Comment comment = commentEvent.getComment();
			final String uuid = UUID.randomUUID().toString();
			final Transaction tx = jedis.multi();

			final Map<String, String> data = new HashMap<>();
			data.put("author", comment.getAuthor().getKey().toString());
			data.put("msg", comment.getMsg());
			//			data.put("creationDate", comment.getCreationDate());
			tx.hmset("comment:" + uuid, data);

			tx.lpush("comments:" + commentEvent.getSubjectURI().getKey(), uuid);
			tx.exec();
		}

	}

	@Override
	public <S extends DtSubject> List<Comment> getComments(final URI<S> subjectURI) {
		final List<Comment> comments = new ArrayList<>();
		final List<String> commentsAsUUID;
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(VUserProfile.class);

		try (final Jedis jedis = jedisPool.getResource()) {
			commentsAsUUID = jedis.lrange("comments:" + subjectURI.getKey(), 0, -1);
			for (final String commentAsUUID : commentsAsUUID) {
				final Map<String, String> data = jedis.hgetAll("comment:" + commentAsUUID);
				if (!data.isEmpty()) {
					final Comment comment = new CommentBuilder()
							.withAuthor(new URI<VUserProfile>(dtDefinition, data.get("author")))
							.withMsg(data.get("msg"))
							.build();

					comments.add(comment);
				}
			}
		}
		return comments;

	}
}
