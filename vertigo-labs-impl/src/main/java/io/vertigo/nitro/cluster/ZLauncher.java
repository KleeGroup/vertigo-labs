package io.vertigo.nitro.cluster;

public class ZLauncher {

	public ZProcess createProcess(final int port) throws Exception {
		final String command = new StringBuilder()//
				.append("java -cp ")//
				.append(System.getProperty("java.class.path"))//
				.append(" io.vertigo.nitro.cluster.ZNode ")//
				.append(port).toString();
		return new ZProcess(command);
	}

	public static void main(final String[] args) throws Exception {
		final ZLauncher launcher = new ZLauncher();
		try (final ZProcess processes1 = launcher.createProcess(6380)) {
			try (final ZProcess process2 = launcher.createProcess(6381)) {
				Thread.sleep(2000);
			}
		}
	}
}
