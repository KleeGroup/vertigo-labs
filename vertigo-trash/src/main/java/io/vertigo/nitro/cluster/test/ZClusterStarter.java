package io.vertigo.nitro.cluster.test;

import io.vertigo.nitro.cluster.ZCluster;
import io.vertigo.nitro.cluster.ZClusterBuilder;
import io.vertigo.nitro.cluster.util.ZProcessUtil;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;


public final class ZClusterStarter {
	private static AutoCloseable execProcess(final int index, ZCluster cluster) throws Exception {
		List<String> args  = new ArrayList<>(); 
		args.add(String.valueOf(index)); 
		for (InetSocketAddress address: cluster.getAddresses()){
			args.add(address.getHostName());
			args.add(String.valueOf(address.getPort()));
		}
		return ZProcessUtil.execProcess(ZNodeStarter.class, args.toArray(new String[args.size()]));
	}

	public static void main(final String[] args) throws Exception {
		ZCluster cluster = createCluster();
		
		List<AutoCloseable>  closeables = new ArrayList<>();
		//---
		startCluster(cluster, closeables);
		//---	
		Thread.sleep(5000);
		//---
		closeCluster(closeables);
	}

	private static void startCluster(ZCluster cluster,	List<AutoCloseable> closeables) throws Exception {
		for (int i = 0 ; i< cluster.getAddresses().size(); i++){
			closeables.add(execProcess(i, cluster));
		}
	}
	
	private static void closeCluster(List<AutoCloseable> closeables) throws Exception {
		for (AutoCloseable closeable: closeables){
			closeable.close();
		}
	}

	private static ZCluster createCluster() {
		return new ZClusterBuilder()//
		.withAddress("localhost", 6380)//
		.withAddress("localhost", 6381)//
		.withAddress("localhost", 6382)//
		.withAddress("localhost", 6383)//
		.withAddress("localhost", 6384)//
		.build();
	}
}

