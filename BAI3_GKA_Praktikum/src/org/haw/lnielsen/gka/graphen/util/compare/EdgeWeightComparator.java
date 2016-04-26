package org.haw.lnielsen.gka.graphen.util.compare;

import java.util.Comparator;

import org.jgrapht.Graph;

/**
 * Comparator für die Prioritäts-Warteschlange, der Kanten aufsteigend nach ihrem Gewicht sortiert.
 * 
 * @author Lars Nielsen
 */
public class EdgeWeightComparator<E> implements Comparator<E> {
	private Graph<?, E> myGraph;
	
	public EdgeWeightComparator(Graph<?, E> graph) {
		myGraph = graph;
	}
	
	@Override
	public int compare(E o1, E o2) {
		return (int)(myGraph.getEdgeWeight(o1)-myGraph.getEdgeWeight(o2));
	}
}