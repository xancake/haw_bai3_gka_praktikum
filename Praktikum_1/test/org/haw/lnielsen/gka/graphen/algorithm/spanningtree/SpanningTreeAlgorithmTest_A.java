package org.haw.lnielsen.gka.graphen.algorithm.spanningtree;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.junit.Before;
import org.junit.Test;

public abstract class SpanningTreeAlgorithmTest_A {
	private SpanningTreeAlgorithm_I<Knoten, DefaultEdge> myAlgorithm;
	
 	@Before
	public void setUp() {
		myAlgorithm = createSpanningTreeAlgorithm();
	}
	
	@Test
	public void testCalculateSpanningTree_() throws Exception {
		Graph<Knoten, DefaultEdge> graph = null;
		Graph<Knoten, DefaultEdge> spanningTree = GraphFactory.createGraph(graph instanceof DirectedGraph, graph instanceof WeightedGraph);
		myAlgorithm.calculateSpanningTree(graph, spanningTree);
		
	}
	
	protected abstract SpanningTreeAlgorithm_I<Knoten, DefaultEdge> createSpanningTreeAlgorithm();
}
