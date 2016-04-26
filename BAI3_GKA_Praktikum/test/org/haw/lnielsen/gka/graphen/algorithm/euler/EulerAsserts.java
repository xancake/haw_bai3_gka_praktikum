package org.haw.lnielsen.gka.graphen.algorithm.euler;

import static org.junit.Assert.*;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

/**
 * Util-Klasse für asserts bezüglich der Euler-Algorithmen.
 *  
 * @author Lars Nielsen
 * @author Jennifer Momsen
 */
public class EulerAsserts {
	private EulerAsserts() {}
	
	/**
	 * Prüft, ob der übergebene {@link GraphPath} eine Euler-Tour repräsentiert.
	 * @param path Der zu testende {@link GraphPath}
	 */
	public static <V, E> void assertEulerTour(GraphPath<V, E> path) {
		assertEquals(path.getStartVertex(), path.getEndVertex());
		assertEulerPath(path);
	}
	
	/**
	 * Prüft, ob der übergebene {@link GraphPath} einen Euler-Pfad repräsentiert.
	 * @param path Der zu testende {@link GraphPath}
	 */
	public static <V, E> void assertEulerPath(GraphPath<V, E> path) {
		Graph<V, E> graph = path.getGraph();
		for(E edge : graph.edgeSet()) {
			assert(path.getEdgeList().contains(edge));
		}
		
		V previous = path.getStartVertex();
		for(E edge : path.getEdgeList()) {
			V source = graph.getEdgeSource(edge);
			V target = graph.getEdgeTarget(edge);
			if(previous.equals(source)) {
				previous = target;
			} else if(previous.equals(target)) {
				previous = source;
			} else {
				fail("Vorgängerknoten ist nicht in der aktuell betrachteten Kante enthalten!");
			}
		}
		assertEquals(previous, path.getEndVertex());
	}
}
