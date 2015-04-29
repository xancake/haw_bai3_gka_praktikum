package org.haw.lnielsen.gka.graphen.ui.generator.factory;

import java.util.Objects;

import org.jgrapht.generate.GraphGenerator;

public abstract class GraphGeneratorFactory<V, E, T> {
	private String[] myParameterNames;
	
	public GraphGeneratorFactory() {
		myParameterNames = Objects.requireNonNull(initParameterNames());
	}
	
	protected abstract String[] initParameterNames();
	
	public GraphGenerator<V, E, T> createGenerator(Integer... parameter) {
		if(getParameterCount() < parameter.length) {
			throw new IllegalArgumentException("Es müssen " + getParameterCount() + " Parameter angegeben werden");
		}
		return createGeneratorImpl(parameter);
	}
	
	protected abstract GraphGenerator<V, E, T> createGeneratorImpl(Integer... parameter);
	
	public int getParameterCount() {
		return getParameterNames().length;
	}
	
	public String[] getParameterNames() {
		return myParameterNames;
	}
}
