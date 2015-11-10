package io.vertigo.knock.impl.namedentity;

import io.vertigo.lang.Plugin;

import java.util.List;

public interface TokenizerPlugin extends Plugin {
	List<String> tokenize(final String text);
}
