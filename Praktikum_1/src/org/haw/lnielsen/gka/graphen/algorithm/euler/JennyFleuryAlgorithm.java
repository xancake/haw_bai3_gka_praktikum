package org.haw.lnielsen.gka.graphen.algorithm.euler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.graph.GraphPathImpl;
import org.jgrapht.graph.UndirectedSubgraph;

public class JennyFleuryAlgorithm<V, E> implements EulerAlgorithm_I<V, E> {

	@Override
	public GraphPath<V, E> findEulerTour(Graph<V, E> graph) {
		if(!(graph instanceof UndirectedGraph)){
			throw new IllegalArgumentException("Der Algorithmus derzeit nur ungerichtete Graphen");
		}
		if(!EulerianCircuit.isEulerian((UndirectedGraph<V, E>)graph)){
			return null;
		}
		
		UndirectedGraph<V, E> mirrorGraph = new UndirectedSubgraph<>((UndirectedGraph<V, E>)graph, null, null);
		List<E> edgeList = new ArrayList<>();
		V start = graph.vertexSet().iterator().next();
		V aktuellerKnoten = start;
		Iterator<E> edgeIterator = graph.edgesOf(aktuellerKnoten).iterator();
		while (!graph.edgeSet().isEmpty()) {
			E edge = edgeIterator.next();
			V target = graph.getEdgeTarget(edge);
			V source = graph.getEdgeSource(edge);
			V other  = aktuellerKnoten.equals(source) ? target : source;
			
			mirrorGraph.removeEdge(edge);
			if (new ConnectivityInspector<>((UndirectedGraph<V, E>)mirrorGraph).isGraphConnected() || mirrorGraph.degreeOf(aktuellerKnoten) == 0) {
				if(mirrorGraph.degreeOf(aktuellerKnoten)==0){
					mirrorGraph.removeVertex(aktuellerKnoten);
				}
				aktuellerKnoten = other;
				graph.removeEdge(edge);
				edgeIterator = graph.edgesOf(aktuellerKnoten).iterator();
				edgeList.add(edge);
			}else{
				mirrorGraph.addEdge(source, target, edge);
			}
		}
		
		GraphPathImpl<V, E> graphPath = new GraphPathImpl<V, E>(graph, start, aktuellerKnoten, edgeList, 0);
		return graphPath;
	}

}
