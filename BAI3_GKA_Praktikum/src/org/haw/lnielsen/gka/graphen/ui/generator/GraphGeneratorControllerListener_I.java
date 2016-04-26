package org.haw.lnielsen.gka.graphen.ui.generator;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.ControllerListener_I;

/**
 * Schnittstelle um auf Ereignisse des Graph Generatoren zu reagieren.
 * 
 * @author Lars Nielsen
 */
public interface GraphGeneratorControllerListener_I extends ControllerListener_I {
	/**
	 * Wird aufgerufen, wenn ein Graph generiert wurde.
	 * @param graph Der generierte Graph
	 */
	void onGraphGenerated(Graph<Knoten, DefaultEdge> graph);
	
	/**
	 * Wird aufgerufen, wenn die Aktion abgebrochen wird.
	 */
	void onCancel();
}
