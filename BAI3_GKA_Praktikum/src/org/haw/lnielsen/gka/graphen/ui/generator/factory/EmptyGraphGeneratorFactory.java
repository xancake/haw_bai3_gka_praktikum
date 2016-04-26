package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.jgrapht.generate.EmptyGraphGenerator;
import org.jgrapht.generate.GraphGenerator;

public class EmptyGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Vertex-Count"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new EmptyGraphGenerator<>(parameter[0]);
	}
	
	@Override
	public String toString() {
		return "Empty Graph Generator";
	}
}
