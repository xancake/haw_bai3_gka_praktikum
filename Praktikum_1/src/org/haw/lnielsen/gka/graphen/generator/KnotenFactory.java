package org.haw.lnielsen.gka.graphen.generator;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.VertexFactory;

public class KnotenFactory implements VertexFactory<Knoten> {
	private int myVertexCounter = 0;
	
	@Override
	public Knoten createVertex() {
		return new Knoten(String.valueOf(myVertexCounter++));
	}
	
	public int getVertexCounter() {
		return myVertexCounter;
	}
}
