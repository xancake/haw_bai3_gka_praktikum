package org.haw.lnielsen.gka.graphen;

import org.jgraph.JGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Modellklasse f�r (ungewichtete) Kanten. Diese Klasse existiert allein dazu,
 * die {@link DefaultEdge#toString()}-Methode so zu �berschreiben, dass sie
 * einen Leerstring zur�ckgibt. Dies wird f�r die Darstellung in einem
 * {@link JGraph} verwendet, damit der Graph nicht so �berladen aussieht.
 * In der Standardimplementation aus {@link DefaultEdge} werden ohnehin nur
 * die zugeh�rigen Knoten ausgegeben, was in der GUI keinen Mehrwert bringt.
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
