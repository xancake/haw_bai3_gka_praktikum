package org.haw.lnielsen.gka.graphen;

import org.jgrapht.graph.DefaultWeightedEdge;

public class GewichteteKante extends DefaultWeightedEdge {
	private static final long serialVersionUID = -158845028547217846L;
	
	@Override
	public String toString() {
		return String.valueOf(getWeight());
	}
}
