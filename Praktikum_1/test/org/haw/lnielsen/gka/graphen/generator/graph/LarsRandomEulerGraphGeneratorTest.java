package org.haw.lnielsen.gka.graphen.generator.graph;

import static org.junit.Assert.*;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.haw.lnielsen.gka.graphen.generator.vertex.KnotenFactory;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

public class LarsRandomEulerGraphGeneratorTest {
	/**
	 * Testet das Generieren eines Eulergraphen mit drei Knoten, da ein Eulergraph mit weniger Knoten ohne
	 * Mehrfachkanten bzw. Schlaufen nicht möglich ist.
	 */
	@Test
	public void testGenerate_Undirected_MinimalPossibleGraph() throws Exception {
		testGenerate_Undirected(3);
	}
	
	/**
	 * Testet das Generieren eines Eulergraphen mit vier Knoten, da dies für den Algorithmus besonders kritisch ist.
	 * Aufgrund der Arbeitsweise kann es vorkommen, dass ein Knoten mit allen anderen Knoten verbunden ist und somit
	 * kein Eulerkreis aus dem Graphen gemacht werden kann.
	 */
	@Test
	public void testGenerate_Undirected_4() throws Exception {
		for(int i=0; i<1000; i++) {
			testGenerate_Undirected(4);
		}
	}
	
	/**
	 * Testet das Generieren von Eulergraphen einer Knotenanzahl von 3 bis 2.000.
	 */
	@Test
	public void testGenerate_Undirected_Increase() throws Exception {
		for(int i=3; i<2000; i++) {
			testGenerate_Undirected(i);
		}
	}
	
	private void testGenerate_Undirected(int vertices) throws Exception {
		Graph<Knoten, DefaultEdge> graph = GraphFactory.createGraph(false, false);
		
		LarsRandomEulerGraphGenerator<Knoten, DefaultEdge> generator = new LarsRandomEulerGraphGenerator<Knoten, DefaultEdge>(vertices);
		generator.generateGraph(graph, new KnotenFactory(), null);
		
		assertEquals("Die Anzahl der Knoten stimmt nicht überein!", vertices, graph.vertexSet().size());
		assertTrue("Der Graph ist kein Eulergraph (" + vertices + " Knoten)!", EulerianCircuit.isEulerian((UndirectedGraph<Knoten, DefaultEdge>)graph));
	}
}
