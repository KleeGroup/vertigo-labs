package io.vertigo.cluster;

import io.vertigo.lang.Assertion;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ZCluster {
	private final List<InetSocketAddress> addresses;

	ZCluster(final List<InetSocketAddress> addresses) {
		Assertion.checkNotNull(addresses);
		//-----
		this.addresses = Collections.unmodifiableList(new ArrayList<>(addresses));
	}

	public List<InetSocketAddress> getAddresses() {
		return addresses;
	}
}
