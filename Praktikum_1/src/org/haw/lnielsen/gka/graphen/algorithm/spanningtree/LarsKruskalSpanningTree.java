package org.haw.lnielsen.gka.graphen.algorithm.spanningtree;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;

public class LarsKruskalSpanningTree<V, E> implements SpanningTreeAlgorithm_I<V, E> {
	@Override
	public Graph<V, E> calculateSpanningTree(Graph<V, E> graph, Graph<V, E> spanningTree) {
		Queue<E> edges = new PriorityQueue<>(graph.edgeSet().size(), new EdgeWeightComparator(graph));
		edges.addAll(graph.edgeSet());
		
		while(!edges.isEmpty()) {
			E edge = edges.poll();
			V edgeSource = graph.getEdgeSource(edge);
			V edgeTarget = graph.getEdgeTarget(edge);
			if(!producesCircle(graph, spanningTree, edge)) {
				spanningTree.addVertex(edgeSource);
				spanningTree.addVertex(edgeTarget);
				spanningTree.addEdge(edgeSource, edgeTarget);
				if(spanningTree instanceof WeightedGraph) {
					((WeightedGraph<V, E>)spanningTree).setEdgeWeight(spanningTree.getEdge(edgeSource, edgeTarget), graph.getEdgeWeight(edge));
				}
			}
		}
		
		return spanningTree;
	}
	
	private boolean producesCircle(Graph<V, E> graph, Graph<V, E> spanningTree, E edge) {
		if(spanningTree.containsVertex(graph.getEdgeSource(edge)) && spanningTree.containsVertex(graph.getEdgeTarget(edge))) {
			DijkstraShortestPath<V, E> dijkstra = new DijkstraShortestPath<V, E>(spanningTree, graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
			return dijkstra.getPath() != null;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Lars Kruskal Spanning Tree";
	}
	
	private class EdgeWeightComparator implements Comparator<E> {
		private Graph<V, E> myGraph;
		
		public EdgeWeightComparator(Graph<V, E> graph) {
			myGraph = graph;
		}
		
		@Override
		public int compare(E o1, E o2) {
			return (int)(myGraph.getEdgeWeight(o1)-myGraph.getEdgeWeight(o2));
		}
	}
}
