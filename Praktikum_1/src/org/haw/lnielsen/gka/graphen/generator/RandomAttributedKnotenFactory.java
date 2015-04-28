package org.haw.lnielsen.gka.graphen.generator;

import java.util.Random;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.VertexFactory;

public class RandomAttributedKnotenFactory implements VertexFactory<Knoten> {
	private int myVertexCounter;
	private int myAttributeMaxValue;
	private Random myRandom;
	
	public RandomAttributedKnotenFactory(int attributeMaxValue) {
		myVertexCounter = 0;
		myAttributeMaxValue = attributeMaxValue;
		myRandom = new Random();
	}
	
	@Override
	public Knoten createVertex() {
		return new Knoten(String.valueOf(myVertexCounter++), (int)(myRandom.nextInt(myAttributeMaxValue)));
	}
}
