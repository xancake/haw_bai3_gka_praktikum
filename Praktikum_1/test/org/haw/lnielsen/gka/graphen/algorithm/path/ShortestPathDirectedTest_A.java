package org.haw.lnielsen.gka.graphen.algorithm.path;

import static org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPathAsserts.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.io.loader.GKAGraphParser;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;

/**
 * Testfälle für Shortest-Path-Algorithmen für gerichtete Graphen.
 * 
 * @author Lars Nielsen
 */
public abstract class ShortestPathDirectedTest_A {
	private ShortestPath_I<Knoten, DefaultEdge> myShortestPathAlgorithm;
	
	@Before
	public void setUp() {
		myShortestPathAlgorithm = createShortestPathAlgorithm();
	}
	
	@Test
	public void testCalculatePath_Directed_1() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp1 - directed.graph"));
		Knoten start = new Knoten("i");
		Knoten destination = new Knoten("f");
		
		GraphPath<Knoten, DefaultEdge> shortestPath = myShortestPathAlgorithm.calculatePath(graph, start, destination);
		
		assertGraphPath(Arrays.asList(
				start,
				new Knoten("c"),
				new Knoten("d"),
				new Knoten("e"),
				destination
		), 4, shortestPath);
	}
	
	@Test
	public void testCalculatePath_Directed_NoPath() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp6 - directed numbers as names and single vertices.graph"));
		Knoten start = new Knoten("12");
		Knoten destination = new Knoten("11");
		
		GraphPath<Knoten, DefaultEdge> shortestPath = myShortestPathAlgorithm.calculatePath(graph, start, destination);
		
		assertNull(shortestPath);
	}
	
	// TODO: weitere Testfälle für gerichtete Graphen
	
	/**
	 * Fabrikmethode für erbende Tests um ihre entsprechende Implementation des Algorithmus
	 * bereitzustellen.
	 * @return Der Algorithmus
	 */
	protected abstract ShortestPath_I<Knoten, DefaultEdge> createShortestPathAlgorithm();
}
