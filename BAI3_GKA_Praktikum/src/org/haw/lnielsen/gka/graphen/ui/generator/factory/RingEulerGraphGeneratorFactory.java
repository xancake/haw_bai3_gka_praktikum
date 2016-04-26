package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.haw.lnielsen.gka.graphen.generator.graph.RingEulerGraphGenerator;
import org.jgrapht.generate.GraphGenerator;

public class RingEulerGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Vertex-Count", "internal Circle-Count"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new RingEulerGraphGenerator<>(parameter[0], parameter[1]);
	}
	
	@Override
	public String toString() {
		return "Ring Euler-Graph Generator";
	}
}
