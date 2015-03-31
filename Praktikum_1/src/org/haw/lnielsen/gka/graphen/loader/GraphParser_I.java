package org.haw.lnielsen.gka.graphen.loader;

import java.io.IOException;
import java.io.InputStream;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public interface GraphParser_I {
	Graph<Knoten, DefaultEdge> parseGraph(InputStream in) throws IOException;
}
