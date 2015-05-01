package org.haw.lnielsen.gka.graphen.generator;

import java.util.Random;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.VertexFactory;

/**
 * Eine Fabrik, die zufällig attributierte Knoten erzeugt.
 * Der erste erzeugte Knoten hat immer eine Heuristik von 0.
 * Alle danach erzeugten Knoten erhalten eine zufällige Heuristik
 * aus dem Wertebereich von 1 bis zu einem Maximalwert.
 * 
 * @author Lars Nielsen
 */
public class RandomAttributedKnotenFactory implements VertexFactory<Knoten> {
	private int myVertexCounter;
	private int myAttributeMaxValue;
	private Random myRandom;
	
	/**
	 * Initialisiert eine neue Knotenfabrik für zufällig attributierte Knoten.
	 * @param attributeMaxValue Der Maximalwert für die Heuristiken
	 */
	public RandomAttributedKnotenFactory(int attributeMaxValue) {
		myVertexCounter = 0;
		myAttributeMaxValue = attributeMaxValue;
		myRandom = new Random();
	}
	
	@Override
	public Knoten createVertex() {
		if(myVertexCounter == 0) {
			return new Knoten(String.valueOf(myVertexCounter++), 0);
		}
		return new Knoten(String.valueOf(myVertexCounter++), 1 + (int)(myRandom.nextInt(myAttributeMaxValue)));
	}
}
