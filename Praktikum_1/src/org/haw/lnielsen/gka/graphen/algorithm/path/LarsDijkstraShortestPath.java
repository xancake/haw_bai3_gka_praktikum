package org.haw.lnielsen.gka.graphen.algorithm.path;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphPathImpl;

import org.haw.lnielsen.gka.graphen.Knoten;

public class LarsDijkstraShortestPath implements ShortestPath_I {
	@Override
	public <V, E> GraphPath<V, E> calculatePath(Graph<V, E> graph, V start, V destination) {
		if(!graph.containsVertex(start)) {
			throw new IllegalArgumentException("Der Startknoten ist nicht im Graphen enthalten");
		}
		if(!graph.containsVertex(destination)) {
			throw new IllegalArgumentException("Der Zielknoten ist nicht im Graphen enthalten");
		}
		
		Map<V, DijkstraAttribute<V>> dijkstraTable = initDijkstraTable(graph, start);
		Set<V> nichtVerarbeitet = initNichtVerarbeitetSet(graph, start);
		
		while(!nichtVerarbeitet.isEmpty()) {
			V vertex = getNaechstenNichtVerarbeitetenKnoten(nichtVerarbeitet, dijkstraTable);
			nichtVerarbeitet.remove(vertex);
			for(E edge : graph.edgesOf(vertex)) {
				V other = vertex.equals(graph.getEdgeSource(edge)) ? graph.getEdgeTarget(edge) : graph.getEdgeSource(edge);
				DijkstraAttribute<V> otherAttribute = dijkstraTable.get(other);
				if(nichtVerarbeitet.contains(other)) {
					DijkstraAttribute<V> vertexAttribute = dijkstraTable.get(vertex);
					int entfNeu = vertexAttribute.entfernung + (int)graph.getEdgeWeight(edge);
					if(entfNeu < otherAttribute.entfernung) {
						otherAttribute.entfernung = entfNeu;
						otherAttribute.vorgaenger = vertex;
					}
				}
			}
		}
		
		int weight = dijkstraTable.get(destination).entfernung;
		List<E> edgeList = calculateEdgeList(graph, dijkstraTable, start, destination);
		return new GraphPathImpl<V, E>(graph, start, destination, edgeList, weight);
	}
	
	private <V, E> Map<V, DijkstraAttribute<V>> initDijkstraTable(Graph<V, E> graph, V start) {
		Map<V, DijkstraAttribute<V>> dijkstraTable = new HashMap<V, DijkstraAttribute<V>>(graph.vertexSet().size());
		for(V vertex : graph.vertexSet()) {
			dijkstraTable.put(vertex, new DijkstraAttribute<V>());
		}
		DijkstraAttribute<V> startAttributes = dijkstraTable.get(start);
		startAttributes.entfernung = 0;
		startAttributes.vorgaenger = start;
		return dijkstraTable;
	}
	
	private <V, E> Set<V> initNichtVerarbeitetSet(Graph<V, E> graph, V start) {
		Set<V> nichtVerarbeitet = new HashSet<V>(graph.vertexSet());
		return nichtVerarbeitet;
	}
	
	private <V> V getNaechstenNichtVerarbeitetenKnoten(Set<V> nichtVerarbeitet, Map<V, DijkstraAttribute<V>> dijkstraTable) {
		V next = null;
		int entfernung = Integer.MAX_VALUE;
		for(V vertex : nichtVerarbeitet) {
			DijkstraAttribute<V> vertexAttributes = dijkstraTable.get(vertex);
			if(vertexAttributes.entfernung <= entfernung) {
				next = vertex;
				entfernung = vertexAttributes.entfernung;
			}
		}
		return next;
	}
	
	private <V, E> List<E> calculateEdgeList(Graph<V, E> graph, Map<V, DijkstraAttribute<V>> dijkstraTable, V start, V destination) {
		List<E> edgeList = new LinkedList<E>();
		
		V vertex = destination;
		while(!start.equals(vertex)) {
			DijkstraAttribute<V> vertexAttribute = dijkstraTable.get(vertex);
			edgeList.add(graph.getEdge(vertex, vertexAttribute.vorgaenger));
			vertex = vertexAttribute.vorgaenger;
		}
		
		Collections.reverse(edgeList);
		return edgeList;
	}
	
	private class DijkstraAttribute<V> {
		private int entfernung;
		private V vorgaenger;
		
		public DijkstraAttribute() {
			entfernung = Integer.MAX_VALUE;
			vorgaenger = null;
		}
		
		@Override
		public String toString() {
			return vorgaenger + " (" + entfernung + ")";
		}
	}
}
