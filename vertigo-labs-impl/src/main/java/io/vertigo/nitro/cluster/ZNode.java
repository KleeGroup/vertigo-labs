package io.vertigo.nitro.cluster;

import io.vertigo.nitro.impl.redis.resp.RespClient;
import io.vertigo.nitro.impl.redis.resp.RespCommand;
import io.vertigo.nitro.impl.redis.resp.RespCommandHandler;
import io.vertigo.nitro.impl.redis.resp.RespProtocol;
import io.vertigo.nitro.impl.redis.resp.RespServer;

import java.io.IOException;
import java.io.OutputStream;

public final class ZNode extends Thread {
	private static int HEART_BEAT = 500; //ms

	//	private static int TIME_OUT_HB = 3 * HEART_BEAT; //ms

	public enum ZState {
		Follower, Candidate, Leader;
	}

	//private final ZState state = ZState.Follower;

	//
	//	public void put(final String name, final String value) {
	//
	//	}
	private final RespServer respServer;

	public ZNode(final int index, ZCluster cluster) {
		int port = cluster.getAddresses().get(index).getPort();
		//---
		respServer = createRespServer(port);
		new Thread(respServer).start();
	}
	@Override
	public void run() {
		while (!isInterrupted()) {
			System.out.println("pom pom");
			try {
				Thread.sleep(HEART_BEAT);
				try (final RespClient respClient = new RespClient("localhost", 6380)) {
					final String response = respClient.execString("ping");
					System.out.println(">>>" + response + " from "+respClient);
				}
			} catch (final InterruptedException e) {
				//
			}
		}
	}

	private static RespServer createRespServer(final int port) {
		return new RespServer(port, new RespCommandHandler() {
			public void onCommand(final OutputStream out, final RespCommand command) throws IOException {
				switch (command.getName().toLowerCase()) {
					case "ping":
						RespProtocol.writeSimpleString(out, "PONG");
						break;
					default:
						RespProtocol.writeError(out, "RESP Command unknown : " + command.getName());
				}
			}
		});
	}
}
