package org.haw.lnielsen.gka.graphen.ui.simple;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public interface SimpleGraphUI_I {
	/**
	 * Zeigt den übergebenen Graphen an.
	 * @param graph Der anzuzeigende Graph
	 */
	void showGraph(Graph<Knoten, DefaultEdge> graph);
}
