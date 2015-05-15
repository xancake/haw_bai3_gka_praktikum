package org.haw.lnielsen.gka.graphen.algorithm.spanningtree;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

public class LarsKruskalSpanningTree implements SpanningTreeAlgorithm_I<Knoten, DefaultEdge> {
	@Override
	public Graph<Knoten, DefaultEdge> calculateSpanningTree(Graph<Knoten, DefaultEdge> graph) {
		Queue<DefaultEdge> edges = new PriorityQueue<DefaultEdge>(graph.edgeSet().size(), new EdgeWeightComparator(graph));
		edges.addAll(graph.edgeSet());
		
		Graph<Knoten, DefaultEdge> spanningTree = GraphFactory.createGraph(graph instanceof DirectedGraph, graph instanceof WeightedGraph);
		while(!edges.isEmpty()) {
			DefaultEdge edge = edges.poll();
			Knoten edgeSource = graph.getEdgeSource(edge);
			Knoten edgeTarget = graph.getEdgeTarget(edge);
			if(!producesCircle(graph, spanningTree, edge)) {
				spanningTree.addVertex(edgeSource);
				spanningTree.addVertex(edgeTarget);
				spanningTree.addEdge(edgeSource, edgeTarget);
				if(spanningTree instanceof WeightedGraph) {
					((WeightedGraph<Knoten, DefaultEdge>)spanningTree).setEdgeWeight(spanningTree.getEdge(edgeSource, edgeTarget), graph.getEdgeWeight(edge));
				}
			}
		}
		
		return spanningTree;
	}
	
	private boolean producesCircle(Graph<Knoten, DefaultEdge> graph, Graph<Knoten, DefaultEdge> spanningTree, DefaultEdge edge) {
		if(spanningTree.containsVertex(graph.getEdgeSource(edge)) && spanningTree.containsVertex(graph.getEdgeTarget(edge))) {
			DijkstraShortestPath<Knoten, DefaultEdge> dijkstra = new DijkstraShortestPath<Knoten, DefaultEdge>(spanningTree, graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
			return dijkstra.getPath() != null;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Lars Kruskal Spanning Tree";
	}
	
	private class EdgeWeightComparator implements Comparator<DefaultEdge> {
		private Graph<Knoten, DefaultEdge> myGraph;
		
		public EdgeWeightComparator(Graph<Knoten, DefaultEdge> graph) {
			myGraph = graph;
		}
		
		@Override
		public int compare(DefaultEdge o1, DefaultEdge o2) {
			return (int)(myGraph.getEdgeWeight(o1)-myGraph.getEdgeWeight(o2));
		}
	}
}
