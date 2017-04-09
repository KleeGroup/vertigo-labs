package io.vertigo.redis.impl.resp;

import java.io.IOException;

@FunctionalInterface
public interface RespCommandHandler {
	void onCommand(RespWriter writer, RespCommand command) throws IOException;
}