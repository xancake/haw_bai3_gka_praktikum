package org.haw.lnielsen.gka.graphen.algorithm.path.dijkstra;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphPathImpl;

/**
 * Implementation des Dijkstra-Shortest-Path-Algorithmus wie in der Vorlesung gelernt.
 * 
 * @author Lars Nielsen
 */
public class LarsDijkstraShortestPath<V, E> implements ShortestPath_I<V, E> {
	@Override
	public GraphPath<V, E> calculatePath(Graph<V, E> graph, V start, V destination) {
		if(!graph.containsVertex(start)) {
			throw new IllegalArgumentException("Der Startknoten ist nicht im Graphen enthalten");
		}
		if(!graph.containsVertex(destination)) {
			throw new IllegalArgumentException("Der Zielknoten ist nicht im Graphen enthalten");
		}
		
		Map<V, DijkstraAttribute> dijkstraTable = initDijkstraTable(graph, start);
		Set<V> bereitsVerarbeitet = new HashSet<>();
		Set<V> nichtVerarbeitet = new HashSet<>();
		nichtVerarbeitet.add(start);
		
		while(!nichtVerarbeitet.isEmpty() && !bereitsVerarbeitet.contains(destination)) {
			V vertex = getNaechstenNichtVerarbeitetenKnoten(nichtVerarbeitet, dijkstraTable);
			
			// Aktuellen Knoten als verarbeitet markieren
			nichtVerarbeitet.remove(vertex);
			bereitsVerarbeitet.add(vertex);
			
			Set<E> outgoingEdgesOfVertex = graph instanceof DirectedGraph ? ((DirectedGraph<V, E>)graph).outgoingEdgesOf(vertex) : graph.edgesOf(vertex);
			for(E edge : outgoingEdgesOfVertex) {
				V other = getTargetVertex(graph, vertex, edge);
				
				// Anderen Knoten in die Liste der zu verarbeitenden Knoten aufnehmen, wenn er noch nicht verarbeitet wurde
				if(!bereitsVerarbeitet.contains(other)) {
					nichtVerarbeitet.add(other);
				}
				
				// Attribute für den anderen Knoten anpassen
				DijkstraAttribute otherAttribute = dijkstraTable.get(other);
				if(!bereitsVerarbeitet.contains(other)) {
					DijkstraAttribute vertexAttribute = dijkstraTable.get(vertex);
					int entfNeu = vertexAttribute.entfernung + (int)graph.getEdgeWeight(edge);
					if(entfNeu < otherAttribute.entfernung) {
						otherAttribute.entfernung = entfNeu;
						otherAttribute.vorgaenger = vertex;
					}
				}
			}
		}
		
		// Zielknoten nicht erreichbar
		if(dijkstraTable.get(destination).vorgaenger == null) {
			return null;
		}
		
		int weight = dijkstraTable.get(destination).entfernung;
		List<E> edgeList = createEdgeList(graph, dijkstraTable, start, destination);
		return new GraphPathImpl<V, E>(graph, start, destination, edgeList, weight);
	}
	
	/**
	 * Initialisiert die Dijkstra-Attribut-Tabelle für alle Knoten aus dem Graphen.
	 * Dabei wird das Attribut für den Startknoten mit {@code vorgaenger=start} und {@code entfernung=0} vorbelegt.
	 * @param graph Der Graph
	 * @param start Der Startknoten
	 * @return Die Dijkstra-Tabelle
	 */
	private Map<V, DijkstraAttribute> initDijkstraTable(Graph<V, E> graph, V start) {
		Map<V, DijkstraAttribute> dijkstraTable = new HashMap<V, DijkstraAttribute>(graph.vertexSet().size());
		for(V vertex : graph.vertexSet()) {
			dijkstraTable.put(vertex, new DijkstraAttribute());
		}
		DijkstraAttribute startAttributes = dijkstraTable.get(start);
		startAttributes.entfernung = 0;
		startAttributes.vorgaenger = start;
		return dijkstraTable;
	}
	
	/**
	 * Ermittelt den nächsten zu verarbeitenden Knoten.
	 * Dies geschieht, indem der Knoten mit dem kleinsten Wert für {@code entfernung} ermittelt wird.
	 * @param nichtVerarbeitet Die Menge der nicht verarbeiteten Knoten
	 * @param dijkstraTable Die Dijkstra-Attribut-Tabelle
	 * @return Den nächsten Knoten
	 */
	private V getNaechstenNichtVerarbeitetenKnoten(Set<V> nichtVerarbeitet, Map<V, DijkstraAttribute> dijkstraTable) {
		V next = null;
		int entfernung = Integer.MAX_VALUE;
		for(V vertex : nichtVerarbeitet) {
			DijkstraAttribute vertexAttributes = dijkstraTable.get(vertex);
			if(vertexAttributes.entfernung < entfernung) {
				next = vertex;
				entfernung = vertexAttributes.entfernung;
			}
		}
		return next;
	}
	
	private V getTargetVertex(Graph<V, E> graph, V source, E edge) {
		if(graph instanceof DirectedGraph) {
			return graph.getEdgeTarget(edge);
		} else {
			return source.equals(graph.getEdgeSource(edge)) ? graph.getEdgeTarget(edge) : graph.getEdgeSource(edge);
		}
	}
	
	/**
	 * Erzeugt eine Liste der Kanten, die zu dem kürzesten Weg zwischen dem Start- und Zielknoten des Graphen gehören.
	 * @param graph Der Graph
	 * @param dijkstraTable Die Dijkstra-Attribut-Tabelle
	 * @param start Der Startknoten
	 * @param destination Der Zielknoten
	 * @return Eine Liste mit den Kanten die zum kürzesten Weg zwischen {@code start} und {@code ziel} gehören
	 */
	private List<E> createEdgeList(Graph<V, E> graph, Map<V, DijkstraAttribute> dijkstraTable, V start, V destination) {
		// Kein Pfad von start - destination
		if(dijkstraTable.get(destination).vorgaenger == null) {
			return null;
		}
		
		List<E> edgeList = new LinkedList<E>();
		
		V vertex = destination;
		while(!start.equals(vertex)) {
			DijkstraAttribute vertexAttribute = dijkstraTable.get(vertex);
			edgeList.add(graph.getEdge(vertexAttribute.vorgaenger, vertex));
			vertex = vertexAttribute.vorgaenger;
		}
		
		Collections.reverse(edgeList);
		return edgeList;
	}
	
	private class DijkstraAttribute {
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
	
	@Override
	public String toString() {
		return "Lars Dijkstra Implementation";
	}
}
