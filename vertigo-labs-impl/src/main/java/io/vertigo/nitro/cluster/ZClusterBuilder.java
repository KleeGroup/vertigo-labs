package io.vertigo.nitro.cluster;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ZClusterBuilder implements Builder<ZCluster>{
	private final List<InetSocketAddress> addresses = new ArrayList<>();

	public ZClusterBuilder  withAddress(final String host, final int port){
		Assertion.checkArgNotEmpty(host);
		//---------------------------------------------------------------------
		final InetSocketAddress address = new InetSocketAddress(host, port);
		addresses.add(address);
		return this;
	}
	public ZCluster build() {
		return new ZCluster(addresses);
	}
}
