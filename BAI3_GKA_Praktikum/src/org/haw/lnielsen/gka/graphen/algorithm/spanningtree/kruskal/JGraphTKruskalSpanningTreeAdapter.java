package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.kruskal;

import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.JGraphTMinimumSpanningTreeAdapter_A;
import org.jgrapht.Graph;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.alg.interfaces.MinimumSpanningTree;

public class JGraphTKruskalSpanningTreeAdapter<V, E>  extends JGraphTMinimumSpanningTreeAdapter_A<V, E> {
	@Override
	protected MinimumSpanningTree<V, E> createAlgorithm(Graph<V, E> graph) {
		return new KruskalMinimumSpanningTree<>(graph);
	}
	
	@Override
	public String toString() {
		return "JGraphT Kruskal";
	}
}
