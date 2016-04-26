package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.GridGraphGenerator;

public class GridGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Rows", "Columns"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new GridGraphGenerator<>(parameter[0], parameter[1]);
	}
	
	@Override
	public String toString() {
		return "Grid Graph Generator";
	}
}
