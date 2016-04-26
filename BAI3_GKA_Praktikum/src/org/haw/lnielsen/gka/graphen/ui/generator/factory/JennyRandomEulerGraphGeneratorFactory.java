package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.haw.lnielsen.gka.graphen.generator.graph.JennyRandomEulerGraphGenerator;
import org.jgrapht.generate.GraphGenerator;

public class JennyRandomEulerGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Vertex-Count", "Edge-Count"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new JennyRandomEulerGraphGenerator<>(parameter[0], parameter[1]);
	}
	
	@Override
	public String toString() {
		return "Jenny Random Euler-Graph Generator";
	}
}
