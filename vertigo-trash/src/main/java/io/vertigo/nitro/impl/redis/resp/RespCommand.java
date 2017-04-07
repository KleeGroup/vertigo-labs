package io.vertigo.nitro.impl.redis.resp;

import java.util.Arrays;

import io.vertigo.lang.Assertion;

public final class RespCommand {
	private final String name;
	private final String[] args;

	RespCommand(final String name, final String... args) {
		Assertion.checkNotNull(name, "name is required");
		Assertion.checkNotNull(args, "args is required, may be empty");
		Arrays.stream(args).forEach(arg -> Assertion.checkNotNull(arg, "arg can not be null"));
		//-------------------------------------------------
		this.name = name.toLowerCase();
		this.args = args;

	}

	public String getName() {
		return name;
	}

	public String[] args() {
		return args;
	}
}
