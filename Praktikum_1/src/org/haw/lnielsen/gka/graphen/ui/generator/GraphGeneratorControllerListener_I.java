package org.haw.lnielsen.gka.graphen.ui.generator;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.ControllerListener_I;

public interface GraphGeneratorControllerListener_I extends ControllerListener_I {
	void onGraphGenerated(Graph<Knoten, DefaultEdge> graph);
	void onCancel();
}
