package org.haw.lnielsen.gka.graphen.ui.generator;

import java.util.List;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.GraphGeneratorFactory;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.window.WindowView_I;

public interface GraphGeneratorView_I extends WindowView_I<Void, GraphGeneratorViewListener_I> {
	
	void setGraphGenerators(List<GraphGeneratorFactory<Knoten, DefaultEdge, Knoten>> generators);
	
	
	void showFehlermeldung(String meldung);
	
	
	void showFehlermeldung(Throwable exception, boolean showTrace);
}
