package org.haw.lnielsen.gka.graphen;

import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_GKA;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class TestMain {
	public static void main(String[] args) throws Exception {
		DirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		
		String a = "A";
		String b = "B";
		String c = "C";
		String d = "D";
		
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		
		graph.addEdge(a, b);
		graph.addEdge(b, a);
		graph.addEdge(b, b);
		graph.addEdge(c, b);
		graph.addEdge(d, d);
		
		System.out.println(graph);
		System.out.println("in: " + graph.incomingEdgesOf(a) + " out:" + graph.outgoingEdgesOf(a));
		System.out.println("in: " + graph.incomingEdgesOf(b) + " out:" + graph.outgoingEdgesOf(b));
		System.out.println("in: " + graph.incomingEdgesOf(c) + " out:" + graph.outgoingEdgesOf(c));
		System.out.println("in: " + graph.incomingEdgesOf(d) + " out:" + graph.outgoingEdgesOf(d));
		
		
		
		Graph<Knoten, DefaultEdge> graph2 = new GraphParser_GKA().parseGraph(ClassLoader.getSystemResourceAsStream("example.graph"));
		
		
		
	}
}
