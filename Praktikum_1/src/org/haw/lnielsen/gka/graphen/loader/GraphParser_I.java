package org.haw.lnielsen.gka.graphen.loader;

import java.io.IOException;
import java.io.InputStream;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public interface GraphParser_I {
	/**
	 * Liest einen Graphen aus einem {@link InputStream}.
	 * @param in Der Inputstream
	 * @return Der gelesene Graph
	 * @throws IOException Wenn ein Fehler beim Zugriff auf den Inputstream auftritt
	 */
	Graph<Knoten, DefaultEdge> parseGraph(InputStream in) throws IOException;
}
