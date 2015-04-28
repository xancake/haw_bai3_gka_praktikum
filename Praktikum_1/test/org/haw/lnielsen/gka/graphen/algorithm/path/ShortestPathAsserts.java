package org.haw.lnielsen.gka.graphen.algorithm.path;

import static org.junit.Assert.*;

import java.util.List;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;

/**
 * Util-Klasse für asserts bezüglich der Shortest-Path-Algorithmen
 * 
 * @author Lars Nielsen
 */
public class ShortestPathAsserts {
	private ShortestPathAsserts() {}
	
	/**
	 * Prüft, ob der GraphPath korrekt aufgebaut wurde.
	 * @param expectedVertices Die Liste der erwarteten Knoten
	 * @param expectedWeight Das erwartete Gewicht
	 * @param actualPath Der zu überprüfende Path
	 */
	public static <V, E> void assertGraphPath(List<V> expectedVertices, double expectedWeight, GraphPath<V, E> actualPath) throws Exception {
		assertNotNull(actualPath);
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
	
	/**
	 * Prüft, ob der GraphPath korrekt aufgebaut wurde.
	 * @param expectedPath Der erwartete Path
	 * @param actualPath Der zu prüfende Path
	 */
	public static void assertGraphPath(GraphPath<Knoten, DefaultEdge> expectedPath, GraphPath<Knoten, DefaultEdge> actualPath) {
		assertNotNull(actualPath);
		assertEquals(expectedPath.getStartVertex(), actualPath.getStartVertex());
		assertEquals(expectedPath.getEndVertex(), actualPath.getEndVertex());
		assertEquals(expectedPath.getWeight(), actualPath.getWeight(), 0);
		
		List<DefaultEdge> expectedList = expectedPath.getEdgeList();
		List<DefaultEdge> actualList   = actualPath.getEdgeList();
		assertEquals(expectedList.size(), actualList.size());
		for(int i=0; i<expectedList.size(); i++) {
			assertEquals(expectedList.get(i), actualList.get(i));
		}
	}
}
