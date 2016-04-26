package org.haw.lnielsen.gka.graphen.util;

import org.jgrapht.Graph;

public class GraphUtils {
	private GraphUtils() {}
	
	public static <V, E> double calculateGraphWeight(Graph<V, E> graph) {
		double weight = 0;
		for(E edge : graph.edgeSet()) {
			weight += graph.getEdgeWeight(edge);
		}
		return weight;
	}
}
