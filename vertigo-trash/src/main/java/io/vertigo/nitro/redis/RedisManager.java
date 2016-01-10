package io.vertigo.nitro.redis;

import io.vertigo.lang.Manager;

public interface RedisManager extends Manager {
	RedisClient createClient();
}
