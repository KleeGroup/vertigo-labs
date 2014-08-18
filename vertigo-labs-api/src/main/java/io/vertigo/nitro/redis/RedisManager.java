package io.vertigo.nitro.redis;

import io.vertigo.kernel.component.Manager;

public interface RedisManager extends Manager {
	RedisClient createClient();
}
