package org.haw.lnielsen.gka.graphen.algorithm.path.dijkstra;

import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.DijkstraShortestPath;

/**
 * Adapterklasse für die JGraphT-Implementation des Dijkstra-Algorithmus, um diesen in der
 * Benutzeroberfläche anzeigen und auswählen zu können.
 * 
 * @author Lars Nielsen
 */
public class JGraphTDijkstraAdapter<V, E> implements ShortestPath_I<V, E> {
	@Override
	public GraphPath<V, E> calculatePath(Graph<V, E> graph, V start, V destination) {
		DijkstraShortestPath<V, E> algorithm = new DijkstraShortestPath<V, E>(graph, start, destination);
		GraphPath<V, E> shortestPath = algorithm.getPath();
		return shortestPath;
	}
	
	@Override
	public String toString() {
		return "JGraphT Dijkstra Shortest Path";
	}
}
