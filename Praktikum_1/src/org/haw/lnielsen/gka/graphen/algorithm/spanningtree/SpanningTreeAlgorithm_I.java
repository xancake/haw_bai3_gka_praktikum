package org.haw.lnielsen.gka.graphen.algorithm.spanningtree;

import org.jgrapht.Graph;

/**
 * Schnittstelle für Algorithmen zum Ermitteln von Spannbäumen von Graphen.
 * 
 * @author Lars Nielsen
 */
public interface SpanningTreeAlgorithm_I<V, E> {
	/**
	 * Erzeugt einen Spannbaum aus dem übergebenen Graphen und gibt den
	 * Spannbaum als neuen Graphen zurück. Der übergebene Graph darf nicht
	 * modifiziert werden.
	 * @param graph Der Graph von dem der Spannbaum erzeugt werden soll
	 * @return Der Spannbaum des Graphen als neuer Graph
	 */
	Graph<V, E> calculateSpanningTree(Graph<V, E> graph);
}
