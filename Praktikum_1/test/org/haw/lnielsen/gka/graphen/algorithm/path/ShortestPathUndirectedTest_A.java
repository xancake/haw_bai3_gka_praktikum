package org.haw.lnielsen.gka.graphen.algorithm.path;

import static org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPathAsserts.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.generator.KnotenFactory;
import org.haw.lnielsen.gka.graphen.io.loader.GKAGraphParser;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.RandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;
import org.junit.Before;
import org.junit.Test;

/**
 * Allgemeine Testfälle für Shortest-Path-Algorithmen für ungerichtete Graphen.
 * 
 * @author Lars Nielsen
 */
public abstract class ShortestPathUndirectedTest_A {
	private ShortestPath_I<Knoten, DefaultEdge> myShortestPathAlgorithm;
	
	@Before
	public void setUp() {
		myShortestPathAlgorithm = createShortestPathAlgorithm();
	}
	
	@Test
	public void testCalculatePath_Aufgabe_Undirected_HusumHamburg() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp3 - umlaute and empty lines.graph"));
		Knoten start = new Knoten("Husum", 120);
		Knoten destination = new Knoten("Hamburg", 0);
		
		GraphPath<Knoten, DefaultEdge> shortestPath = myShortestPathAlgorithm.calculatePath(graph, start, destination);
		
		assertGraphPath(Arrays.asList(
				start,
				new Knoten("Kiel", 86),
				new Knoten("Uelzen", 94),
				new Knoten("Rotenburg", 63),
				new Knoten("Soltau", 63),
				new Knoten("Buxtehude", 20),
				destination
		), 518, shortestPath);
	}
	
	@Test
	public void testCalculatePath_Aufgabe_Undirected_MindenHamburg() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp3 - umlaute and empty lines.graph"));
		Knoten start = new Knoten("Minden", 157);
		Knoten destination = new Knoten("Hamburg", 0);
		
		GraphPath<Knoten, DefaultEdge> shortestPath = myShortestPathAlgorithm.calculatePath(graph, start, destination);
		
		assertGraphPath(Arrays.asList(
				start,
				new Knoten("Walsrode", 81),
				destination
		), 227, shortestPath);
	}
	
	@Test
	public void testCalculatePath_Aufgabe_Undirected_MuensterHamburg() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp3 - umlaute and empty lines.graph"));
		Knoten start = new Knoten("Münster", 237);
		Knoten destination = new Knoten("Hamburg", 0);
		
		GraphPath<Knoten, DefaultEdge> shortestPath = myShortestPathAlgorithm.calculatePath(graph, start, destination);
		
		assertGraphPath(Arrays.asList(
				start,
				new Knoten("Bremen", 95),
				destination
		), 300, shortestPath);
	}
	
	@Test
	public void testCalculatePath_Undirected_HamburgBuxtehudeSoltau() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp3 - umlaute and empty lines.graph"));
		Knoten start = new Knoten("Hamburg", 0);
		Knoten destination = new Knoten("Soltau", 63);
		
		GraphPath<Knoten, DefaultEdge> shortestPath = myShortestPathAlgorithm.calculatePath(graph, start, destination);
		
		assertGraphPath(Arrays.asList(
				start,
				new Knoten("Buxtehude", 20), 
				destination
		), 97, shortestPath);
	}
	
	@Test
	public void testCalculatePath_Undirected_KielUelzenHammelnDetmold() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp3 - umlaute and empty lines.graph"));
		Knoten start = new Knoten("Kiel", 86);
		Knoten destination = new Knoten("Detmold", 195);
		
		GraphPath<Knoten, DefaultEdge> shortestPath = myShortestPathAlgorithm.calculatePath(graph, start, destination);
		
		assertGraphPath(Arrays.asList(
				start,
				new Knoten("Uelzen", 94),
				new Knoten("Hameln", 166),
				destination
		), 395, shortestPath);
	}
	
	@Test
	public void testCalculatePath_Unirected_NoPath() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp6 - undirected numbers as names and single vertices.graph"));
		Knoten start = new Knoten("12");
		Knoten destination = new Knoten("9");
		
		GraphPath<Knoten, DefaultEdge> shortestPath = myShortestPathAlgorithm.calculatePath(graph, start, destination);
		
		assertNull(shortestPath);
	}
	
	@Test
	public void testCalculatePath_Aufgabe_Random_100_4000() throws Exception {
		GraphGenerator<Knoten, DefaultEdge, Knoten> generator = new RandomGraphGenerator<>(100, 4000);
		Graph<Knoten, DefaultEdge> graph = new ListenableUndirectedWeightedGraph<Knoten, DefaultEdge>(DefaultEdge.class);
		generator.generateGraph(graph, new KnotenFactory(), null);
		
		Knoten start = new Knoten("0");
		Knoten destination = new Knoten("99");
		
		GraphPath<Knoten, DefaultEdge> jgraphtPath = new DijkstraShortestPath<Knoten, DefaultEdge>(graph, start, destination).getPath();
		GraphPath<Knoten, DefaultEdge> larsPath = myShortestPathAlgorithm.calculatePath(graph, start, destination);
		assertGraphPath(jgraphtPath, larsPath);
	}
	
	/**
	 * Fabrikmethode für erbende Tests um ihre entsprechende Implementation des Algorithmus
	 * bereitzustellen.
	 * @return Der Algorithmus
	 */
	protected abstract ShortestPath_I<Knoten, DefaultEdge> createShortestPathAlgorithm();
}
