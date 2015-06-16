package org.haw.lnielsen.gka.graphen.algorithm.euler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
	
	/**
	 * Wählt einen Startknoten für den nächsten Teilkreis aus den bisher erzeugten Teilkreisen.
	 * Dabei wird mit dem letzten Teilkreis der erzeugt wurde angefangen zu suchen,
	 * ob es einen Knoten mit einem Knotengrad größer 0 gibt.
	 * Sollte es noch keine Teilkreise geben, wird ein zufälliger Knoten aus dem
	 * Graphen gewählt.
	 * @param graph Der Graph
	 * @param circuits Die Liste der Teilkreise
	 * @return Der Startknoten des nächsten Teilkreises
	 */
	private V pickStartVertex(UndirectedGraph<V, E> graph, List<List<V>> circuits) {
		if(circuits.isEmpty()) {
			return graph.vertexSet().iterator().next();
		} else {
			for(int i=circuits.size()-1; i>=0; i--) {
				for(V v : circuits.get(i)) {
					if(graph.degreeOf(v) > 0) {
						return v;
					}
				}
			}
			throw new IllegalStateException("Es wurde kein Knoten gefunden der an einem Kreis hängt!");
		}
	}
	
	/**
	 * Baut einen Eulerkreis aus der übergebenen Liste von Kreisen.
	 * Die zurückgegebene Liste enthält den Startknoten nicht nochmal als letzten Knoten.
	 * @param graph Der Graph dem die Kreise entstammen
	 * @param circuits Die Kreise
	 * @return Der ermittelte Eulerkreis als Liste von Knoten
	 */
	private List<V> buildEulerCircuit(UndirectedGraph<V, E> graph, List<List<V>> circuits) {
		List<V> eulerCircuit = new ArrayList<>();
		List<V> firstCircuit = circuits.remove(0);
		buildEulerCircuit_Rec(graph, firstCircuit.remove(0), firstCircuit, circuits, eulerCircuit);
		return eulerCircuit;
	}
	
	/**
	 * Arbeitet rekursiv Teilkreise ab um einen Eulerkreis zu erzeugen.
	 * Die zurückgegebene Liste enthält den Startknoten nicht nochmal als letzten Knoten.
	 * @param graph Der Graph dem die Kreise entstammen
	 * @param start Der Startknoten von dem aus der aktuelle Teilkreis abgearbeitet werden soll
	 * @param currentCircuit Der aktuelle Teilkreis der verarbeitet wird
	 * @param remainingCircuits Die Liste der verbleibenden Teilkreise
	 * @param eulerCircuit Eine Liste von Knoten die den Eulerkreis repräsentieren soll
	 */
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
	
	/**
	 * Gibt den Nachfolgeknoten des aktuellen Knotens in dem übergebenen Kreis zurück.
	 * @param current Der aktuelle Knoten
	 * @param currentCircuit Der Kreis aus dem der Nachfolgeknoten ermittelt werden soll
	 * @return Der Nachfolgeknoten des aktuellen Knotens
	 */
	private V getNextVertex(V current, List<V> currentCircuit) {
		int currentIndex = currentCircuit.indexOf(current);
		int nextIndex    = (currentIndex+1)%currentCircuit.size();
		return currentCircuit.get(nextIndex);
	}
	
	/**
	 * Gibt den ersten Kreis zurück, der den übergebenen Knoten enthält.
	 * @param remainingCircuits Die Liste der Kreise in denen gesucht werden soll
	 * @param vertex Der zu suchende Knoten
	 * @return Der erste Kreis der den Knoten enthält oder {@code null}, wenn keiner den Knoten enthält
	 */
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
