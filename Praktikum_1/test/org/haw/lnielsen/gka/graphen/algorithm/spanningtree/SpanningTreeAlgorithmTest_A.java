package org.haw.lnielsen.gka.graphen.algorithm.spanningtree;

import static org.haw.lnielsen.gka.graphen.GraphAsserts.*;
import static org.junit.Assert.*;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.haw.lnielsen.gka.graphen.io.loader.GKAGraphParser;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;

/**
 * Allgemeine Testfälle für Spanning-Tree-Algorithmen.
 * 
 * @author Lars Nielsen
 */
public abstract class SpanningTreeAlgorithmTest_A {
	private SpanningTreeAlgorithm_I<Knoten, DefaultEdge> myAlgorithm;
	
 	@Before
	public void setUp() {
		myAlgorithm = createSpanningTreeAlgorithm();
	}
	
 	/**
 	 * Test auf einem kompletten Graphen K5. Dem Graphen wurden zufällige Kantengewichte
 	 * zugewiesen deren Summe im minimalen Spannbaum 6.0 betragen.
 	 * Da der K5 eine Komponente ist muss die Anzahl Kanten des Spannbaums der Anzahl
 	 * der Knoten-1 entsprechen.
 	 */
	@Test
	public void testCalculateSpanningTree_Complete5() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/complete5.graph"));
		Graph<Knoten, DefaultEdge> spanningTree = GraphFactory.createGraph(graph instanceof DirectedGraph, graph instanceof WeightedGraph);
		myAlgorithm.calculateSpanningTree(graph, spanningTree);
		assertGraphWeight(6.0, spanningTree);
		assertEquals(spanningTree.vertexSet().size()-1, spanningTree.edgeSet().size());
	}
	
	/**
	 * Test auf einem linearen Graphen mit 5 Knoten. Dem Graphen wurden zufällige
	 * Kantengewichte zugewiesen deren Summe 8.0 betragen. Da die Kanten einen Pfad
	 * (ohne Kreise) durch alle Knoten ergeben, ist der Graph schon selbst der
	 * minimale Spannbaum.
	 */
	@Test
	public void testCalculateSpanningTree_Linear5() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/linear5.graph"));
		Graph<Knoten, DefaultEdge> spanningTree = GraphFactory.createGraph(graph instanceof DirectedGraph, graph instanceof WeightedGraph);
		myAlgorithm.calculateSpanningTree(graph, spanningTree);
		assertGraphWeight(8.0, spanningTree);
		assertEqualsGraph(graph, spanningTree);
	}
	
	/**
	 * Test auf einem Graphen mit zwei Komponenten. Die beiden Komponenten sind für
	 * sich gesehen bereits minimale Spannbäume. 
	 */
	@Test
	public void testCalculateSpanningTree_TwoComponents() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/two_components.graph"));
		Graph<Knoten, DefaultEdge> spanningTree = GraphFactory.createGraph(graph instanceof DirectedGraph, graph instanceof WeightedGraph);
		myAlgorithm.calculateSpanningTree(graph, spanningTree);
		assertGraphWeight(4.0, spanningTree);
		assertEqualsGraph(graph, spanningTree);
	}
	
	/**
	 * Fabrikmethode für erbende Tests um ihre entsprechende Implementation des Algorithmus
	 * bereitzustellen.
	 * @return Der Algorithmus
	 */
	protected abstract SpanningTreeAlgorithm_I<Knoten, DefaultEdge> createSpanningTreeAlgorithm();
}
