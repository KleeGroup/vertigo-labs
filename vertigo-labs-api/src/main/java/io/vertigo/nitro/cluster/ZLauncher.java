package io.vertigo.nitro.cluster;

public class ZLauncher {

	public ZProcess createProcess() throws Exception {
		final String command = new StringBuilder()//
				.append("java -cp ")//
				.append(System.getProperty("java.class.path"))//
				.append(" io.vertigo.nitro.cluster.ZNode ")//
				.toString();
		return new ZProcess(command);
	}

	public static void main(final String[] args) throws Exception {
		final ZLauncher launcher = new ZLauncher();
		try (final ZProcess process = launcher.createProcess()) {
			Thread.sleep(2000);
		}
	}
}
