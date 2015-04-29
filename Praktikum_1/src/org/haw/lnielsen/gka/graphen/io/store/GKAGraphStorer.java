package org.haw.lnielsen.gka.graphen.io.store;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Klasse zum Speichern von Graphen in einen Ausgabestrom.
 * Dabei wird das Format aus dem GKA-Praktikum eingehalten (vgl. GKAGraphParser).
 * 
 * @author Lars Nielsen
 */
public class GKAGraphStorer implements GraphStorer_I {
	private static final String DIRECTED            = "#directed";
	private static final String ATTRIBUTED          = "#attributed";
	private static final String WEIGHTED            = "#weighted";
	private static final String ATTRIBUTE_SEPARATOR = ":";
	private static final String KANTEN_SEPARATOR    = ",";
	private static final String WEIGHT_SEPARATOR    = "::";
	private static final String SPACE               = " ";
	
	@Override
	public void storeGraph(Graph<Knoten, DefaultEdge> graph, OutputStream output) throws IOException {
		try(PrintStream out = new PrintStream(new BufferedOutputStream(output))) {
			boolean directed = graph instanceof DirectedGraph;
			boolean weighted = graph instanceof WeightedGraph;
			boolean attributed = !graph.vertexSet().isEmpty() ? graph.vertexSet().iterator().next().isAttributiert() : false;
			
			if(directed)   out.print(DIRECTED + SPACE);
			if(attributed) out.print(ATTRIBUTED + SPACE);
			if(weighted)   out.print(WEIGHTED);
			out.println();
			
			Set<DefaultEdge> edges = graph.edgeSet();
			for(DefaultEdge edge : edges) {
				Knoten source = graph.getEdgeSource(edge);
				Knoten target = graph.getEdgeTarget(edge);
				
				printKnoten(out, source, attributed);
				out.print(KANTEN_SEPARATOR);
				printKnoten(out, target, attributed);
				
				if(weighted) {
					out.print(WEIGHT_SEPARATOR);
					out.print((int)graph.getEdgeWeight(edge));
				}
				
				out.println();
			}
			
			Set<Knoten> vertices = graph.vertexSet();
			for(Knoten vertex : vertices) {
				if(graph.edgesOf(vertex).isEmpty()) {
					printKnoten(out, vertex, attributed);
					out.println();
				}
			}
		}
	}
	
	/**
	 * Schreibt einen Knoten in den Ausgabestrom. Dabei wird das Attribut nur mit geschrieben,
	 * wenn es sich um einen attributierten Graphen handelt.
	 * @param out Der Ausgabestrom
	 * @param knoten Der zu schreibende Knoten
	 * @param attributed Ob der Knoten attributiert geschrieben werden soll oder nicht
	 */
	private void printKnoten(PrintStream out, Knoten knoten, boolean attributed) {
		out.print(knoten.getName());
		if(attributed) {
			out.print(ATTRIBUTE_SEPARATOR);
			out.print(knoten.getAttribut());
		}
	}
}
