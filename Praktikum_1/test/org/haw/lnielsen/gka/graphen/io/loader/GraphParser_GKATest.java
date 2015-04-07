package org.haw.lnielsen.gka.graphen.io.loader;

import static org.junit.Assert.*;

import java.io.IOException;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_GKA;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_I;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse zum {@link GraphParser_GKA}.
 * 
 * @author Lars Nielsen
 * @author Jennifer Momsen
 */
public class GraphParser_GKATest {
	private GraphParser_I myGraphParser;
	
	@Before
	public void setUp() {
		myGraphParser = new GraphParser_GKA();
	}
	
	@Test
	public void testParseGraph_Undirected() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/undirected.graph"));
		assertEquals(5, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a")));
		assertTrue(graph.containsVertex(new Knoten("b")));
		assertTrue(graph.containsVertex(new Knoten("c")));
		assertTrue(graph.containsVertex(new Knoten("d")));
		assertTrue(graph.containsVertex(new Knoten("e")));
		assertEquals(5, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("c")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("d")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("e")));
		assertTrue(graph.containsEdge(new Knoten("d"), new Knoten("e")));
	}
	
	@Test
	public void testParseGraph_UndirectedWeighted() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/undirected weighted.graph"));
		assertEquals(5, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a")));
		assertTrue(graph.containsVertex(new Knoten("b")));
		assertTrue(graph.containsVertex(new Knoten("c")));
		assertTrue(graph.containsVertex(new Knoten("d")));
		assertTrue(graph.containsVertex(new Knoten("e")));
		assertEquals(5, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("c")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("d")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("e")));
		assertTrue(graph.containsEdge(new Knoten("d"), new Knoten("e")));
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("a"), new Knoten("b"))), 0);
		assertEquals(50, graph.getEdgeWeight(graph.getEdge(new Knoten("a"), new Knoten("c"))), 0);
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("b"), new Knoten("d"))), 0);
		assertEquals(20, graph.getEdgeWeight(graph.getEdge(new Knoten("b"), new Knoten("e"))), 0);
		assertEquals(40, graph.getEdgeWeight(graph.getEdge(new Knoten("d"), new Knoten("e"))), 0);
	}
	
	@Test
	public void testParseGraph_UndirectedAttributed() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/undirected attributed.graph"));
		assertEquals(5, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a",10)));
		assertTrue(graph.containsVertex(new Knoten("b",20)));
		assertTrue(graph.containsVertex(new Knoten("c",30)));
		assertTrue(graph.containsVertex(new Knoten("d",100)));
		assertTrue(graph.containsVertex(new Knoten("e",40)));
		assertEquals(5, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a",10), new Knoten("c",30)));
		assertTrue(graph.containsEdge(new Knoten("b",20), new Knoten("c",30)));
		assertTrue(graph.containsEdge(new Knoten("c",30), new Knoten("e",40)));
		assertTrue(graph.containsEdge(new Knoten("c",30), new Knoten("d",100)));
		assertTrue(graph.containsEdge(new Knoten("d",100), new Knoten("e",40)));
	}
	
	@Test
	public void testParseGraph_UndirectedWeightedAttributed() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/undirected weighted attributed.graph"));
		assertEquals(4, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a", 10)));
		assertTrue(graph.containsVertex(new Knoten("b", 100)));
		assertTrue(graph.containsVertex(new Knoten("c", 30)));
		assertTrue(graph.containsVertex(new Knoten("d", 20)));
		assertEquals(3, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a", 10), new Knoten("b", 100)));
		assertTrue(graph.containsEdge(new Knoten("a", 10), new Knoten("c", 30)));
		assertTrue(graph.containsEdge(new Knoten("b", 100), new Knoten("d", 20)));
		assertEquals(1, graph.getEdgeWeight(graph.getEdge(new Knoten("a",10), new Knoten("b",100))), 0);
		assertEquals(3, graph.getEdgeWeight(graph.getEdge(new Knoten("a",10), new Knoten("c",30))), 0);
		assertEquals(2, graph.getEdgeWeight(graph.getEdge(new Knoten("b",100), new Knoten("d",20))), 0);	
	}
	
	@Test
	public void testParseGraph_Directed() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/directed.graph"));
		assertEquals(5, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a")));
		assertTrue(graph.containsVertex(new Knoten("b")));
		assertTrue(graph.containsVertex(new Knoten("c")));
		assertTrue(graph.containsVertex(new Knoten("d")));
		assertTrue(graph.containsVertex(new Knoten("e")));
		assertEquals(7, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("c")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("d")));
		assertTrue(graph.containsEdge(new Knoten("c"), new Knoten("a")));
		assertTrue(graph.containsEdge(new Knoten("d"), new Knoten("e")));
		assertTrue(graph.containsEdge(new Knoten("e"), new Knoten("b")));
	}
	
	@Test
	public void testParseGraph_DirectedWeighted() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/directed weighted.graph"));
		assertEquals(5, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a")));
		assertTrue(graph.containsVertex(new Knoten("b")));
		assertTrue(graph.containsVertex(new Knoten("c")));
		assertTrue(graph.containsVertex(new Knoten("d")));
		assertTrue(graph.containsVertex(new Knoten("e")));
		assertEquals(7, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("a"), new Knoten("c")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("b")));
		assertTrue(graph.containsEdge(new Knoten("b"), new Knoten("d")));
		assertTrue(graph.containsEdge(new Knoten("c"), new Knoten("a")));
		assertTrue(graph.containsEdge(new Knoten("d"), new Knoten("e")));
		assertTrue(graph.containsEdge(new Knoten("e"), new Knoten("b")));
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("a"), new Knoten("b"))), 0);
		assertEquals(50, graph.getEdgeWeight(graph.getEdge(new Knoten("a"), new Knoten("c"))), 0);
		assertEquals(30, graph.getEdgeWeight(graph.getEdge(new Knoten("b"), new Knoten("b"))), 0);
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("b"), new Knoten("d"))), 0);
		assertEquals(30, graph.getEdgeWeight(graph.getEdge(new Knoten("c"), new Knoten("a"))), 0);
		assertEquals(40, graph.getEdgeWeight(graph.getEdge(new Knoten("d"), new Knoten("e"))), 0);
		assertEquals(20, graph.getEdgeWeight(graph.getEdge(new Knoten("e"), new Knoten("b"))), 0);
	}
	
	@Test
	public void testParseGraph_DirectedAttributed() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/directed attributed.graph"));
		assertEquals(4, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a", 10)));
		assertTrue(graph.containsVertex(new Knoten("b", 50)));
		assertTrue(graph.containsVertex(new Knoten("c", 25)));
		assertTrue(graph.containsVertex(new Knoten("d", 60)));
		assertEquals(4, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a", 10), new Knoten("c", 25)));
		assertTrue(graph.containsEdge(new Knoten("b", 50), new Knoten("c", 25)));
		assertTrue(graph.containsEdge(new Knoten("c", 25), new Knoten("b", 50)));
		assertTrue(graph.containsEdge(new Knoten("c", 25), new Knoten("d", 60)));
	}
	
	@Test
	public void testParseGraph_DirectedWeightedAttributed() throws Exception {
		Graph<Knoten, DefaultEdge> graph = myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/directed weighted attributed.graph"));
		assertEquals(3, graph.vertexSet().size());
		assertTrue(graph.containsVertex(new Knoten("a", 20)));
		assertTrue(graph.containsVertex(new Knoten("b", 40)));
		assertTrue(graph.containsVertex(new Knoten("c", 30)));
		assertEquals(3, graph.edgeSet().size());
		assertTrue(graph.containsEdge(new Knoten("a", 20), new Knoten("c", 30)));
		assertTrue(graph.containsEdge(new Knoten("c", 30), new Knoten("a", 20)));
		assertTrue(graph.containsEdge(new Knoten("b", 40), new Knoten("c", 30)));
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("a", 20), new Knoten("c", 30))),0);
		assertEquals(10, graph.getEdgeWeight(graph.getEdge(new Knoten("c", 30), new Knoten("a", 20))),0);
		assertEquals(60, graph.getEdgeWeight(graph.getEdge(new Knoten("b", 40), new Knoten("c", 30))),0);	
	}
}
