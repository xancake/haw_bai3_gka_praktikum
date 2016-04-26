package org.haw.lnielsen.gka.graphen.algorithm.euler;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

/**
 * Schnittstelle für einen Algorithmus zur Berechnung von Eulertouren.
 * 
 * @author Lars Nielsen
 * @author Jennifer Momsen
 */
public interface EulerAlgorithm_I<V, E> {
	/**
	 * Ermittelt eine Eulertour aus dem übergebenen Graphen.
	 * Eine Eulertour muss alle Kanten aus dem Graphen genau ein mal enthalten
	 * und abgeschlossen sein, der Start- und Endknoten also dieselben sind.
	 * @param graph Der Graph
	 * @return Die Eulertour in einem {@link GraphPath} oder {@code null}, wenn es keine Eulertour gibt 
	 */
	GraphPath<V, E> findEulerTour(Graph<V, E> graph);
}
