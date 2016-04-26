package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import org.jgrapht.generate.CompleteBipartiteGraphGenerator;
import org.jgrapht.generate.GraphGenerator;

public class CompleteBipartiteGraphGeneratorFactory<V, E> extends GraphGeneratorFactory<V, E, V> {
	@Override
	protected String[] initParameterNames() {
		return new String[] {"Partition 1 Vertex-Count", "Partition 2 Vertex-Count"};
	}
	
	@Override
	protected GraphGenerator<V, E, V> createGeneratorImpl(Integer... parameter) {
		return new CompleteBipartiteGraphGenerator<V, E>(parameter[0], parameter[1]);
	}
	
	@Override
	public String toString() {
		return "Complete Bipartite Graph Generator";
	}
}
