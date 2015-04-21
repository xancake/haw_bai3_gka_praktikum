package org.haw.lnielsen.gka.graphen.algorithm.path;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_GKA;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

public class LarsDijkstraShortestPathTest {
	@Test
	public void testCalculatePath_HamburgBuxtehudeSoltau() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GraphParser_GKA().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp3 - umlaute and empty lines.graph"));
		Knoten start = new Knoten("Hamburg", 0);
		Knoten destination = new Knoten("Soltau", 63);
		
		GraphPath<Knoten, DefaultEdge> shortestPath = new LarsDijkstraShortestPath().calculatePath(graph, start, destination);
		
		assertGraphPath(Arrays.asList(
				start,
				new Knoten("Buxtehude", 20), 
				destination
		), 97, shortestPath);
	}
	
	@Test
	public void testCalculatePath_KielUelzenHammelnDetmold() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GraphParser_GKA().parseGraph(ClassLoader.getSystemResourceAsStream("loader/bsp/bsp3 - umlaute and empty lines.graph"));
		Knoten start = new Knoten("Kiel", 86);
		Knoten destination = new Knoten("Detmold", 195);
		
		GraphPath<Knoten, DefaultEdge> shortestPath = new LarsDijkstraShortestPath().calculatePath(graph, start, destination);
		
		assertGraphPath(Arrays.asList(
				start,
				new Knoten("Uelzen", 94),
				new Knoten("Hameln", 166),
				destination
		), 395, shortestPath);
	}
	
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
}
