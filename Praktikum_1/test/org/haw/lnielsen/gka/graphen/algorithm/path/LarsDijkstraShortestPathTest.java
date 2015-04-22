package org.haw.lnielsen.gka.graphen.algorithm.path;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.io.loader.GKAGraphParser;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;

public class LarsDijkstraShortestPathTest {
	private ShortestPath_I myShortestPathAlgorithm;
	
	@Before
	public void setUp() {
		myShortestPathAlgorithm = createShortestPathAlgorithm();
	}
	
	@Test
	public void testCalculatePath_Undirected_Aufgabe_HusumHamburg() throws Exception {
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
	public void testCalculatePath_Undirected_Aufgabe_MindenHamburg() throws Exception {
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
	public void testCalculatePath_Undirected_Aufgabe_MuensterHamburg() throws Exception {
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
	
	// TODO: Testfälle für kein Weg vorhanden
	// TODO: Testfälle für directed Graphennew HashSet<V>(graph.vertexSet())
	
	private <V, E> void assertGraphPath(List<V> expectedVertices, double expectedWeight, GraphPath<V, E> actualPath) throws Exception {
		assertEquals(expectedWeight, actualPath.getWeight(), 0);
		
		List<E> edgeList = actualPath.getEdgeList();
		assertEquals(expectedVertices.size()-1, edgeList.size());
		
		Graph<V, E> graph = actualPath.getGraph();
		for(int i=0; i<edgeList.size(); i++) {
			V edgeSource = graph.getEdgeSource(edgeList.get(i));
			V edgeTarget = graph.getEdgeTarget(edgeList.get(i));
			if(expectedVertices.get(i).equals(edgeSource)) {
				assertEquals(expectedVertices.get(i), edgeSource);
				assertEquals(expectedVertices.get(i+1), edgeTarget);
			} else {
				assertEquals(expectedVertices.get(i), edgeTarget);
				assertEquals(expectedVertices.get(i+1), edgeSource);
			}
		}
	}
	
	protected ShortestPath_I createShortestPathAlgorithm() {
		return new LarsDijkstraShortestPath();
	}
}
