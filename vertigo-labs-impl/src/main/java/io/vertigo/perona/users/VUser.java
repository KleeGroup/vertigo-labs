package io.vertigo.perona.users;

import io.vertigo.lang.Option;

import java.util.UUID;

public interface VUser {
	UUID getUId();

	Option<String> getMail();
}
