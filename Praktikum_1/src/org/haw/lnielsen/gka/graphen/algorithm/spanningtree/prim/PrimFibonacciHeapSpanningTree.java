package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.prim;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

/**
 * Implementation des Prim-Algorithmus mit einem Fibonacci-Heap zum Realisieren der Prioritätswarteschlange.
 * 
 * @author Lars Nielsen
 * @author Jennifer Momsen
 */
public class PrimFibonacciHeapSpanningTree<V, E> implements SpanningTreeAlgorithm_I<V, E> {
	@Override
	public Graph<V, E> calculateSpanningTree(Graph<V, E> graph, Graph<V, E> spanningTree) {
		Set<V> vertices = new HashSet<>(graph.vertexSet());
		
		// Initialisieren des Fibonacci Heaps
		Map<V, FibonacciHeapNode<V>> heapNodeMapping = new HashMap<>();
		FibonacciHeap<V> heap = new FibonacciHeap<>();
		for(V vertex : graph.vertexSet()) {
			FibonacciHeapNode<V> node = new FibonacciHeapNode<>(vertex);
			heapNodeMapping.put(vertex, node);
			heap.insert(node, Double.POSITIVE_INFINITY);
		}
		
		while(!spanningTree.vertexSet().containsAll(graph.vertexSet())) {
			V vertex = vertices.iterator().next();
			addVertex(vertex, graph, spanningTree, vertices, heapNodeMapping, heap);
			
			while(heap.min().getKey() != Double.POSITIVE_INFINITY) {
				FibonacciHeapNode<V> node = heap.removeMin();
				V newVertex = node.getData();
				
				// Ermitteln der Kante, mit der der Knoten dem Spannbaum hinzugefügt werden soll
				E newEdge = null;
				for(E edge : graph.edgesOf(newVertex)) {
					V source = graph.getEdgeSource(edge);
					V target = graph.getEdgeTarget(edge);
					V other  = newVertex.equals(source) ? target : source;
					if(spanningTree.containsVertex(other) && (newEdge==null || graph.getEdgeWeight(newEdge) > graph.getEdgeWeight(edge))) {
						newEdge = edge;
					}
				}
				
				// Knoten hinzufügen
				addVertex(newVertex, graph, spanningTree, vertices, heapNodeMapping, heap);
				
				// Kante hinzufügen
				V source = graph.getEdgeSource(newEdge);
				V target = graph.getEdgeTarget(newEdge);
				spanningTree.addEdge(source, target);
				if(spanningTree instanceof WeightedGraph) {
					((WeightedGraph<V, E>)spanningTree).setEdgeWeight(spanningTree.getEdge(source, target), graph.getEdgeWeight(newEdge));
				}
			}
		}
		
		return spanningTree;
	}
	
	/**
	 * Fügt einen Knoten dem Spannbaum hinzu und entfernt ihn aus der Menge der nicht verarbeiteten Knoten.
	 * Dabei wird auch die Bewertung der benachbarten Knoten im {@link FibonacciHeap} angepasst, wenn der
	 * benachbarte Knoten noch nicht im Spannbaum enthalten ist und das Kantengewicht niedriger als die
	 * bisherige Bewertung ist.
	 * @param vertex Der hinzuzufügende Knoten
	 * @param graph Der original Graph
	 * @param spanningTree Der Spannbaum des Graphen
	 * @param vertices Die Menge der nicht verarbeiteten Knoten
	 * @param heapNodeMapping Das Mapping der Knoten zu ihren entsprechenden {@link FibonacciHeapNode}s
	 * @param heap Der {@link FibonacciHeap}
	 */
	private void addVertex(V vertex, Graph<V, E> graph, Graph<V, E> spanningTree, Set<V> vertices, Map<V, FibonacciHeapNode<V>> heapNodeMapping, FibonacciHeap<V> heap) {
		vertices.remove(vertex);
		spanningTree.addVertex(vertex);
		for(E edge : graph.edgesOf(vertex)) {
			V source = graph.getEdgeSource(edge);
			V target = graph.getEdgeTarget(edge);
			V other  = vertex.equals(source) ? target : source;
			FibonacciHeapNode<V> otherNode = heapNodeMapping.get(other);
			if(!spanningTree.containsVertex(other) && otherNode.getKey() > graph.getEdgeWeight(edge)) {
				heap.decreaseKey(otherNode, graph.getEdgeWeight(edge));
			}
		}
	}
	
	@Override
	public String toString() {
		return "Prim Fibonacci Heap";
	}
}
