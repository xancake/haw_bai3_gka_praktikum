package org.haw.lnielsen.gka.graphen.algorithm.path;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

public interface ShortestPath_I {
	<V, E> GraphPath<V, E> calculatePath(Graph<V, E> graph, V start, V destination);
}
