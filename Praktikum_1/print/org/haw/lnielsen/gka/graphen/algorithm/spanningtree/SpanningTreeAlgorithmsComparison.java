package org.haw.lnielsen.gka.graphen.algorithm.spanningtree;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.kruskal.LarsKruskalSpanningTree;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.prim.LarsPrimSpanningTree;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.prim.PrimFibonacciHeapSpanningTree;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.haw.lnielsen.gka.graphen.io.loader.GKAGraphParser;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_I;
import org.haw.lnielsen.gka.graphen.util.GraphUtils;
import org.haw.lnielsen.gka.graphen.zugriffszaehler.ZugriffszaehlenderGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Before;
import org.junit.Test;

public class SpanningTreeAlgorithmsComparison {
	private GraphParser_I parser;
	private List<SpanningTreeAlgorithm_I<Knoten, DefaultEdge>> algorithms;
	
	@Before
	public void setUp() {
		parser = new GKAGraphParser();
		algorithms = new ArrayList<>();
		algorithms.add(new LarsKruskalSpanningTree<Knoten, DefaultEdge>());
		algorithms.add(new LarsPrimSpanningTree<Knoten, DefaultEdge>());
		algorithms.add(new PrimFibonacciHeapSpanningTree<Knoten, DefaultEdge>());
	}
	
	@Test
	public void testRandom_5000_50000() throws Exception {
		Graph<Knoten, DefaultEdge> graph = parser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/random_5000v_50000e.graph"));
		profileAndPrintSpanningTreeAlgorithms(graph, algorithms);
	}
	
	@Test
	public void testRandom_5000_100000() throws Exception {
		Graph<Knoten, DefaultEdge> graph = parser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/random_5000v_100000e.graph"));
		profileAndPrintSpanningTreeAlgorithms(graph, algorithms);
	}
	
	@Test
	public void testRandom_6000_100000() throws Exception {
		Graph<Knoten, DefaultEdge> graph = parser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/random_6000v_100000e.graph"));
		profileAndPrintSpanningTreeAlgorithms(graph, algorithms);
	}
	
	@Test
	public void testRandom_7000_100000() throws Exception {
		Graph<Knoten, DefaultEdge> graph = parser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/random_7000v_100000e.graph"));
		profileAndPrintSpanningTreeAlgorithms(graph, algorithms);
	}
	
	@Test
	public void testRandom_7000_200000() throws Exception {
		Graph<Knoten, DefaultEdge> graph = parser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/random_7000v_200000e.graph"));
		profileAndPrintSpanningTreeAlgorithms(graph, algorithms);
	}
	
	@Test
	public void testRandom_7000_300000() throws Exception {
		Graph<Knoten, DefaultEdge> graph = parser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/random_7000v_300000e.graph"));
		profileAndPrintSpanningTreeAlgorithms(graph, algorithms);
	}
	
	@Test
	public void testRandom_7000_400000() throws Exception {
		Graph<Knoten, DefaultEdge> graph = parser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/random_7000v_400000e.graph"));
		profileAndPrintSpanningTreeAlgorithms(graph, algorithms);
	}
	
	@Test
	public void testRandom_7000_500000() throws Exception {
		Graph<Knoten, DefaultEdge> graph = parser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/random_7000v_500000e.graph"));
		profileAndPrintSpanningTreeAlgorithms(graph, algorithms);
	}
	
	@Test
	public void testRandom_10000_500000() throws Exception {
		Graph<Knoten, DefaultEdge> graph = parser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/spanningtree/random_10000v_500000e.graph"));
		profileAndPrintSpanningTreeAlgorithms(graph, algorithms);
	}
	
	private static void profileAndPrintSpanningTreeAlgorithms(Graph<Knoten, DefaultEdge> graph, List<SpanningTreeAlgorithm_I<Knoten, DefaultEdge>> algorithms) {
		Map<SpanningTreeAlgorithm_I<Knoten, DefaultEdge>, Statistic> statistics = new HashMap<>();
		for(SpanningTreeAlgorithm_I<Knoten, DefaultEdge> algorithm : algorithms) {
			ZugriffszaehlenderGraph<Knoten, DefaultEdge> zugriffszaehlenderGraph = GraphFactory.createZugriffszaehlenderGraph(graph);
			Graph<Knoten, DefaultEdge> mst = GraphFactory.createGraph(graph);
			long before = System.currentTimeMillis();
			algorithm.calculateSpanningTree(zugriffszaehlenderGraph, mst);
			long millis = System.currentTimeMillis() - before;
			statistics.put(algorithm, new Statistic(millis, zugriffszaehlenderGraph.getZugriffsZaehler(), mst));
		}
		
		System.out.printf("%20s | %10s | %10s | %10s%n", "Algorithmus", "Zeit", "Zugriffe", "Gesamtgewicht");
		for(Entry<SpanningTreeAlgorithm_I<Knoten, DefaultEdge>, Statistic> entry : statistics.entrySet()) {
			System.out.printf("%20s | %10s | %10s | %10s%n", entry.getKey().toString(), new SimpleDateFormat("mm:ss:SSS").format(new Date(entry.getValue().millis)), entry.getValue().zugriffe, GraphUtils.calculateGraphWeight(entry.getValue().spannbaum));
		}
		System.out.println();
	}
	
	private static class Statistic {
		private long millis;
		private int zugriffe;
		private Graph<Knoten, DefaultEdge> spannbaum;
		
		public Statistic(long millis, int zugriffe, Graph<Knoten, DefaultEdge> spannbaum) {
			this.millis = millis;
			this.zugriffe = zugriffe;
			this.spannbaum = spannbaum;
		}
	}
}
