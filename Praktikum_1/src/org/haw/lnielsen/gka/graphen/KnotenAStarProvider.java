package org.haw.lnielsen.gka.graphen;

import org.haw.lnielsen.gka.graphen.algorithm.path.LarsAStarShortestPath.AStarProvider;

/**
 * Stellt die Heuristik eines Knoten bereit.
 * 
 * @author Lars Nielsen
 */
public class KnotenAStarProvider implements AStarProvider<Knoten> {
	@Override
	public int getHeuristik(Knoten knoten) {
		return knoten.getAttribut();
	}
}
