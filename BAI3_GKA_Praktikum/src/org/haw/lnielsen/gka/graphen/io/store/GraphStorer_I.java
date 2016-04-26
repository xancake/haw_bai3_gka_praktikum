package org.haw.lnielsen.gka.graphen.io.store;

import java.io.IOException;
import java.io.OutputStream;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Die Schnittstelle für einen Graph-Schreiber.
 * 
 * @author Lars Nielsen
 */
public interface GraphStorer_I {
	/**
	 * Speichert den übergebenen Graphen.
	 * @param graph Der zu speichernde Graph
	 * @param out Der Outputstream in den geschrieben wird
	 * @throws IOException Wenn ein Fehler beim Speichern auftritt
	 */
	void storeGraph(Graph<Knoten, DefaultEdge> graph, OutputStream out) throws IOException;
}
