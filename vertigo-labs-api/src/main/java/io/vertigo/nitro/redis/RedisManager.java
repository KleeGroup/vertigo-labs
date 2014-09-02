package io.vertigo.nitro.redis;

import io.vertigo.core.component.Manager;

public interface RedisManager extends Manager {
	RedisClient createClient();
}
