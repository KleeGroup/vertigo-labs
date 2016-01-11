package io.vertigo.nitro.impl.redis.resp;

import java.io.IOException;

public interface RespCommandHandler {
	void onCommand(RespWriter writer, RespCommand command) throws IOException;
}
