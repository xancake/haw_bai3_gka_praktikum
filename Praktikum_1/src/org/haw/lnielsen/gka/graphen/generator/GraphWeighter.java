package org.haw.lnielsen.gka.graphen.generator;

import java.util.Random;
import java.util.Set;

import org.haw.lnielsen.gka.graphen.algorithm.path.astar.HeuristikProvider_I;
import org.jgrapht.WeightedGraph;

/**
 * Generiert Gewichte für die Kanten eines Graphen, die den Kanten zugeordnet werden.
 * Die Gewichte werden aus der Heuristik der beiden Knoten jeder Kante ermittelt.
 * 
 * @author Lars Nielsen
 */
public class GraphWeighter<V, E> {
	private HeuristikProvider_I<V> myHeuristikProvider;
	private Random myRandom;
	private int myWeightModifier;
	
	public GraphWeighter(int weightModifier) {
		this(new DefaultHeuristikProvider<V>(), weightModifier);
	}
	
	/**
	 * Initialisiert einen neuen GraphWeighter.
	 * @param heuristikProvider Ein {@link HeuristikProvider_I}, wenn {@code null} übergeben wird,
	 *        wird ein standard HeuristikProvider verwendet, der als Heuristik immer 1 zurückgibt
	 * @param weightModifier Der Gewichtsmodifikator (muss > 0 sein)
	 */
	public GraphWeighter(HeuristikProvider_I<V> heuristikProvider, int weightModifier) {
		if(weightModifier <= 0) {
			throw new IllegalArgumentException("Der Gewichts-Modifikator darf nicht kleiner oder gleich 0 sein!");
		}
		myHeuristikProvider = heuristikProvider!=null ? heuristikProvider : new DefaultHeuristikProvider<V>();
		myRandom = new Random();
		myWeightModifier = weightModifier;
	}
	
	/**
	 * Generiert Gewichte für die Kanten des übergebenen Graphen.
	 * Die Gewichte werden aus der Heuristik der Knoten ermittelt.
	 * @param graph Der Graph, dem Kantengewichte erzeugt werden sollen
	 */
	public void appendGraphWeights(WeightedGraph<V, E> graph) {
		Set<E> edges = graph.edgeSet();
		for(E edge : edges) {
			int heuristikSource = myHeuristikProvider.getHeuristik(graph.getEdgeSource(edge));
			int heuristikTarget = myHeuristikProvider.getHeuristik(graph.getEdgeTarget(edge));
			int entfernungSourceMin = heuristikSource-heuristikTarget;
			int entfernungTargetMin = heuristikTarget-heuristikSource;
			int weight = (int)(Math.max(entfernungSourceMin, entfernungTargetMin)) + (int)(myRandom.nextInt(myWeightModifier));
			graph.setEdgeWeight(edge, weight);
		}
	}
	
	private static class DefaultHeuristikProvider<T> implements HeuristikProvider_I<T> {
		@Override
		public int getHeuristik(T vertex) {
			return 1;
		}
	}
}
