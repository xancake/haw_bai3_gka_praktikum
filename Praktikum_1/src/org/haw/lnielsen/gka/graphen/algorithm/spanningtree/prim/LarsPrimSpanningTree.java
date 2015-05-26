package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.prim;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
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
		Set<V> unspanned = new HashSet<>(graph.vertexSet());
		
		Map<V, VertexAttribute> vertexAttributeMapping = new HashMap<>();
		for(V vertex : unspanned) {
			vertexAttributeMapping.put(vertex, new VertexAttribute());
		}
		
		Queue<V> queue = new PriorityQueue<>(new VertexAttributeComparator(vertexAttributeMapping));
		
		while(!spanningTree.vertexSet().containsAll(graph.vertexSet())) {
			V vertex = unspanned.iterator().next();
			addVertex(vertex, graph, spanningTree, unspanned, vertexAttributeMapping, queue);
			
			while(!queue.isEmpty() && vertexAttributeMapping.get(queue.peek()).weight != Double.POSITIVE_INFINITY) {
				V newVertex = queue.poll();
				E edge      = vertexAttributeMapping.get(newVertex).edge;
				
				// Knoten hinzufügen
				addVertex(newVertex, graph, spanningTree, unspanned, vertexAttributeMapping, queue);
				
				// Kante hinzufügen
				V source = graph.getEdgeSource(edge);
				V target = graph.getEdgeTarget(edge);
				spanningTree.addEdge(source, target);
				if(spanningTree instanceof WeightedGraph) {
					((WeightedGraph<V, E>)spanningTree).setEdgeWeight(spanningTree.getEdge(source, target), graph.getEdgeWeight(edge));
				}
			}
		}
		
		return spanningTree;
	}
	
	/**
	 * Fügt den Knoten dem Spannbaum hinzu und entfernt ihn aus der Liste der noch nicht verarbeiteten
	 * Knoten. Die Kanten des Knoten werden der Warteschlange hinzugefügt, wenn sich einer der
	 * beteiligten Knoten noch nicht im Spannbaum befindet und die Kante noch nicht in der
	 * Warteschlange enthalten ist.
	 * @param vertex Der Knoten
	 * @param graph Der original-Graph
	 * @param spanningTree Der Spannbaum zu dem der Knoten hinzugefügt werden soll
	 * @param unspanned Die Menge der nicht verarbeiteten Knoten
	 * @param queue Die Warteschlange
	 */
	private void addVertex(V vertex, Graph<V, E> graph, Graph<V, E> spanningTree, Set<V> unspanned, Map<V, VertexAttribute> vertexWeightMapping, Queue<V> queue) {
		unspanned.remove(vertex);
		spanningTree.addVertex(vertex);
		for(E edge : graph.edgesOf(vertex)) {
			V source = graph.getEdgeSource(edge);
			V target = graph.getEdgeTarget(edge);
			V other  = vertex.equals(source) ? target : source;
			VertexAttribute otherWithWeight = vertexWeightMapping.get(other);
			if(!spanningTree.containsVertex(other) && otherWithWeight.weight > graph.getEdgeWeight(edge)) {
				queue.remove(other);
				otherWithWeight.weight = graph.getEdgeWeight(edge);
				otherWithWeight.edge   = edge;
				queue.add(other);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Lars Prim";
	}
	
	private class VertexAttribute {
		private E edge;
		private double weight;
		
		public VertexAttribute() {
			this(null, Double.POSITIVE_INFINITY);
		}
		
		public VertexAttribute(E edge, double weight) {
			this.edge   = edge;
			this.weight = weight;
		}
		
		@Override
		public String toString() {
			return String.valueOf(weight);
		}
	}
	
	private class VertexAttributeComparator implements Comparator<V> {
		private Map<V, VertexAttribute> mapping;
		
		public VertexAttributeComparator(Map<V, VertexAttribute> mapping) {
			this.mapping = mapping;
		}
		
		@Override
		public int compare(V o1, V o2) {
			return (int)(mapping.get(o1).weight-mapping.get(o2).weight);
		}
	}
}
