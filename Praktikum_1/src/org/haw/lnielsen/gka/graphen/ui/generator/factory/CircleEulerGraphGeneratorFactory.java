package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.haw.lnielsen.gka.graphen.generator.graph.CircleEulerGraphGenerator;
import org.jgrapht.generate.GraphGenerator;

public class CircleEulerGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Vertex-Count", "Circle-Size"};
	}

	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new CircleEulerGraphGenerator<>(parameter[0], parameter[1]);
	}
	
	@Override
	public String toString() {
		return "Circle Euler-Graph Generator";
	}
}
