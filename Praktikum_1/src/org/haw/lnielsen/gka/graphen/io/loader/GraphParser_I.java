package org.haw.lnielsen.gka.graphen.io.loader;

import java.io.IOException;
import java.io.InputStream;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Die Schnittstelle für einen Graph-Parser.
 * 
 * @author Lars Nielsen
 */
public interface GraphParser_I {
	/**
	 * Liest einen Graphen aus einem {@link InputStream}.
	 * @param in Der Inputstream aus dem gelesen wird
	 * @return Der gelesene Graph
	 * @throws GraphParseException Wenn ein Fehler beim Verarbeiten des InputStreams auftritt
	 * @throws IOException Wenn ein Fehler beim Zugriff auf den InputStream auftritt
	 */
	Graph<Knoten, DefaultEdge> parseGraph(InputStream in) throws GraphParseException, IOException;
}
