package org.haw.lnielsen.gka.graphen.algorithm.path.astar;

/**
 * Eine Schnittstelle zum Bereitstellen von Heuristikwerten für Knoten.
 * 
 * @author Lars Nielsen
 */
public interface AStarProvider<T> {
	/**
	 * Ermittelt die Heuristik für einen übergebenen Knoten.
	 * @param vertex Der Knoten
	 * @return Die Heuristik
	 */
	int getHeuristik(T vertex);
}