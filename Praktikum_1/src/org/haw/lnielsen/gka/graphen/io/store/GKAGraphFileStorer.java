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

public class GKAGraphFileStorer implements GraphStorer_I {
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
			
			Set<Knoten> vertices = graph.vertexSet();
			Set<DefaultEdge> edges = graph.edgeSet();
			for(Knoten knoten : vertices) {
				
				
				out.println();
			}
		}
	}
}
