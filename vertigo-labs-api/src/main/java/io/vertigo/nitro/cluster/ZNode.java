package io.vertigo.nitro.cluster;

public final class ZNode extends Thread {
	private static int HEART_BEAT = 100; //ms

	//	private static int TIME_OUT_HB = 3 * HEART_BEAT; //ms

	public enum ZState {
		Follower, Candidate, Leader;
	}

	//private final ZState state = ZState.Follower;

	//
	//	public void put(final String name, final String value) {
	//
	//	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {
			System.out.println("pom pom");
			try {
				Thread.sleep(HEART_BEAT);
			} catch (final InterruptedException e) {
				//
			}
		}
	}

	public static void main(final String[] args) {
		new ZNode().start();
	}
}
