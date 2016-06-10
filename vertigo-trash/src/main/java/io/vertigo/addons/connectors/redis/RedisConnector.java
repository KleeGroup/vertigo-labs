package io.vertigo.addons.connectors.redis;

import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Component;
import io.vertigo.lang.Option;

import javax.inject.Inject;
import javax.inject.Named;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author pchretien
 */
public final class RedisConnector implements Component, Activeable {
	private static final int CONNECT_TIMEOUT = 2000;
	private final JedisPool jedisPool;

	/**
	 * Constructor.
	 * @param redisHost REDIS server host name
	 * @param redisPort REDIS server port
	 * @param redisDatabase REDIS database index 
	 * @param passwordOption password (optional)
	 */
	@Inject
	public RedisConnector(
			@Named("host") final String redisHost,
			@Named("port") final int redisPort,
			@Named("database") final int redisDatabase,
			@Named("password") final Option<String> passwordOption) {
		Assertion.checkArgNotEmpty(redisHost);
		Assertion.checkNotNull(passwordOption);
		Assertion.checkArgument(redisDatabase >= 0 && redisDatabase < 16, "there 16 DBs(0 - 15); your index database '{0}' is not inside this range", redisDatabase);
		//-----
		final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort, CONNECT_TIMEOUT, passwordOption.getOrElse(null), redisDatabase);
		//test
		try (Jedis jedis = jedisPool.getResource()) {
			jedis.ping();
			jedis.flushAll();
		}
	}

	/**
	 * @return Redis resource
	 */
	public Jedis getResource() {
		return jedisPool.getResource();
	}

	/** {@inheritDoc} */
	@Override
	public void start() {
		//
	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		jedisPool.destroy();
	}

}
