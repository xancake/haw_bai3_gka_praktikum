package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.haw.lnielsen.gka.graphen.generator.graph.LarsRandomEulerGraphGenerator;
import org.jgrapht.generate.GraphGenerator;

public class LarsRandomEulerGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Vertex-Count"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new LarsRandomEulerGraphGenerator<V, E>(parameter[0]);
	}
	
	@Override
	public String toString() {
		return "Lars Random Euler-Graph Generator";
	}
}
