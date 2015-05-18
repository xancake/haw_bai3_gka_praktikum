package org.haw.lnielsen.gka.graphen.ui.editor;

import java.util.List;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.window.WindowView_I;

/**
 * Schnittstelle für das Hauptfenster der Benutzeroberfläche.
 * 
 * @author Lars Nielsen
 */
public interface GraphEditorWindow_I extends WindowView_I<Graph<Knoten, DefaultEdge>, GraphEditorWindowListener_I> {
	/**
	 * Legt die Algorithmen zur Berechnung des kürzesten Weges fest,
	 * die die Benutzeroberläche anbietet.
	 * @param algorithms Eine Liste der Algorithmen
	 */
	void setShortestPathAlgorithms(List<ShortestPath_I<Knoten, DefaultEdge>> algorithms);
	
	/**
	 * Legt die Algorithmen zur Berechnung des Spannbaums fest,
	 * die die Benutzeroberfläche anbietet.
	 * @param algorithms Eine Liste der Algorithmen
	 */
	void setSpanningTreeAlgorithms(List<SpanningTreeAlgorithm_I<Knoten, DefaultEdge>> algorithms);
	
	/**
	 * Zeigt das Graphengewicht auf der Benutzeroberfläche an.
	 * @param weight Das Gesamtgewicht des Graphen
	 */
	void showGraphWeight(double weight);
	
	/**
	 * Zeigt den übergebenen Pfad an.
	 * @param path Der anzuzeigende Pfad
	 * @param anzahlZugriffe Die Anzahl der Zugriffe auf den Graphen
	 */
	void showPath(GraphPath<Knoten, DefaultEdge> path, int anzahlZugriffe);
	
	/**
	 * Zeigt die Traversierung des Graphens an, in der Reihenfolge,
	 * in der die Knoten durchlaufen wurden.
	 * @param trace Die Knoten in der Reihenfolge, in der sie durchlaufen wurden
	 */
	void showTraverseTrace(List<Knoten> trace);
	
	/**
	 * Zeigt die übergebene Nachricht als Fehlermeldung an.
	 * @param message Die Nachricht
	 */
	void showFehlermeldung(String message);
	
	/**
	 * Zeigt die übergebene Exception an. Dabei kann festgelegt werden, ob der
	 * gesamte Stacktrace ausgegeben werden soll, oder nur die kaskadierenden Nachrichten.
	 * @param exception Die Exception
	 * @param showTrace {@code true}, wenn der Stacktrace angezeigt werden soll, ansonsten {@code false}
	 */
	void showFehlermeldung(Throwable exception, boolean showTrace);
}
