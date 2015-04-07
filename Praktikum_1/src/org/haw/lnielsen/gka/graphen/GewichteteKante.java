package org.haw.lnielsen.gka.graphen;

import org.jgraph.JGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Modellklasse f�r gewichtete Kanten. Diese Klasse existiert allein dazu,
 * die {@link DefaultWeightedEdge#toString()}-Methode so zu �berschreiben,
 * dass nur das Gewicht der Kante zur�ckgegeben wird. Dies wird f�r die
 * Darstellung in einem {@link JGraph} verwendet, damit dieser das Gewicht
 * auch anzeigt.
 * 
 * @author Lars Nielsen
 */
public class GewichteteKante extends DefaultWeightedEdge {
	private static final long serialVersionUID = -158845028547217846L;
	
	@Override
	public String toString() {
		return String.valueOf(getWeight());
	}
}
