package io.vertigo.redis.impl;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import io.vertigo.lang.Assertion;
import io.vertigo.redis.RedisClient;
import io.vertigo.redis.RedisManager;

public final class RedisManagerImpl implements RedisManager {
	private final String host;
	private final int port;
	private final Optional<String> password;

	@Inject
	public RedisManagerImpl(@Named("host") final String host, @Named("port") final int port, @Named("password") final Optional<String> password) {
		Assertion.checkArgNotEmpty(host);
		Assertion.checkNotNull(password);
		//---------------------------------------------------------------------
		this.host = host;
		this.port = port;
		this.password = password;
	}

	@Override
	public RedisClient createClient() {
		final RedisClient redisClient = new RedisClientImpl(host, port);
		if (password.isPresent()) {
			redisClient.auth(password.get());
		}
		return redisClient;
	}
}
