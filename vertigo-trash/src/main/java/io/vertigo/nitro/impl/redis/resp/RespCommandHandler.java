package io.vertigo.nitro.impl.redis.resp;

import java.io.IOException;

@FunctionalInterface
public interface RespCommandHandler {
	void onCommand(RespWriter writer, RespCommand command) throws IOException;
}
