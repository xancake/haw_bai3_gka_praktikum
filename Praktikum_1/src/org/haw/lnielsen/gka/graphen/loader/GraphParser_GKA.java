package org.haw.lnielsen.gka.graphen.loader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

public class GraphParser_GKA implements GraphParser_I {
	private static final String COMMENT_PREFIX      = "//";
	private static final String DIRECTED            = "#directed";
	private static final String ATTRIBUTED          = "#attributed";
	private static final String WEIGHTED            = "#weighted";
	private static final String ATTRIBUTE_SEPARATOR = ":";
	private static final String KANTEN_SEPARATOR    = ",";
	private static final String WEIGHT_SEPARATOR    = "::";
	private static final String NAME                = "[a-zA-Z_]+[0-9]*";
	private static final String NUMBER              = "[0-9]+";
	
	private static final Pattern PHEADER         = Pattern.compile("(" + DIRECTED + ")?\\s*(" + ATTRIBUTED + ")?\\s*(" + WEIGHTED + ")?");
	private static final Pattern PDEF_SIMPLE     = Pattern.compile(NAME + KANTEN_SEPARATOR + NAME);
	private static final Pattern PDEF_WEIGHTED   = Pattern.compile(NAME + KANTEN_SEPARATOR + NAME + WEIGHT_SEPARATOR + NUMBER);
	private static final Pattern PDEF_ATTRIBUTED = Pattern.compile(NAME + ATTRIBUTE_SEPARATOR + NUMBER + KANTEN_SEPARATOR + NAME + ATTRIBUTE_SEPARATOR + NUMBER);
	private static final Pattern PDEF_WEIGHTED_ATTRIBUTED = Pattern.compile(NAME + ATTRIBUTE_SEPARATOR + NUMBER + KANTEN_SEPARATOR + NAME + ATTRIBUTE_SEPARATOR + NUMBER + WEIGHT_SEPARATOR + NUMBER);
	
	private static final int NO_HEADER_FOUND = -1;
	
	public Graph<Knoten, DefaultEdge> parseGraph(InputStream in) throws IOException {
		try(Scanner scanner = new Scanner(new BufferedInputStream(in))) {
			int aktuelleZeile = 0;
			int headerZeile = NO_HEADER_FOUND;
			boolean directed = false;
			boolean attributed = false;
			boolean weighted = false;
			
			String line = null;
			while(scanner.hasNextLine() && headerZeile == NO_HEADER_FOUND) {
				line = scanner.nextLine();
				aktuelleZeile++;
				
				if(line.trim().isEmpty() || line.startsWith(COMMENT_PREFIX)) {
					continue;
				} else if(PHEADER.matcher(line).matches()) {
					headerZeile = aktuelleZeile;
					directed = line.contains(DIRECTED);
					attributed = line.contains(ATTRIBUTED);
					weighted = line.contains(WEIGHTED);
				} else if(PDEF_SIMPLE.matcher(line).matches()) {
					break;
				} else {
					throw new RuntimeException("Couldn't parse line: " + line);
				}
			}
			
			Graph<Knoten, DefaultEdge> graph = createGraph(directed, weighted);
			if(headerZeile == NO_HEADER_FOUND) {
				parseDefinitionLine(graph, directed, attributed, weighted, line);
			}
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				aktuelleZeile++;
				parseDefinitionLine(graph, directed, attributed, weighted, line);
			}
			
			return graph;
		}
	}
	
	private void parseDefinitionLine(Graph<Knoten, DefaultEdge> graph, boolean directed, boolean attributed, boolean weighted, String line) {
		if(line.trim().isEmpty() || line.startsWith(COMMENT_PREFIX)) {
			// skip
		} else {
			Knoten k1;
			Knoten k2;
			int weight = 0;
			if(!attributed && !weighted && PDEF_SIMPLE.matcher(line).matches()) {
				String[] elem = line.split(KANTEN_SEPARATOR);
				k1 = new Knoten(elem[0]);
				k2 = new Knoten(elem[1]);
			} else if(attributed && !weighted && PDEF_ATTRIBUTED.matcher(line).matches()) {
				String[] elem = line.split("[" + KANTEN_SEPARATOR + ATTRIBUTE_SEPARATOR + "]");
				k1 = new Knoten(elem[0], Integer.parseInt(elem[1]));
				k2 = new Knoten(elem[2], Integer.parseInt(elem[3]));
			} else if(!attributed && weighted && PDEF_WEIGHTED.matcher(line).matches()) {
				String[] elem = line.split(KANTEN_SEPARATOR + "|(" + WEIGHT_SEPARATOR + ")");
				k1 = new Knoten(elem[0]);
				k2 = new Knoten(elem[1]);
				weight = Integer.parseInt(elem[2]);
			} else if(attributed && weighted && PDEF_WEIGHTED_ATTRIBUTED.matcher(line).matches()) {
				String[] elem = line.split("[" + KANTEN_SEPARATOR + ATTRIBUTE_SEPARATOR + "]|(" + WEIGHT_SEPARATOR + ")");
				k1 = new Knoten(elem[0], Integer.parseInt(elem[1]));
				k2 = new Knoten(elem[2], Integer.parseInt(elem[3]));
				weight = Integer.parseInt(elem[4]);
			} else {
				throw new RuntimeException("Couldn't parse definition line: " + line);
			}
			graph.addVertex(k1);
			graph.addVertex(k2);
			DefaultEdge edge = graph.addEdge(k1, k2);
			if(weighted) {
				((WeightedGraph<Knoten, DefaultEdge>)graph).setEdgeWeight(edge, weight);
			}
		}
	}
	
	private Graph<Knoten, DefaultEdge> createGraph(boolean directed, boolean weighted) {
		if(directed && weighted) {
			return new ListenableDirectedWeightedGraph<Knoten, DefaultEdge>(DefaultWeightedEdge.class);
		} else if(directed && !weighted) {
			return new ListenableDirectedGraph<Knoten, DefaultEdge>(DefaultEdge.class);
		} else if(!directed && weighted) {
			return new ListenableUndirectedWeightedGraph<Knoten, DefaultEdge>(DefaultWeightedEdge.class);
		} else {
			return new ListenableUndirectedGraph<Knoten, DefaultEdge>(DefaultEdge.class);
		}
	}
}
