package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.prim;

import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.JGraphTMinimumSpanningTreeAdapter_A;
import org.jgrapht.Graph;
import org.jgrapht.alg.PrimMinimumSpanningTree;
import org.jgrapht.alg.interfaces.MinimumSpanningTree;

public class JGraphTPrimSpanningTreeAdapter<V, E> extends JGraphTMinimumSpanningTreeAdapter_A<V, E> {
	@Override
	protected MinimumSpanningTree<V, E> createAlgorithm(Graph<V, E> graph) {
		return new PrimMinimumSpanningTree<>(graph);
	}
	
	@Override
	public String toString() {
		return "JGraphT Prim";
	}
}
