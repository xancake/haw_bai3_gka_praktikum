package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.prim;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.haw.lnielsen.gka.graphen.util.compare.EdgeWeightComparator;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

/**
 * Implementation des Prim-Spanning-Tree-Algorithmus
 * 
 * @author Lars Nielsen
 */
public class LarsPrimSpanningTree<V, E> implements SpanningTreeAlgorithm_I<V, E> {
	@Override
	public Graph<V, E> calculateSpanningTree(Graph<V, E> graph, Graph<V, E> spanningTree) {
		if(!spanningTree.vertexSet().isEmpty() || !spanningTree.edgeSet().isEmpty()) {
			throw new IllegalArgumentException("Der Spannbaum muss leer sein!");
		}
		
		Set<V> vertices = new HashSet<>(graph.vertexSet());
		Queue<E> edges = new PriorityQueue<>(graph.edgeSet().size(), new EdgeWeightComparator<E>(graph));
		
		while(!spanningTree.vertexSet().containsAll(graph.vertexSet())) {
			V vertex = vertices.iterator().next();
			addVertex(vertex, graph, spanningTree, vertices, edges);
			
			while(!edges.isEmpty()) {
				E edge = edges.poll();
				V source = graph.getEdgeSource(edge);
				V target = graph.getEdgeTarget(edge);
				
				// Beide Knoten sind schon im Spannbaum, dann braucht die Kante nicht mehr betrachtet werden,
				// da sie aufgrund der niedrigeren Priorität von einer alternativen Route ausgestochen wurde
				if(spanningTree.containsVertex(source) && spanningTree.containsVertex(target)) {
					continue;
				}
				
				// Nur einer der beiden Knoten ist noch nicht im Spannbaum
				// Dann muss der neue Knoten und die Kante dem Spannbaum hinzugefügt werden
				// und alle Kanten der Queue hinzugefügt werden.
				if(spanningTree.containsVertex(source) ^ spanningTree.containsVertex(target)) {
					V newVertex  = spanningTree.containsVertex(source) ? target : source;
					
					addVertex(newVertex, graph, spanningTree, vertices, edges);
					spanningTree.addEdge(source, target);
					if(spanningTree instanceof WeightedGraph) {
						((WeightedGraph<V, E>)spanningTree).setEdgeWeight(spanningTree.getEdge(source, target), graph.getEdgeWeight(edge));
					}
				}
			}
		}
		
		return spanningTree;
	}
	
	/**
	 * Fügt den Knoten dem Spannbaum hinzu unt entfernt ihn aus der Liste der noch nicht verarbeiteten
	 * Knoten. Die Kanten des Knoten werden der Warteschlange hinzugefügt.
	 * @param vertex Der Knoten
	 * @param graph Der original-Graph
	 * @param spanningTree Der Spannbaum zu dem der Knoten hinzugefügt werden soll
	 * @param vertices Die Menge der nicht verarbeiteten Knoten
	 * @param edges Die Warteschlange der Kanten
	 */
	private void addVertex(V vertex, Graph<V, E> graph, Graph<V, E> spanningTree, Set<V> vertices, Queue<E> edges) {
		vertices.remove(vertex);
		spanningTree.addVertex(vertex);
		for(E edge : graph.edgesOf(vertex)) {
			if(!spanningTree.containsEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge)) && !edges.contains(edge)) {
				edges.add(edge);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Lars Prim";
	}
}
