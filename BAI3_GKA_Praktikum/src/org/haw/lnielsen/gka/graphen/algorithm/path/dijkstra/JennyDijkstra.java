package org.haw.lnielsen.gka.graphen.algorithm.path.dijkstra;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphPathImpl;

/**
 * Implementation des Dijkstra-Shortest-Path-Algorithmus wie in der Vorlesung gelernt.
 * 
 * @author Jennifer Momsen
 */
public class JennyDijkstra<V, E> implements ShortestPath_I<V, E> {

	@Override
	public GraphPath<V, E> calculatePath(Graph<V, E> graph, V start, V destination) {
		 
		//Prüfen ob der Knoten im Graph ist
		if(!graph.containsVertex(start) || !graph.containsVertex(destination)) {
			throw new IllegalArgumentException("Der Knoten existiert nicht!");
		}
		
		Map<V, Attribut> dijkstraMap = new HashMap<V, Attribut>();
		
		//Befüllen der Tabelle:
		initTable(dijkstraMap, graph, start);
		
		while(alleKnotenBesucht(graph, dijkstraMap) == false){
			V aktuellerKnoten = sucheKleinsteEntf(graph, dijkstraMap);
		
			if(aktuellerKnoten == null) {
				return null;
			}
			
			Attribut nextAttribut = dijkstraMap.get(aktuellerKnoten);
			nextAttribut._ok = true;
			
			//Wenn man am Ziel angekommen ist und die kürzeste Entfernung für den Zielknoten gefunden wurde,
			//breche Schleife ab und gebe den Path aus.
			if(dijkstraMap.get(destination)._ok && aktuellerKnoten.equals(destination)){
				break;
			}
			
			for(V vertex : graph.vertexSet()){
				Attribut knotenAttributVertex = dijkstraMap.get(vertex);
				double neueEntf = 0;
				E aktuelleKante;
		
				if(!knotenAttributVertex._ok && graph.containsEdge(aktuellerKnoten, vertex)) {
					aktuelleKante = graph.getEdge(aktuellerKnoten, vertex);
					neueEntf = nextAttribut._entfernung + graph.getEdgeWeight(aktuelleKante);
					if(dijkstraMap.get(vertex)._entfernung > neueEntf){
						knotenAttributVertex._entfernung = neueEntf;
						knotenAttributVertex._vorgaenger = aktuellerKnoten;	
					}
				}
			}
		}
		List<E> edgeList = ermittleEdgeList(start, destination, dijkstraMap, graph);
		double weight = dijkstraMap.get(destination)._entfernung;
		GraphPathImpl<V, E> graphPath = new GraphPathImpl<V, E>(graph, start, destination, edgeList, weight);
		return graphPath;
	}
	/**
	 * Initialisiert die Tabelle mit den Startwerten
	 * @param dijkstraMap Die Tabelle in der die Attribute eingefügt werden
	 * @param graph Der Graph, wodrin der kürzeste Weg berechnet wird
	 * @param start Der Startknoten
	 * @return Map Die Tabelle mit den Attributen von jeden Knoten
	 */
	private Map<V, Attribut> initTable(Map<V, Attribut> dijkstraMap, Graph<V, E> graph, V start) {
		for(V vertex : graph.vertexSet()){
			dijkstraMap.put(vertex, new Attribut());
		}
		dijkstraMap.put(start, new Attribut(start, 0, false));
		return dijkstraMap;
	}
	/**
	 * Sucht den Knoten, mit der geringsten Entfernung
	 * @param graph Der Graph in dem nach dem Knoten gesucht werden soll
	 * @param map Die Tabelle, in denen die Attribute der Knoten enthalten sind
	 * @return V der Knoten mit der kleinsten Entfernung
	 */
	private V sucheKleinsteEntf(Graph<V, E> graph, Map<V, Attribut> map) {
		double min = Double.MAX_VALUE;
		V minVertex = null;
		for(V vertex : graph.vertexSet()){
			Attribut attrVertex = map.get(vertex);
			if(!attrVertex._ok && (attrVertex._entfernung < min)) {
				min = attrVertex._entfernung;
				minVertex = vertex;
			}
		}
		return minVertex;
	}
	
	/**
	 * Schaut, ob alle Knoten in dem Graphen auf true gesetzt sind, also für jeden die kürzeste
	 * Entfernung ermittelt wurde.
	 * @param graph Der Graph, wo nach den Knoten gesucht werden soll
	 * @param map Die Tabelle, in der die aktuelle Belegung der Attribute nachgeschaut werden kann
	 * @return true, wenn alle Knoten besucht wurden; false, wenn noch nicht alle Knoten besucht worden sind
	 */
	private boolean alleKnotenBesucht(Graph<V, E> graph, Map<V, Attribut> map){
		Boolean vertexOk = true;
		for(V vertex : graph.vertexSet()){
			vertexOk = (map.get(vertex))._ok;
			if(!vertexOk){
				return vertexOk;
			}
		}
		return vertexOk;
	}
	
	/**
	 * Ermittelt eine Liste der Kanten, die zur Erzeugung des kürzesten Weges notwendig ist
	 * @param start Der Startknoten
	 * @param destination Der Endknoten
	 * @param graph Der Graph, der die Knoten enthält
	 * @param map Die Tabelle, in der die aktuelle Belegung der Attribute nachgeschaut werden kann
	 * @return list Die Liste aller Kanten, die im kürzesten Weg vorkommen
	 */
	private List<E> ermittleEdgeList(V start,V destination, Map<V, Attribut> map, Graph<V, E> graph){
		List<E> list = new LinkedList<>();
		V aktuellerKnoten = destination;
		V aktuellerVorgaenger = map.get(destination)._vorgaenger;
		while(!aktuellerKnoten.equals(start)){
			E kante = graph.getEdge(aktuellerKnoten, aktuellerVorgaenger);
			list.add(kante);
			aktuellerKnoten = aktuellerVorgaenger;
			aktuellerVorgaenger = map.get(aktuellerKnoten)._vorgaenger;	
		}
		Collections.reverse(list);
		return list;	
	}
	
	
	private class Attribut {
		private V _vorgaenger;
		private double _entfernung;
		private boolean _ok;
		
		private Attribut() {
			this(null, Double.MAX_VALUE,false);
		}
		private Attribut(V vorgaenger, double entfernung, boolean ok){
			this._vorgaenger = vorgaenger;
			this._entfernung = entfernung;
			this._ok = ok;
		}	
	}
	
	@Override
	public String toString() {
		return "Jenny Dijkstra Implementation";
	}
}
