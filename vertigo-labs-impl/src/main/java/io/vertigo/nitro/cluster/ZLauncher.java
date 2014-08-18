package io.vertigo.nitro.cluster;

import io.vertigo.nitro.cluster.util.ZProcessUtil;


public final class ZLauncher {
	private static AutoCloseable execProcess(final int port) throws Exception {
		return ZProcessUtil.execProcess(ZNode.class, String.valueOf(port));
	}

	public static void main(final String[] args) throws Exception {
		try (final AutoCloseable processes1 = execProcess(6380)) {
			try (final AutoCloseable process2 = execProcess(6381)) {
				try (final AutoCloseable process3 = execProcess(6382)) {
					Thread.sleep(20000000);
				}
			}
		}
	}
}

