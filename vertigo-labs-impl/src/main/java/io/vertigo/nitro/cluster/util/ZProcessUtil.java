package io.vertigo.nitro.cluster.util;


public final class ZProcessUtil {
	
	public static AutoCloseable execProcess(final Class clazz, final String... args) throws Exception {
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

