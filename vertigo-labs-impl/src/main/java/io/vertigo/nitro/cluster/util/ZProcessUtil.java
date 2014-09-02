package io.vertigo.nitro.cluster.util;

import io.vertigo.core.lang.Assertion;

/**
 * Execute a new process from a class and some args, and redirect output and err streams to console.
 * This process is an autocloseable, so it can be killed using try-with-resource pattern.
 * 
 * @author pchretien
 */
public final class ZProcessUtil {

	public static AutoCloseable execProcess(final Class clazz, final String... args) throws Exception {
		Assertion.checkNotNull(clazz);
		Assertion.checkNotNull(args);
		//---------------------------------------------------------------------
		final StringBuilder commandBuilder = new StringBuilder()//
		.append("java -cp ")//
		.append(System.getProperty("java.class.path"))//
		.append(" ")//
		.append(clazz.getCanonicalName());

		for (final String arg : args) {
			commandBuilder//
			.append(" ")//
			.append(arg);
		}

		return new ZProcess(commandBuilder.toString());
	}
}
