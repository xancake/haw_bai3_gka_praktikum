package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.kruskal;

import java.util.Comparator;

import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.jgrapht.Graph;

public class JennyKruskalSpanningTree<V, E> implements SpanningTreeAlgorithm_I<V, E> {

	@Override
	public Graph<V, E> calculateSpanningTree(Graph<V, E> graph, Graph<V, E> spanningTree) {
		//Kantengewichte sortieren
		return null;
	}
	
	private class SortEdgeWeight implements Comparator<E> {
		private Graph<V, E> _graph;
		
		public SortEdgeWeight(Graph<V, E> graph) {
			this._graph = graph; 
		}
		
		@Override
		public int compare(E edge1, E edge2) {	
			return (int)(_graph.getEdgeWeight(edge1) - _graph.getEdgeWeight(edge2));
		}
	}

}
