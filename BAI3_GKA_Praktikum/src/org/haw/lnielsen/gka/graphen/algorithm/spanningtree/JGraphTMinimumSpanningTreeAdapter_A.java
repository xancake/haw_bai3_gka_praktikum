package org.haw.lnielsen.gka.graphen.algorithm.spanningtree;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.interfaces.MinimumSpanningTree;

/**
 * Eine Adapterklasse für Spannbaum-Algorithmen die das JGraphT {@link MinimumSpanningTree}-Interface
 * erfüllen. Die Kanten die der {@link MinimumSpanningTree}-Algorithmus ermittelt hat werden (mitsamt
 * Knoten) in einen Graphen hinzugefügt, der dann den Spannbaum des ursprünglichen Graphen
 * repräsentiert.
 * 
 * @author Lars Nielsen
 */
public abstract class JGraphTMinimumSpanningTreeAdapter_A<V, E> implements SpanningTreeAlgorithm_I<V, E> {
	@Override
	public Graph<V, E> calculateSpanningTree(Graph<V, E> graph, Graph<V, E> spanningTree) {
		MinimumSpanningTree<V, E> algorithm = createAlgorithm(graph);
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
	
	/**
	 * Erzeugt den tatsächlichen Algorithmus zum Berechnen des minimalen Spannbaums.
	 * @param graph Der Graph auf dem der Algorithmus ausgeführt werden soll
	 * @return Der Algorithmus
	 */
	protected abstract MinimumSpanningTree<V, E> createAlgorithm(Graph<V, E> graph);
}
