package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.kruskal;

import java.util.PriorityQueue;
import java.util.Queue;

import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.haw.lnielsen.gka.graphen.util.compare.EdgeWeightComparator;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

/**
 * Implementation des Kruskal-Spanning-Tree-Algorithmus.
 * 
 * <p>Laufzeit: O(|E|*Breitensuche + |V|), also O(|E|*Breitensuche)
 * 
 * @author Lars Nielsen
 */
public class LarsKruskalSpanningTree<V, E> implements SpanningTreeAlgorithm_I<V, E> {
	@Override
	public Graph<V, E> calculateSpanningTree(Graph<V, E> graph, Graph<V, E> spanningTree) {
		for(V vertex : graph.vertexSet()) {
			spanningTree.addVertex(vertex);
		}
		
		Queue<E> edges = new PriorityQueue<>(graph.edgeSet().size(), new EdgeWeightComparator<E>(graph));
		edges.addAll(graph.edgeSet());
		while(spanningTree.edgeSet().size() < spanningTree.vertexSet().size()-1 && !edges.isEmpty()) {
			E edge = edges.poll();
			V edgeSource = graph.getEdgeSource(edge);
			V edgeTarget = graph.getEdgeTarget(edge);
			if(!producesCircle(graph, spanningTree, edge)) {
				spanningTree.addEdge(edgeSource, edgeTarget);
				if(spanningTree instanceof WeightedGraph) {
					((WeightedGraph<V, E>)spanningTree).setEdgeWeight(spanningTree.getEdge(edgeSource, edgeTarget), graph.getEdgeWeight(edge));
				}
			}
		}
		
		return spanningTree;
	}
	
	/**
	 * Prüft, ob die übergebene Kante einen Kreis erzeugen würde, wenn sie dem Spannbaum-Graphen
	 * hinzugefügt werden würde.
	 * @param graph Der ursprüngliche Graph
	 * @param spanningTree Der Spannbaum des Graphen
	 * @param edge Die Kante die dem Spannbaum hinzugefügt werden soll
	 * @return {@code true}, wenn die Kante einen Kreis erzeugen würde, ansonsten {@code false}
	 */
	private boolean producesCircle(Graph<V, E> graph, Graph<V, E> spanningTree, E edge) {
		V source = graph.getEdgeSource(edge);
		V target = graph.getEdgeTarget(edge);
		if(spanningTree.containsVertex(source) && spanningTree.containsVertex(target)) {
			BreadthFirstIterator<V, E> spanningTreeIterator = new BreadthFirstIterator<>(spanningTree, source);
			while(spanningTreeIterator.hasNext()) {
				V next = spanningTreeIterator.next();
				if(target.equals(next)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Lars Kruskal";
	}
}
