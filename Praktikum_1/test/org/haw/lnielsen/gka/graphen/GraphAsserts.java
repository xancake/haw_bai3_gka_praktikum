package org.haw.lnielsen.gka.graphen;

import static org.junit.Assert.*;

import java.util.Set;

import org.haw.lnielsen.gka.graphen.util.GraphUtils;
import org.jgrapht.Graph;

/**
 * Util-Klasse für Asserts auf Graphen.
 * 
 * @author Lars 
 */
public class GraphAsserts {
	private GraphAsserts() {}
	
	public static <V, E> void assertEqualsGraph(Graph<V, E> expected, Graph<V, E> actual) {
		Set<V> expectedVertices = expected.vertexSet();
		Set<V> actualVertices   = actual.vertexSet();
		assertEquals(expectedVertices.size(), actualVertices.size());
		for(V vertex : expectedVertices) {
			actual.containsVertex(vertex);
		}
		
		Set<E> expectedEdges = expected.edgeSet();
		Set<E> actualEdges   = actual.edgeSet();
		assertEquals(expectedEdges.size(), actualEdges.size());
		for(E edge : expectedEdges) {
			V source = expected.getEdgeSource(edge);
			V target = expected.getEdgeTarget(edge);
			actual.containsEdge(source, target);
		}
	}
	
	public static <V, E> void assertGraphWeight(double expectedWeight, Graph<V, E> graph) {
		assertEquals(expectedWeight, GraphUtils.calculateGraphWeight(graph), 0);
	}
}
