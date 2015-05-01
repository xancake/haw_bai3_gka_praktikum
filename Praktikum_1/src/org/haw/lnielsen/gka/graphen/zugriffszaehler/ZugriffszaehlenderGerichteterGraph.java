package org.haw.lnielsen.gka.graphen.zugriffszaehler;

import java.util.Set;

import org.jgrapht.DirectedGraph;

/**
 * Eine Klasse die es ermöglicht, Zugriffe auf einen gerichteten Graphen zu zählen.
 * Ein Objekt dieser Klasse umschließt einen Graphen und delegiert die Methodenaufrufe
 * an das interne Objekt. Dabei wird bei allen Methodenaufrufen ein Zähler hochgezählt.
 * 
 * @author Lars Nielsen
 */
public class ZugriffszaehlenderGerichteterGraph<V, E> extends ZugriffszaehlenderGraph<V, E> implements DirectedGraph<V, E> {
	private DirectedGraph<V, E> myDirectedGraph;
	
	/**
	 * Initialisiert den zugriffszählenden Graphen mit dem übergebenen Graphen.
	 * @param graph Ein Graph, darf nicht {@code null} sein
	 */
	public ZugriffszaehlenderGerichteterGraph(DirectedGraph<V, E> graph) {
		super(graph);
		myDirectedGraph = graph;
	}

	@Override
	public Set<E> incomingEdgesOf(V vertex) {
		myLesenZaehler++;
		return myDirectedGraph.incomingEdgesOf(vertex);
	}

	@Override
	public Set<E> outgoingEdgesOf(V vertex) {
		myLesenZaehler++;
		return myDirectedGraph.outgoingEdgesOf(vertex);
	}
	
	@Override
	public int inDegreeOf(V vertex) {
		myLesenZaehler++;
		return myDirectedGraph.inDegreeOf(vertex);
	}

	@Override
	public int outDegreeOf(V vertex) {
		myLesenZaehler++;
		return myDirectedGraph.outDegreeOf(vertex);
	}
}
