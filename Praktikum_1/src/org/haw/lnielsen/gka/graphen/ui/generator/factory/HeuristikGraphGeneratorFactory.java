package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import java.util.Objects;

import org.haw.lnielsen.gka.graphen.algorithm.path.astar.HeuristikProvider_I;
import org.haw.lnielsen.gka.graphen.generator.HeuristikGraphGenerator;
import org.jgrapht.generate.GraphGenerator;

public class HeuristikGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	private HeuristikProvider_I<V> myHeuristikProvider;
	
	public HeuristikGraphGeneratorFactory(HeuristikProvider_I<V> heuristikProvider) {
		myHeuristikProvider = Objects.requireNonNull(heuristikProvider);
	}
	
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Vertex-Count", "Edge-Count", "Weight-Modifier"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new HeuristikGraphGenerator<>(myHeuristikProvider, parameter[0], parameter[1], parameter[2]);
	}
	
	@Override
	public String toString() {
		return "Heuristik Graph Generator";
	}
}
