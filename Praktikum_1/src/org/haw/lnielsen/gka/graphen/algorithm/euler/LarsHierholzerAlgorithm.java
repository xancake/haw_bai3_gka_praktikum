package org.haw.lnielsen.gka.graphen.algorithm.euler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.graph.GraphPathImpl;
import org.jgrapht.graph.UndirectedSubgraph;

/**
 * Implementation des Hierholzer-Algorithmus zum finden von Eulerkreisen.
 * 
 * @author Lars Nielsen
 */
public class LarsHierholzerAlgorithm<V, E> implements EulerAlgorithm_I<V, E> {
	@Override
	public GraphPath<V, E> findEulerTour(Graph<V, E> graph) {
		if(!(graph instanceof UndirectedGraph)) {
			throw new IllegalArgumentException("Der Algorithmus unterstützt derzeit nur ungerichtete Graphen!");
		}
		if(!EulerianCircuit.isEulerian((UndirectedGraph<V, E>)graph)) {
			return null;
		}
		
		UndirectedGraph<V, E> subgraph = new UndirectedSubgraph<>((UndirectedGraph<V, E>)graph, null, null);
		
		List<List<V>> circuits = new LinkedList<>();
		while(subgraph.edgeSet().size() > 0) {
			List<V> circuit = new LinkedList<>();
			
			V start = pickStartVertex(subgraph, circuits);
			V current = start;
			do {
				circuit.add(current);
				E edge = subgraph.edgesOf(current).iterator().next();
				V source = subgraph.getEdgeSource(edge);
				V target = subgraph.getEdgeTarget(edge);
				V other  = current.equals(source) ? target : source;
				subgraph.removeEdge(edge);
				current = other;
			} while(!start.equals(current));
			circuit.add(current);
			
			circuits.add(circuit);
		}
		
		List<V> eulerCircuit = buildEulerCircuit((UndirectedGraph<V, E>)graph, circuits);
		
		List<E> edgeList = new ArrayList<>();
		for(int i=0; i<eulerCircuit.size()-1; i++) {
			edgeList.add(graph.getEdge(eulerCircuit.get(i), eulerCircuit.get(i+1)));
		}
		edgeList.add(graph.getEdge(eulerCircuit.get(eulerCircuit.size()-1), eulerCircuit.get(0)));
		return new GraphPathImpl<>(graph, eulerCircuit.get(0), eulerCircuit.get(0), edgeList, 0);
	}
	
	private V pickStartVertex(UndirectedGraph<V, E> subgraph, List<List<V>> circuits) {
		if(circuits.isEmpty()) {
			return subgraph.vertexSet().iterator().next();
		} else {
			for(int i=circuits.size()-1; i>=0; i--) {
				for(V v : circuits.get(i)) {
					if(subgraph.degreeOf(v) > 0) {
						return v;
					}
				}
			}
			throw new IllegalStateException("Es wurde kein Knoten gefunden der an einem Kreis hängt!");
		}
	}
	
	private List<V> buildEulerCircuit(UndirectedGraph<V, E> graph, List<List<V>> remainingCircuits) {
		List<V> eulerCircuit = new ArrayList<>();
		List<V> firstCircuit = remainingCircuits.remove(0);
		buildEulerCircuit_Rec(graph, firstCircuit.remove(0), firstCircuit, remainingCircuits, eulerCircuit);
		return eulerCircuit;
	}
	
	private void buildEulerCircuit_Rec(UndirectedGraph<V, E> graph, V start, List<V> currentCircuit, List<List<V>> remainingCircuits, List<V> eulerCircuit) {
		V current = start;
		do {
			eulerCircuit.add(current);
			
			List<V> circuitContainingVertex = getCircuitContainingVertex(remainingCircuits, current);
			if(circuitContainingVertex != null) {
				remainingCircuits.remove(circuitContainingVertex);
				V matchingVertex = circuitContainingVertex.remove(circuitContainingVertex.indexOf(current));
				buildEulerCircuit_Rec(graph, getNextVertex(matchingVertex, circuitContainingVertex), circuitContainingVertex, remainingCircuits, eulerCircuit);
			}
			
			V next = getNextVertex(current, currentCircuit);
			current = next;
		} while(!start.equals(current));
	}
	
	private V getNextVertex(V current, List<V> currentCircuit) {
		int currentIndex = currentCircuit.indexOf(current);
		int nextIndex    = (currentIndex+1)%currentCircuit.size();
		return currentCircuit.get(nextIndex);
	}
	
	private List<V> getCircuitContainingVertex(List<List<V>> remainingCircuits, V vertex) {
		for(List<V> circuit : remainingCircuits) {
			for(V v : circuit) {
				if(v.equals(vertex)) {
					return circuit;
				}
			}
		}
		return null;
	}
}
