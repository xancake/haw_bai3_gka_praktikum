package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.LinearGraphGenerator;

public class LinearGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Vertex-Count"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new LinearGraphGenerator<>(parameter[0]);
	}
	
	@Override
	public String toString() {
		return "Linear Graph Generator";
	}
}
