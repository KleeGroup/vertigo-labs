package io.vertigo.persona.groups;

import io.vertigo.lang.Option;
import io.vertigo.perona.users.VUser;

import java.util.Map;

public interface VGroup {
	Option<String> getMail();

	Map<VUser, VRole> getUsers();

}
