package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.kruskal;

import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.alg.interfaces.MinimumSpanningTree;

public class JGraphTKruskalSpanningTreeAdapter<V, E> implements SpanningTreeAlgorithm_I<V, E> {
	@Override
	public Graph<V, E> calculateSpanningTree(Graph<V, E> graph, Graph<V, E> spanningTree) {
		MinimumSpanningTree<V, E> algorithm = new KruskalMinimumSpanningTree<>(graph);
		for(E edge : algorithm.getMinimumSpanningTreeEdgeSet()) {
			V source = graph.getEdgeSource(edge);
			V target = graph.getEdgeTarget(edge);
			spanningTree.addVertex(source);
			spanningTree.addVertex(target);
			spanningTree.addEdge(source, target);
			if(spanningTree instanceof WeightedGraph) {
				((WeightedGraph<V, E>)spanningTree).setEdgeWeight(spanningTree.getEdge(source, target), graph.getEdgeWeight(edge));
			}
		}
		return spanningTree;
	}
	
	@Override
	public String toString() {
		return "JGraphT Kruskal";
	}
}
