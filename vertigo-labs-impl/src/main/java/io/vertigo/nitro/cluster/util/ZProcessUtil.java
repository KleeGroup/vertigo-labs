package io.vertigo.nitro.cluster.util;

import io.vertigo.kernel.lang.Assertion;


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

		for(String arg : args){
			commandBuilder//
			.append(" ")//
			.append(arg);
		}
		
		return new ZProcess(commandBuilder.toString());
	}
}

