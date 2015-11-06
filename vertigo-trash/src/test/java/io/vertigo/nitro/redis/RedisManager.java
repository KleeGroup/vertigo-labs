package io.vertigo.nitro.redis;

import io.vertigo.lang.Component;

public interface RedisManager extends Component {
	RedisClient createClient();
}
