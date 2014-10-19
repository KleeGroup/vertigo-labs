package io.vertigo.nitro.cluster;

import io.vertigo.lang.Assertion;
import io.vertigo.nitro.impl.redis.resp.RespClient;
import io.vertigo.nitro.impl.redis.resp.RespCommand;
import io.vertigo.nitro.impl.redis.resp.RespCommandHandler;
import io.vertigo.nitro.impl.redis.resp.RespProtocol;
import io.vertigo.nitro.impl.redis.resp.RespServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public final class ZNode extends Thread {
	private static int HEART_BEAT = 500; //ms

	//	private static int TIME_OUT_HB = 3 * HEART_BEAT; //ms

	public enum ZState {
		Follower, Candidate, Leader;
	}

	//private final Map<InetSocketAddress, >
	//	private ZState state = ZState.Follower;
	private final List<RespClient> connections = new ArrayList<>();
	//
	//	public void put(final String name, final String value) {
	//
	//	}
	private final RespServer respServer;
	private final int index;

	public ZNode(final int index, final ZCluster cluster) {
		System.out.println("create znode [" + index + "]");
		Assertion.checkNotNull(cluster);
		//---------------------------------------------------------------------
		this.index = index;
		if (index == 0) {
			//	state = ZState.Leader;
			for (int j = 1; j < cluster.getAddresses().size(); j++) {
				final InetSocketAddress address = cluster.getAddresses().get(j);
				System.out.println("starting cx[" + index + "] :" + address);
				connections.add(new RespClient(address.getHostName(), address.getPort()));
			}
		}
		final int port = cluster.getAddresses().get(index).getPort();
		//---
		respServer = createRespServer(port);
		new Thread(respServer).start();
	}

	@Override
	public void run() {
		System.out.println("start [" + index + "]");
		while (!isInterrupted()) {
			try {
				Thread.sleep(HEART_BEAT);
				//---
				System.out.println(">>>>>>>HB  [" + index + "]");
				for (final RespClient respClient : connections) {
					final String response = respClient.execString("ping");
					System.out.println(">> [" + index + "]" + respClient + ": " + response);
				}
				//				try (final RespClient respClient = new RespClient("localhost", 6380)) {
				//					final String response = respClient.execString("ping");
				//					System.out.println(">>>" + response + " from " + respClient);
				//				}
				//
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
