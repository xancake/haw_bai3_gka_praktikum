package org.haw.lnielsen.gka.graphen;

import org.jgrapht.graph.DefaultWeightedEdge;

public class GewichteteKante extends DefaultWeightedEdge {
	@Override
	public String toString() {
		return String.valueOf(getWeight());
	}
}
