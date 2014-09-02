package io.vertigo.nitro.impl.redis;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Option;
import io.vertigo.nitro.redis.RedisClient;
import io.vertigo.nitro.redis.RedisManager;

import javax.inject.Inject;
import javax.inject.Named;

public final class RedisManagerImpl implements RedisManager {
	private final String host;
	private final int port;
	private final Option<String> password;

	@Inject
	public RedisManagerImpl(@Named("host") final String host, @Named("port") final int port, @Named("password") final Option<String> password) {
		Assertion.checkArgNotEmpty(host);
		Assertion.checkNotNull(password);
		//---------------------------------------------------------------------
		this.host = host;
		this.port = port;
		this.password=password;
	}

	public RedisClient createClient() {
		final RedisClient redisClient = new RedisClientImpl(host, port);
		if (password.isDefined()){
			redisClient.auth(password.get());
		}
		return redisClient;
	}
}
