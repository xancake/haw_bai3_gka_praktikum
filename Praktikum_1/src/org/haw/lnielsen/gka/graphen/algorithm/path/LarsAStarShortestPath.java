package org.haw.lnielsen.gka.graphen.algorithm.path;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphPathImpl;

/**
 * Implementation des A* Algorithmus wie in der Vorlesung gelernt.
 * 
 * @author Lars Nielsen
 */
public class LarsAStarShortestPath<V, E> implements ShortestPath_I<V, E> {
	private AStarProvider<V> myAStarProvider;
	
	public LarsAStarShortestPath(AStarProvider<V> provider) {
		myAStarProvider = provider;
	}
	
	@Override
	public GraphPath<V, E> calculatePath(Graph<V, E> graph, V start, V destination) {
		if(!graph.containsVertex(start)) {
			throw new IllegalArgumentException("Der Startknoten ist nicht im Graphen enthalten");
		}
		if(!graph.containsVertex(destination)) {
			throw new IllegalArgumentException("Der Zielknoten ist nicht im Graphen enthalten");
		}
		
		Map<V, AStarAttribute> aStarTable = initAStarTable(graph, start);
		Set<V> bereitsVerarbeitet = new HashSet<>();
		Set<V> nichtVerarbeitet = new HashSet<>();
		nichtVerarbeitet.add(start);
		
		while(!nichtVerarbeitet.isEmpty() && !bereitsVerarbeitet.contains(destination)) {
			V vertex = getNaechstenNichtVerarbeitetenKnoten(nichtVerarbeitet, aStarTable);
			nichtVerarbeitet.remove(vertex);
			bereitsVerarbeitet.add(vertex);
			Set<E> outgoingEdgesOfVertex = graph instanceof DirectedGraph ? ((DirectedGraph<V, E>)graph).outgoingEdgesOf(vertex) : graph.edgesOf(vertex);
			for(E edge : outgoingEdgesOfVertex) {
				V other = getTargetVertex(graph, vertex, edge);
				if(!bereitsVerarbeitet.contains(other)) {
					nichtVerarbeitet.add(other);
				}
				AStarAttribute otherAttribute = aStarTable.get(other);
				if(!bereitsVerarbeitet.contains(other)) {
					AStarAttribute vertexAttribute = aStarTable.get(vertex);
					int entfNeu = vertexAttribute.entfernung + (int)graph.getEdgeWeight(edge);
					if(entfNeu < otherAttribute.entfernung) {
						otherAttribute.entfernung = entfNeu;
						otherAttribute.schaetzung = entfNeu + myAStarProvider.getHeuristik(other);
						otherAttribute.vorgaenger = vertex;
					}
				}
			}
		}
		
		// Zielknoten nicht erreichbar
		if(aStarTable.get(destination).vorgaenger == null) {
			return null;
		}
		
		int weight = aStarTable.get(destination).entfernung;
		List<E> edgeList = createEdgeList(graph, aStarTable, start, destination);
		return new GraphPathImpl<V, E>(graph, start, destination, edgeList, weight);
	}
	
	private Map<V, AStarAttribute> initAStarTable(Graph<V, E> graph, V start) {
		Map<V, AStarAttribute> dijkstraTable = new HashMap<V, AStarAttribute>(graph.vertexSet().size());
		for(V vertex : graph.vertexSet()) {
			dijkstraTable.put(vertex, new AStarAttribute());
		}
		AStarAttribute startAttributes = dijkstraTable.get(start);
		startAttributes.entfernung = 0;
		startAttributes.vorgaenger = start;
		startAttributes.schaetzung = myAStarProvider.getHeuristik(start);
		return dijkstraTable;
	}
	
	private V getNaechstenNichtVerarbeitetenKnoten(Set<V> nichtVerarbeitet, Map<V, AStarAttribute> aStarTable) {
		V next = null;
		int schaetzung = Integer.MAX_VALUE;
		for(V vertex : nichtVerarbeitet) {
			AStarAttribute vertexAttributes = aStarTable.get(vertex);
			if(vertexAttributes.schaetzung < schaetzung) {
				next = vertex;
				schaetzung = vertexAttributes.schaetzung;
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
	
	private List<E> createEdgeList(Graph<V, E> graph, Map<V, AStarAttribute> aStarTable, V start, V destination) {
		// Kein Pfad von start - destination
		if(aStarTable.get(destination).vorgaenger == null) {
			return null;
		}
		
		List<E> edgeList = new LinkedList<E>();
		
		V vertex = destination;
		while(!start.equals(vertex)) {
			AStarAttribute vertexAttribute = aStarTable.get(vertex);
			edgeList.add(graph.getEdge(vertexAttribute.vorgaenger, vertex));
			vertex = vertexAttribute.vorgaenger;
		}
		
		Collections.reverse(edgeList);
		return edgeList;
	}
	
	public interface AStarProvider<T> {
		int getHeuristik(T vertex);
	}
	
	private class AStarAttribute {
		private int entfernung;
		private int schaetzung;
		private V vorgaenger;
		
		public AStarAttribute() {
			entfernung = Integer.MAX_VALUE;
			schaetzung = 0;
			vorgaenger = null;
		}
		
		@Override
		public String toString() {
			return vorgaenger + " (" + entfernung + ")";
		}
	}
	
	@Override
	public String toString() {
		return "Lars A* Implementation";
	}
}
