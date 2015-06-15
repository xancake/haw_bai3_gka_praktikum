package org.haw.lnielsen.gka.graphen.algorithm.euler;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.EulerianCircuit;

public class LarsHierholzerAlgorithm<V, E> implements EulerAlgorithm_I<V, E> {
	@Override
	public GraphPath<V, E> findEulerTour(Graph<V, E> graph) {
		if(!(graph instanceof UndirectedGraph) || EulerianCircuit.isEulerian((UndirectedGraph<V, E>)graph)) {
			return null;
		}
		
		UndirectedGraph<V, E> ugraph = (UndirectedGraph<V, E>)graph;
		
		
		
		
		return null;
	}
}
