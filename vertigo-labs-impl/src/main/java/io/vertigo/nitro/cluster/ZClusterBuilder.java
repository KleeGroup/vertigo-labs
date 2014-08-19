package io.vertigo.nitro.cluster;

import io.vertigo.kernel.lang.Assertion;
import io.vertigo.kernel.lang.Builder;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ZClusterBuilder implements Builder<ZCluster>{
	private final List<InetSocketAddress> addresses = new ArrayList<>();
	
	public ZClusterBuilder  withAddress(String host, int port){
		Assertion.checkArgNotEmpty(host);
		//---------------------------------------------------------------------
		InetSocketAddress address = new InetSocketAddress(host, port);
		addresses.add(address);
		return this;
	}
	public ZCluster build() {
		return new ZCluster(addresses);
	}
}
