package org.haw.lnielsen.gka.graphen.generator;

import org.haw.lnielsen.gka.graphen.GewichteteKante;
import org.haw.lnielsen.gka.graphen.Kante;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.zugriffszaehler.ZugriffszaehlenderGerichteterGraph;
import org.haw.lnielsen.gka.graphen.zugriffszaehler.ZugriffszaehlenderGraph;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

/**
 * Erzeugt Graphen, Knoten und Kanten im Rahmen dieses Projekts.
 * 
 * @author Lars Nielsen
 */
public class GraphFactory {
	private GraphFactory() {}
	
	/**
	 * Erzeugt den korrekten Graphen für die übergebene Konfiguration.
	 * @param directed Ob der zu erzeugende Graph gerichtet sein soll
	 * @param weighted Ob der zu erzeugende Graph gewichtet sein soll
	 * @return Ein Graph, der der gewünschten Konfiguration entspricht
	 */
	public static Graph<Knoten, DefaultEdge> createGraph(boolean directed, boolean weighted) {
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
	
	public static Graph<Knoten, DefaultEdge> createGraph(Graph<Knoten, DefaultEdge> graph) {
		return createGraph(graph instanceof DirectedGraph, graph instanceof WeightedGraph);
	}
	
	public static ZugriffszaehlenderGraph<Knoten, DefaultEdge> createZugriffszaehlenderGraph(Graph<Knoten, DefaultEdge> graph) {
		return graph instanceof DirectedGraph
				? new ZugriffszaehlenderGerichteterGraph<Knoten, DefaultEdge>((DirectedGraph<Knoten, DefaultEdge>)graph)
				: new ZugriffszaehlenderGraph<Knoten, DefaultEdge>(graph);
	}
}
