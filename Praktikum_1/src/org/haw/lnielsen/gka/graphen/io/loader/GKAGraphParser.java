package org.haw.lnielsen.gka.graphen.io.loader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.haw.lnielsen.gka.graphen.GewichteteKante;
import org.haw.lnielsen.gka.graphen.Kante;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

/**
 * Die standart Implementation eines Graph-Parsers.
 * Der Parser unterstützt das in der GKA-Vorlesung vorgegebene Format.
 * Zusätzlich zum ursprünglichen Format können mittels "//" Kommentare angegeben werden,
 * die der Parser nicht verarbeitet. Ebenso werden leere Zeilen in der Verarbeitung übersprungen.
 * 
 * @author Lars Nielsen
 */
public class GKAGraphParser implements GraphParser_I {
	private static final String COMMENT_PREFIX      = "//";
	private static final String DIRECTED            = "#directed";
	private static final String ATTRIBUTED          = "#attributed";
	private static final String WEIGHTED            = "#weighted";
	private static final String ATTRIBUTE_SEPARATOR = ":";
	private static final String KANTEN_SEPARATOR    = ",";
	private static final String WEIGHT_SEPARATOR    = "::";
	private static final String NAME                = "[a-zA-Z0-9_ÄäÖöÜü]+";
	private static final String NUMBER              = "[0-9]+";
	private static final String SPACE               = "\\s*";
	
	private static final Pattern PHEADER         = Pattern.compile("(" + DIRECTED + ")?\\s*(" + ATTRIBUTED + ")?\\s*(" + WEIGHTED + ")?");
	private static final Pattern PDEF_KNOTEN     = Pattern.compile(NAME);
	private static final Pattern PDEF_KNOTEN_ATTRIBUTED = Pattern.compile(NAME + ATTRIBUTE_SEPARATOR + NUMBER);
	private static final Pattern PDEF_SIMPLE     = Pattern.compile(NAME + SPACE + KANTEN_SEPARATOR + SPACE + NAME);
	private static final Pattern PDEF_WEIGHTED   = Pattern.compile(NAME + SPACE + KANTEN_SEPARATOR + SPACE + NAME + WEIGHT_SEPARATOR + NUMBER);
	private static final Pattern PDEF_ATTRIBUTED = Pattern.compile(NAME + ATTRIBUTE_SEPARATOR + NUMBER + SPACE + KANTEN_SEPARATOR + SPACE + NAME + ATTRIBUTE_SEPARATOR + NUMBER);
	private static final Pattern PDEF_WEIGHTED_ATTRIBUTED = Pattern.compile(NAME + ATTRIBUTE_SEPARATOR + NUMBER + SPACE + KANTEN_SEPARATOR + SPACE + NAME + ATTRIBUTE_SEPARATOR + NUMBER + WEIGHT_SEPARATOR + NUMBER);
	
	private static final int NO_HEADER_FOUND = -1;
	
	/**
	 * Liest einen Graphen aus einem {@link InputStream}.
	 * Das Format muss den Vorgaben aus der GKA-Vorlesung entsprechen.
	 * @param in Der InputStream aus dem gelesen wird
	 * @return Der gelesene Graph
	 * @throws IOException Wenn ein Fehler beim Zugriff auf den Inputstream auftritt
	 */
	@Override
	public Graph<Knoten, DefaultEdge> parseGraph(InputStream in) throws GraphParseException, IOException {
		try(Scanner scanner = new Scanner(new BufferedInputStream(in))) {
			int currentLine = 0;
			int headerLine = NO_HEADER_FOUND;
			boolean directed = false;
			boolean attributed = false;
			boolean weighted = false;
			
			String line = null;
			while(scanner.hasNextLine() && headerLine == NO_HEADER_FOUND) {
				line = scanner.nextLine().trim();
				currentLine++;
				
				if(line.trim().isEmpty() || line.startsWith(COMMENT_PREFIX)) {
					continue;
				} else if(PHEADER.matcher(line).matches()) {
					headerLine = currentLine;
					directed = line.contains(DIRECTED);
					attributed = line.contains(ATTRIBUTED);
					weighted = line.contains(WEIGHTED);
				} else if(PDEF_SIMPLE.matcher(line).matches()) {
					break;
				} else {
					throw new GraphParseException("Couldn't parse line " + currentLine + ": " + line);
				}
			}
			
			if(line == null) {
				// Es wurde keine Headerzeile gefunden und dabei das Dateiende erreicht
				throw new GraphParseException("File is empty");
			}
			
			Graph<Knoten, DefaultEdge> graph = createGraph(directed, weighted);
			if(headerLine == NO_HEADER_FOUND) {
				parseDefinitionLine(graph, directed, attributed, weighted, line, currentLine);
			}
			while(scanner.hasNextLine()) {
				line = scanner.nextLine().trim();
				currentLine++;
				parseDefinitionLine(graph, directed, attributed, weighted, line, currentLine);
			}
			
			return graph;
		}
	}
	
