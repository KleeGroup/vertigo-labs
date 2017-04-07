package io.vertigo.cluster.test;

import io.vertigo.cluster.ZCluster;
import io.vertigo.cluster.ZClusterBuilder;
import io.vertigo.cluster.ZNode;

public class ZNodeStarter {
	public static void main(final String[] args) {
		int index = Integer.valueOf(args[0]);
		ZClusterBuilder clusterBuilder = new ZClusterBuilder(); 
		for (int i = 1 ; i<args.length ; i=i+2){
			clusterBuilder.withAddress(args[i], Integer.valueOf(args[i+1]));
		}
		ZCluster cluster = clusterBuilder.build();
		
		new ZNode(index, cluster).start();
	}
}
