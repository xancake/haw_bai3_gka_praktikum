package org.haw.lnielsen.gka.graphen.algorithm.euler;

import static org.haw.lnielsen.gka.graphen.algorithm.euler.EulerAsserts.*;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.io.loader.GKAGraphParser;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Test;

public class LarsHierholzerAlgorithmTest extends EulerAlgorithmTest_A {
	@Test
	public void testEulerGraph_Lars_1() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/euler/test_eulergraph_lars_1.graph"));
		GraphPath<Knoten, DefaultEdge> eulerpath = createEulerAlgorithm().findEulerTour(graph);
		assertEulerTour(eulerpath);
	}
	
	@Override
	protected EulerAlgorithm_I<Knoten, DefaultEdge> createEulerAlgorithm() {
		return new LarsHierholzerAlgorithm<Knoten, DefaultEdge>();
	}
}
