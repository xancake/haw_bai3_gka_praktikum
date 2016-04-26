package org.haw.lnielsen.gka.graphen.generator.graph;

import static org.junit.Assert.*;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.haw.lnielsen.gka.graphen.generator.vertex.KnotenFactory;
import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

public class IncrementalEulerGraphGeneratorTest {
	/**
	 * Testet das Generieren eines Eulergraphen mit drei Knoten, da ein Eulergraph mit weniger Knoten ohne
	 * Mehrfachkanten bzw. Schlaufen nicht möglich ist.
	 */
	@Test
	public void testGenerate_Undirected_MinimalPossibleGraph() throws Exception {
		testGenerate_Undirected(3, 3);
	}
	
	/**
	 * Testet das Generieren eines Eulergraphen mit drei Knoten, da ein Eulergraph mit weniger Knoten ohne
	 * Mehrfachkanten bzw. Schlaufen nicht möglich ist. Hierbei werden zufällige Kreisgrößen gewählt.
	 */
	@Test
	public void testGenerate_Undirected_MinimalPossibleGraph_RandomCircles() throws Exception {
		testGenerate_Undirected(3, -1);
	}
	
	/**
	 * Testet den Fall, dass die angegebene Kreisgröße kleiner als die Anzahl der Knoten ist.
	 * Hierbei muss ein Viereck herauskommen, anstatt von zwei Dreiecken, die an zwei Eckpunkten
	 * miteinander verbunden sind.
	 */
	@Test
	public void testGenerate_Undirected_4vertices_3circleSize() throws Exception {
		testGenerate_Undirected(4, 3);
	}
	
	/**
	 * Testet das Generieren von Eulergraphen einer Knotenanzahl von 3 bis 100.
	 */
	@Test
	public void testGenerate_Undirected_Increase() throws Exception {
		for(int i=3; i<100; i++) {
			for(int cs=3; cs<i; cs++) {
				testGenerate_Undirected(i, cs, false);
			}
		}
	}
	
	private void testGenerate_Undirected(int vertices, int circleSize) throws Exception {
		testGenerate_Undirected(vertices, circleSize, true);
	}
	
	private void testGenerate_Undirected(int vertices, int circleSize, boolean checkVertexCount) throws Exception {
		Graph<Knoten, DefaultEdge> graph = GraphFactory.createGraph(false, false);
		
		GraphGenerator<Knoten, DefaultEdge, Knoten> generator = new IncrementalEulerGraphGenerator<Knoten, DefaultEdge>(vertices, circleSize);
		generator.generateGraph(graph, new KnotenFactory(), null);
		
		if(checkVertexCount) {
			assertEquals("Die Anzahl der Knoten stimmt nicht überein!", vertices, graph.vertexSet().size());
		}
		assertTrue("Der Graph ist kein Eulergraph (" + vertices + " Knoten)!", EulerianCircuit.isEulerian((UndirectedGraph<Knoten, DefaultEdge>)graph));
	}
}
