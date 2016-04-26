package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.RandomGraphGenerator;

public class RandomGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Vertex-Count", "Edge-Count"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new RandomGraphGenerator<V, E>(parameter[0], parameter[1]);
	}
	
	@Override
	public String toString() {
		return "Random Graph Generator";
	}
}
