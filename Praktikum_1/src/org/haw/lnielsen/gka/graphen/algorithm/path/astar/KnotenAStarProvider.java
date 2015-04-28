package org.haw.lnielsen.gka.graphen.algorithm.path.astar;

import org.haw.lnielsen.gka.graphen.Knoten;

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
