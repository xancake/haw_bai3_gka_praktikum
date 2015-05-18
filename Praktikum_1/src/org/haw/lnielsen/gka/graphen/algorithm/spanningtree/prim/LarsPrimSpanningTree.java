package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.prim;

import java.util.HashSet;
import java.util.Set;

import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
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
		Set<V> vertices = new HashSet<>(graph.vertexSet());
		
		V startVertex = vertices.iterator().next();
		spanningTree.addVertex(startVertex);
		vertices.remove(startVertex);
		
		while(!vertices.isEmpty()) {
			E nextEdge = getNextEdge(graph, spanningTree);
			
			if(nextEdge == null) {
				// Alle Knoten die jetzt noch in 'vertices' stecken, sind nicht mit den Knoten aus dem Spannbaum verbunden
				break;
			}
			
			V source = graph.getEdgeSource(nextEdge);
			V target = graph.getEdgeTarget(nextEdge);
			spanningTree.addVertex(source);
			spanningTree.addVertex(target);
			spanningTree.addEdge(source, target);
			if(spanningTree instanceof WeightedGraph) {
				((WeightedGraph<V, E>)spanningTree).setEdgeWeight(spanningTree.getEdge(source, target), graph.getEdgeWeight(nextEdge));
			}
			vertices.remove(source);
			vertices.remove(target);
		}
		
		return spanningTree;
	}
	
	/**
	 * Ermittelt die nächste zu verarbeitende Kante. Die nächste Kante ist über eine Kante
	 * mit den Knoten aus dem Spannbaum verbunden und hat das niedrigste Gewicht gegenüber
	 * anderen möglichen Kanditaten.
	 * @param graph Der ursprüngliche Graph
	 * @param spanningTree Der Spannbaum des Graphen
	 * @return Die nächste zu verarbeitende Kante
	 */
	private E getNextEdge(Graph<V, E> graph, Graph<V, E> spanningTree) {
		Set<V> spanningTreeVertices = spanningTree.vertexSet();
		
		E nextEdge = null;
		for(V vertex : spanningTreeVertices) {
			for(E edge : graph.edgesOf(vertex)) {
				V edgeSource = graph.getEdgeSource(edge);
				V edgeTarget = graph.getEdgeTarget(edge);
				V other = (vertex == edgeSource ? edgeTarget : edgeSource);
				
				if(!spanningTree.containsVertex(other) && !spanningTree.containsEdge(edgeSource, edgeTarget)) {
					if(nextEdge == null || graph.getEdgeWeight(edge) < graph.getEdgeWeight(nextEdge)) {
						nextEdge = edge;
					}
				}
			}
		}
		
		return nextEdge;
	}
	
	@Override
	public String toString() {
		return "Lars Prim";
	}
}
