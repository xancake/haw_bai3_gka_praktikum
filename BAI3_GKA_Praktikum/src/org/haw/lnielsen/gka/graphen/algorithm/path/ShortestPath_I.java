package org.haw.lnielsen.gka.graphen.algorithm.path;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

/**
 * Schnittstelle für einen Algorithmus zur Berechnung von kürzesten Wegen.
 * 
 * @author Lars Nielsen
 */
public interface ShortestPath_I<V, E> {
	/**
	 * Berechnet den kürzesten Pfad von {@code start} nach {@code destination} in dem
	 * Graphen.
	 * @param graph Der Graph
	 * @param start Der Startknoten
	 * @param destination Der Zielknoten
	 * @return Der kürzeste Pfad von start nach destination oder {@code null}, wenn es keinen gibt
	 */
	GraphPath<V, E> calculatePath(Graph<V, E> graph, V start, V destination);
}
