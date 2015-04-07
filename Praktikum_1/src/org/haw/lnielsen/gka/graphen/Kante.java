package org.haw.lnielsen.gka.graphen;

import org.jgraph.JGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Modellklasse für (ungewichtete) Kanten. Diese Klasse existiert allein dazu,
 * die {@link DefaultEdge#toString()}-Methode so zu überschreiben, dass sie
 * einen Leerstring zurückgibt. Dies wird für die Darstellung in einem
 * {@link JGraph} verwendet, damit der Graph nicht so überladen aussieht.
 * In der Standardimplementation aus {@link DefaultEdge} werden ohnehin nur
 * die zugehörigen Knoten ausgegeben, was in der GUI keinen Mehrwert bringt.
 * 
 * @author Lars Nielsen
 */
public class Kante extends DefaultEdge {
	private static final long serialVersionUID = -42077918715721940L;
	
	@Override
	public String toString() {
		return new String();
	}
}
