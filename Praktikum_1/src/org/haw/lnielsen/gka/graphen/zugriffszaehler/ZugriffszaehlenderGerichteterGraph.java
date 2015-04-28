package org.haw.lnielsen.gka.graphen.zugriffszaehler;

import java.util.Set;

import org.jgrapht.DirectedGraph;

public class ZugriffszaehlenderGerichteterGraph<V, E> extends ZugriffszaehlenderGraph<V, E> implements DirectedGraph<V, E> {
	private DirectedGraph<V, E> myDirectedGraph;
	
	public ZugriffszaehlenderGerichteterGraph(DirectedGraph<V, E> graph) {
		super(graph);
		myDirectedGraph = graph;
	}

	@Override
	public Set<E> incomingEdgesOf(V vertex) {
		myZugriffsZaehler++;
		return myDirectedGraph.incomingEdgesOf(vertex);
	}

	@Override
	public Set<E> outgoingEdgesOf(V vertex) {
		myZugriffsZaehler++;
		return myDirectedGraph.outgoingEdgesOf(vertex);
	}
	
	@Override
	public int inDegreeOf(V vertex) {
		return myDirectedGraph.inDegreeOf(vertex);
	}

	@Override
	public int outDegreeOf(V vertex) {
		return myDirectedGraph.outDegreeOf(vertex);
	}
}
