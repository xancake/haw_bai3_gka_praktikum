package org.haw.lnielsen.gka.graphen.ui.generator;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.GraphGeneratorFactory;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.window.WindowViewListener_I;

public interface GraphGeneratorViewListener_I extends WindowViewListener_I {
	
	void onGeneratorSelected(GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> generator);
	
	
	void onGenerateGraph(boolean attributed, boolean directed, boolean weighted, GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> generator, Integer... parameter);
	
	
	void onCancel();
}
