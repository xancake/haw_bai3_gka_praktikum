package org.haw.lnielsen.gka.graphen.algorithm.path;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.DijkstraShortestPath;

public class JGraphTDijkstraAdapter implements ShortestPath_I {
	@Override
	public <V, E> GraphPath<V, E> calculatePath(Graph<V, E> graph, V start, V destination) {
		DijkstraShortestPath<V, E> algorithm = new DijkstraShortestPath<V, E>(graph, start, destination);
		GraphPath<V, E> shortestPath = algorithm.getPath();
		return shortestPath;
	}
	
	@Override
	public String toString() {
		return "JGraphT Dijkstra Shortest Path";
	}
}
