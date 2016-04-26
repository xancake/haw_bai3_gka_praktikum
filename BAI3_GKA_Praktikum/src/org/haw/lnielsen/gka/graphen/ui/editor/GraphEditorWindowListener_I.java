package org.haw.lnielsen.gka.graphen.ui.editor;

import java.io.File;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.euler.EulerAlgorithm_I;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.window.WindowViewListener_I;

/**
 * Schnittstelle für die Benutzerinteraktionen auf dem Hauptfenster der Benutzeroberfläche.
 * 
 * @author Lars Nielsen
 */
public interface GraphEditorWindowListener_I extends WindowViewListener_I {
	/**
	 * Wird aufgerufen, wenn die Aktion zum Anlegen eines neuen Graphen aufgerufen wird.
	 */
	void onNewGraph();
	
	/**
	 * Wird aufgerufen, wenn die Aktion zum Laden eines Graphen aus einer Datei aufgerufen wird.
	 * @param file Die Datei, aus der der Graph geladen werden soll
	 */
	void onLoadGraph(File file);
	
	/**
	 * Wird aufgerufen, wenn die Aktion zum Speichern eines Graphen in eine Datei aufgerufen wird.
	 * @param file Die Datei, in die der Graph gespeichert werden soll
	 */
	void onStoreGraph(File file);
	
	/**
	 * Wird aufgerufen, wenn die Aktion zum Berechnen des Graphengewichts aufgerufen wird.
	 */
	void onCalculateGraphWeight();
	
	/**
	 * Wird aufgerufen, wenn die Aktion zum berechnen des kürzesten Pfades zwischen zwei Knoten
	 * aufgerufen wird.
	 * @param algorithm Der Algorithmus der verwendet werden soll
	 * @param start Der Startknoten
	 * @param end Der Zielknoten
	 */
	void onCalculateShortestPath(ShortestPath_I<Knoten, DefaultEdge> algorithm, Knoten start, Knoten end);
	
	/**
	 * Wird aufgerufen, wenn die Aktion zum Traversieren des Graphens aufgerufen wird.
	 * @param start Der Knoten von dem aus der Graph traversiert werden soll
	 */
	void onTraverse(Knoten start);
	
	/**
	 * Wird aufgerufen, wenn die Aktion zum berechnen des Spannbaums eines Graphen aufgerufen wird.
	 * @param algorithm Der Algorithmus der verwendet werden soll
	 */
	void onCalculateSpanningTree(SpanningTreeAlgorithm_I<Knoten, DefaultEdge> algorithm);
	
	/**
	 * Wird aufgerufen, wenn die Aktion zum Ermitteln eines Eulerkreises von einem Graphen
	 * aufgerufen wird.
	 * @param algorithm Der Algorithmus der verwendet werden soll
	 */
	void onCalculateEulerTour(EulerAlgorithm_I<Knoten, DefaultEdge> algorithm);
}