	/**
	 * Liest und verarbeitet eine Definitions-Zeile.
	 * @param graph Der Graph, dem die gelesenen Knoten und Kanten hinzugefügt werden soll
	 * @param directed Ob die Kanten des Graphen gerichtet sind
	 * @param attributed Ob die Knoten des Graphen attributiert sind
	 * @param weighted Ob die Kanten des Graphen gewichtet sind
	 * @param line Die aktuell vom Parser zu verarbeitende Zeile
	 * @param currentLine Die aktuelle Zeilennummer (wird nur als Zeilenangabe beim Fehlerfall ausgegeben)
	 */
	private void parseDefinitionLine(Graph<Knoten, DefaultEdge> graph, boolean directed, boolean attributed, boolean weighted, String line, int currentLine) throws GraphParseException {
		if(line.isEmpty() || line.startsWith(COMMENT_PREFIX)) {
			// skip
		} else if(!attributed && PDEF_KNOTEN.matcher(line).matches()) {
			graph.addVertex(new Knoten(line.trim()));
		} else if(attributed && PDEF_KNOTEN_ATTRIBUTED.matcher(line).matches()) {
			String[] elem = line.split(ATTRIBUTE_SEPARATOR);
			graph.addVertex(new Knoten(elem[0].trim(), Integer.parseInt(elem[1].trim())));
		} else {
			Knoten k1 = null;
			Knoten k2 = null;
			int weight = 0;
			if(!attributed && !weighted && PDEF_SIMPLE.matcher(line).matches()) {
				String[] elem = line.split(KANTEN_SEPARATOR);
				k1 = new Knoten(elem[0].trim());
				k2 = new Knoten(elem[1].trim());
			} else if(attributed && !weighted && PDEF_ATTRIBUTED.matcher(line).matches()) {
				String[] elem = line.split(KANTEN_SEPARATOR + "|" + ATTRIBUTE_SEPARATOR);
				k1 = new Knoten(elem[0].trim(), Integer.parseInt(elem[1].trim()));
				k2 = new Knoten(elem[2].trim(), Integer.parseInt(elem[3].trim()));
			} else if(!attributed && weighted && PDEF_WEIGHTED.matcher(line).matches()) {
				String[] elem = line.split("(" + WEIGHT_SEPARATOR + ")|" + KANTEN_SEPARATOR);
				k1 = new Knoten(elem[0].trim());
				k2 = new Knoten(elem[1].trim());
				weight = Integer.parseInt(elem[2].trim());
			} else if(attributed && weighted && PDEF_WEIGHTED_ATTRIBUTED.matcher(line).matches()) {
				String[] elem = line.split("(" + WEIGHT_SEPARATOR + ")|" + KANTEN_SEPARATOR +  "|" + ATTRIBUTE_SEPARATOR);
				k1 = new Knoten(elem[0].trim(), Integer.parseInt(elem[1].trim()));
				k2 = new Knoten(elem[2].trim(), Integer.parseInt(elem[3].trim()));
				weight = Integer.parseInt(elem[4].trim());
			} else {
				throw new GraphParseException("Couldn't parse definition line " + currentLine + ": " + line);
			}
			graph.addVertex(k1);
			graph.addVertex(k2);
			try {
				DefaultEdge edge = graph.addEdge(k1, k2);
				if(weighted) {
					if(edge == null) { // Die Kante wurde bereits hinzugefügt
						edge = graph.getEdge(k1, k2);
						// TODO: Warnung loggen, da möglicherweise das weight überschrieben wird 
					}
					((WeightedGraph<Knoten, DefaultEdge>)graph).setEdgeWeight(edge, weight);
				}
			} catch(IllegalArgumentException e) {
				throw new GraphParseException("Couldn't parse definition line " + currentLine + ": " + line, e);
			}
		}
	}
	
	/**
	 * Erzeugt den korrekten Graphen für die übergebene Konfiguration.
	 * @param directed Ob der zu erzeugende Graph gerichtet sein soll
	 * @param weighted Ob der zu erzeugende Graph gewichtet sein soll
	 * @return Ein Graph, der der gewünschten Konfiguration entspricht
	 */
	private Graph<Knoten, DefaultEdge> createGraph(boolean directed, boolean weighted) {
		if(directed && weighted) {
			return new ListenableDirectedWeightedGraph<Knoten, DefaultEdge>(GewichteteKante.class);
		} else if(directed && !weighted) {
			return new ListenableDirectedGraph<Knoten, DefaultEdge>(Kante.class);
		} else if(!directed && weighted) {
			return new ListenableUndirectedWeightedGraph<Knoten, DefaultEdge>(GewichteteKante.class);
		} else {
			return new ListenableUndirectedGraph<Knoten, DefaultEdge>(Kante.class);
		}
	}
}
