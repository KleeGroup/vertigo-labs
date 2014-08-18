package io.vertigo.nitro.impl.redis;

import io.vertigo.kernel.lang.Assertion;
import io.vertigo.nitro.redis.RedisClient;
import io.vertigo.nitro.redis.RedisManager;

import javax.inject.Inject;
import javax.inject.Named;

public final class RedisManagerImpl implements RedisManager {
	private final String host;
	private final int port;

	@Inject
	public RedisManagerImpl(@Named("host") final String host, @Named("port") final int port) {
		Assertion.checkArgNotEmpty(host);
		//---------------------------------------------------------------------
		this.host = host;
		this.port = port;
	}

	public RedisClient createClient() {
		return new RedisClientImpl(host, port);
	}
}
