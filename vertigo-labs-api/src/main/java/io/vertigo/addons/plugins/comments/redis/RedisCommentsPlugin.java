package io.vertigo.addons.plugins.comments.redis;

import io.vertigo.addons.comments.Comment;
import io.vertigo.addons.comments.CommentBuilder;
import io.vertigo.addons.connectors.redis.RedisConnector;
import io.vertigo.addons.impl.comments.CommentEvent;
import io.vertigo.addons.impl.comments.CommentsPlugin;
import io.vertigo.addons.users.VUserProfile;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.model.DtSubject;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.lang.Assertion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * @author pchretien
 */
public final class RedisCommentsPlugin implements CommentsPlugin {
	private final RedisConnector redisConnector;

	@Inject
	public RedisCommentsPlugin(final RedisConnector redisConnector) {
		Assertion.checkNotNull(redisConnector);
		//-----
		this.redisConnector = redisConnector;
	}

	@Override
	public void emit(final CommentEvent commentEvent) {
		try (final Jedis jedis = redisConnector.getResource()) {
			final Comment comment = commentEvent.getComment();
			final UUID uuid = UUID.randomUUID();
			final Transaction tx = jedis.multi();
			tx.hmset("comment:" + uuid, toMap(uuid, comment));
			tx.lpush("comments:" + commentEvent.getSubjectURI().getKey(), uuid.toString());
			tx.exec();
		}

	}

	private static Map<String, String> toMap(final UUID uuid, final Comment comment) {
		final Map<String, String> data = new HashMap<>();
		data.put("author", comment.getAuthor().getKey().toString());
		data.put("msg", comment.getMsg());
		data.put("uuid", uuid.toString());
		//			data.put("creationDate", comment.getCreationDate());
		return data;
	}

	private static Comment fromMap(final Map<String, String> data) {
		final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(VUserProfile.class);

		return new CommentBuilder()
				.withAuthor(new URI<VUserProfile>(dtDefinition, data.get("author")))
				.withUUID(UUID.fromString(data.get("uuid")))
				.withMsg(data.get("msg"))
				.build();
	}

	@Override
	public <S extends DtSubject> List<Comment> getComments(final URI<S> subjectURI) {
		final List<Response<Map<String, String>>> responses = new ArrayList<>();
		try (final Jedis jedis = redisConnector.getResource()) {
			final List<String> uuids = jedis.lrange("comments:" + subjectURI.getKey(), 0, -1);
			final Transaction tx = jedis.multi();
			for (final String uuid : uuids) {
				responses.add(tx.hgetAll("comment:" + uuid));
			}
			tx.exec();
		}
		//----- we are using tx to avoid roundtrips
		final List<Comment> comments = new ArrayList<>();
		for (final Response<Map<String, String>> response : responses) {
			final Map<String, String> data = response.get();
			if (!data.isEmpty()) {
				comments.add(fromMap(data));
			}
		}
		return comments;
	}
}
