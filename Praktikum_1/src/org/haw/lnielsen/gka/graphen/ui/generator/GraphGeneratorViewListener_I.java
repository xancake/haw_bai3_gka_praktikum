package org.haw.lnielsen.gka.graphen.ui.generator;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.GraphGeneratorFactory;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.window.WindowViewListener_I;

/**
 * Schnittstelle für die Benutzerinteraktion auf der Graph-Generator-View.
 * 
 * @author Lars Nielsen
 */
public interface GraphGeneratorViewListener_I extends WindowViewListener_I {
	/**
	 * Wird aufgerufen, wenn die Graph-Eigenschaft "attributiert" ausgewählt wurde.
	 * @param selected {@code true}, wenn die Eigenschaft ausgewählt wurde, ansonsten {@code false}
	 */
	void onAttributedSelected(boolean selected);
	
	/**
	 * Wird aufgerufen, wenn die Graph-Eigenschaft "gerichtet" ausgewählt wurde.
	 * @param selected {@code true}, wenn die Eigenschaft ausgewählt wurde, ansonsten {@code false}
	 */
	void onDirectedSelected(boolean selected);
	
	/**
	 * Wird aufgerufen, wenn die Graph-Eigenschaft "gewichtet" ausgewählt wurde.
	 * @param selected {@code true}, wenn die Eigenschaft ausgewählt wurde, ansonsten {@code false}
	 */
	void onWeightedSelected(boolean selected);
	
	/**
	 * Wird aufgerufen, wenn ein Generator ausgewählt wurde.
	 * @param generator Der ausgewählte Generator
	 */
	void onGeneratorSelected(GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> generator);
	
	/**
	 * Wird aufgerufen, wenn die Aktion zum Generieren eines Graphen aufgerufen wird.
	 * @param attributed Ob die Knoten des Graphens attributiert sein sollen
	 * @param directed Ob der Graph gerichtet sein soll 
	 * @param weighted Ob der Graph gewichtet sein soll
	 * @param generator Der zu verwendende Generator
	 * @param parameter Die Parameter für den Generator
	 */
	void onGenerateGraph(boolean attributed, boolean directed, boolean weighted, GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> generator, Integer... parameter);
	
	/**
	 * Wird aufgerufen, wenn die Aktion zum Abbrechen aufgerufen wird.
	 */
	void onCancel();
}
