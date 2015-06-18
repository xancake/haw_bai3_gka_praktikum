package org.haw.lnielsen.gka.graphen.algorithm.euler;

import static org.haw.lnielsen.gka.graphen.algorithm.euler.EulerAsserts.*;
import static org.junit.Assert.*;
import java.io.FileOutputStream;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.haw.lnielsen.gka.graphen.generator.graph.IncrementalEulerGraphGenerator;
import org.haw.lnielsen.gka.graphen.generator.vertex.KnotenFactory;
import org.haw.lnielsen.gka.graphen.io.loader.GKAGraphParser;
import org.haw.lnielsen.gka.graphen.io.store.GKAGraphStorer;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;

/**
 * Allgemeine Testfälle für Euler-Algorithmen.
 * 
 * @author Lars Nielsen
 * @author Jennifer Momsen
 */
public abstract class EulerAlgorithmTest_A {
	private EulerAlgorithm_I<Knoten, DefaultEdge> myAlgorithm;
	
	@Before
	public void setUp() {
		myAlgorithm = createEulerAlgorithm();
	}
	
	@Test
	public void testFindEulerGraph_EmptyGraph() throws Exception {
		Graph<Knoten, DefaultEdge> graph = GraphFactory.createGraph(false, false);
		GraphPath<Knoten, DefaultEdge> path = myAlgorithm.findEulerTour(graph);
		assertNull(path);
	}
	
	@Test
	public void testFindEulerTour_Eulerpfad_Nikolaushaus() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/euler/eulerpfad_nikolaushaus.graph"));
		GraphPath<Knoten, DefaultEdge> eulerpath = myAlgorithm.findEulerTour(graph);
		assertNull(eulerpath);
	}
	
	@Test
	public void testFindEulerTour_Eulertour_1() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/euler/eulertour_1.graph"));
		GraphPath<Knoten, DefaultEdge> eulerpath = myAlgorithm.findEulerTour(graph);
		assertEulerTour(eulerpath);
	}
	
	@Test
	public void testFindEulerTour_Eulertour_2() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/euler/eulertour_2.graph"));
		GraphPath<Knoten, DefaultEdge> eulerpath = myAlgorithm.findEulerTour(graph);
		assertEulerTour(eulerpath);
	}
	@Test
	public void testFindEulerTour_Test_Lars_1() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/euler/test_eulergraph_lars_1.graph"));
		GraphPath<Knoten, DefaultEdge> eulerpath = createEulerAlgorithm().findEulerTour(graph);
		assertEulerTour(eulerpath);
	}
	
	@Test
	public void testFindEulerTour_NoEulertour_1() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/euler/noeulertour_1.graph"));
		GraphPath<Knoten, DefaultEdge> eulerpath = myAlgorithm.findEulerTour(graph);
		assertNull(eulerpath);
	}
	
	@Test
	public void testFindEulerTour_NoEulertour_2() throws Exception {
		Graph<Knoten, DefaultEdge> graph = new GKAGraphParser().parseGraph(ClassLoader.getSystemResourceAsStream("loader/euler/noeulertour_2.graph"));
		GraphPath<Knoten, DefaultEdge> eulerpath = myAlgorithm.findEulerTour(graph);
		assertNull(eulerpath);
	}
	
	@Test
	public void testFindEulerTour_Random() throws Exception {
		Graph<Knoten, DefaultEdge> graph = GraphFactory.createGraph(false, false);
		
		GraphGenerator<Knoten, DefaultEdge, Knoten> generator = new IncrementalEulerGraphGenerator<Knoten, DefaultEdge>((int)(10+Math.random()*(100-10)), -1);
		generator.generateGraph(graph, new KnotenFactory(), null);
		
		GraphPath<Knoten, DefaultEdge> path = myAlgorithm.findEulerTour(graph);
		
		try {
			assertEulerTour(path);
		} catch(AssertionError e) {
			String file = "loader/euler/fehler/v" + graph.vertexSet().size() + "_e" + graph.edgeSet().size() + ".graph";
			new GKAGraphStorer().storeGraph(graph, new FileOutputStream(file));
			fail("Der geprüfte Graph enthält laut Algorithmus keine Eulertour, müsste aber eine enthalten. Der Graph wurde unter '" + file + "' gespeichert, um behoben werden zu können.");
		}
	}
	
	@Test
	public void testFindEulerTour_RandomRepeatedly() throws Exception {
		for(int i=0; i<100; i++) {
			Graph<Knoten, DefaultEdge> graph = GraphFactory.createGraph(false, false);
			
			GraphGenerator<Knoten, DefaultEdge, Knoten> generator = new IncrementalEulerGraphGenerator<Knoten, DefaultEdge>((int)(100+Math.random()*(1000-100)), -1);
			generator.generateGraph(graph, new KnotenFactory(), null);
			
			GraphPath<Knoten, DefaultEdge> path = myAlgorithm.findEulerTour(graph);
			
			try {
				assertEulerTour(path);
			} catch(AssertionError e) {
				String file = "loader/euler/fehler/v" + graph.vertexSet().size() + "_e" + graph.edgeSet().size() + ".graph";
				new GKAGraphStorer().storeGraph(graph, new FileOutputStream(file));
				fail("Der geprüfte Graph enthält laut Algorithmus keine Eulertour, müsste aber eine enthalten. Der Graph wurde unter '" + file + "' gespeichert, um behoben werden zu können.");
			}
		}
	}
	
	/**
	 * Fabrikmethode für erbende Tests um ihre entsprechende Implementierung des Algorithmus
	 * bereitzustellen.
	 * @return Der Algorithmus
	 */
	protected abstract EulerAlgorithm_I<Knoten, DefaultEdge> createEulerAlgorithm();
}
