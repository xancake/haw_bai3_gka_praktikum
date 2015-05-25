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
	
	/**
	 * Erzeugt einen neuen Graphen mit den gleichen Eigenschaften des übergebenen Graphen.
	 * Die kopierten Eigenschaften sind ob der Graph gerichtet oder gewichtet ist.
	 * @param graph Der Graph, dessen Eigenschaften kopiert werden sollen
	 * @return Ein neuer, leerer Graph mit den gleichen Eigenschaften wie denen des übergebenen Graphen
	 */
	public static Graph<Knoten, DefaultEdge> createGraph(Graph<Knoten, DefaultEdge> graph) {
		return createGraph(graph instanceof DirectedGraph, graph instanceof WeightedGraph);
	}
	
	/**
	 * Erzeugt einen {@link ZugriffszaehlenderGraph zugriffszählenden Graphen} der den übergebenen Graphen
	 * wrapped, um ihn mit der Funktionalität zum Messen von Zugriffen zu erweitern. Dabei leitet der
	 * zugriffszählende Graph die Methodenaufrufe nur an den eigentlichen Graph weiter.
	 * @param graph Der eigentliche Graph
	 * @return Der {@link ZugriffszaehlenderGraph}
	 */
	public static ZugriffszaehlenderGraph<Knoten, DefaultEdge> createZugriffszaehlenderGraph(Graph<Knoten, DefaultEdge> graph) {
		return graph instanceof DirectedGraph
				? new ZugriffszaehlenderGerichteterGraph<Knoten, DefaultEdge>((DirectedGraph<Knoten, DefaultEdge>)graph)
				: new ZugriffszaehlenderGraph<Knoten, DefaultEdge>(graph);
	}
}
