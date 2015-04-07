package org.haw.lnielsen.gka.graphen.ui.editor;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import de.xancake.ui.mvc.window.WindowView_I;

/**
 * Schnittstelle f�r das Hauptfenster der Benutzeroberfl�che.
 * 
 * @author Lars Nielsen
 */
public interface GraphEditorWindow_I extends WindowView_I<Graph<Knoten, DefaultEdge>, GraphEditorWindowListener_I> {
	/**
	 * Zeigt den �bergebenen Pfad an.
	 * @param path Der anzuzeigende Pfad
	 */
	void showPath(GraphPath<Knoten, DefaultEdge> path);
}
