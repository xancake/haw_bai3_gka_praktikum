package org.haw.lnielsen.gka.graphen.generator.vertex;

import java.util.Random;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.VertexFactory;

/**
 * Eine Fabrik, die zufällig attributierte Knoten erzeugt.
 * Der erste erzeugte Knoten hat immer eine Heuristik von 0.
 * Alle danach erzeugten Knoten erhalten eine zufällige Heuristik
 * aus dem Wertebereich von einem konfigurierbaren Minimal- bis
 * zu einem Maximalwert. Es wird aber verhindert, dass mehrere
 * Knoten mit einer Heuristik von 0 erzeugt werden.
 * 
 * @author Lars Nielsen
 */
public class RandomAttributedKnotenFactory implements VertexFactory<Knoten> {
	private int myVertexCounter;
	private int myAttributeMinValue;
	private int myAttributeMaxValue;
	private Random myRandom;
	
	/**
	 * Initialisiert eine neue Knotenfabrik für zufällig attributierte Knoten.
	 * @param attributeMinValue Der Minimalwert für die Heuristiken
	 * @param attributeMaxValue Der Maximalwert für die Heuristiken
	 */
	public RandomAttributedKnotenFactory(int attributeMinValue, int attributeMaxValue) {
		if(attributeMinValue > attributeMaxValue) {
			throw new IllegalArgumentException("Der Minimalwert für die Attributierung von Knoten muss kleiner oder gleich dem Maximalwert sein.");
		}
		myVertexCounter = 0;
		myAttributeMinValue = attributeMinValue;
		myAttributeMaxValue = attributeMaxValue;
		myRandom = new Random();
	}
	
	@Override
	public Knoten createVertex() {
		if(myVertexCounter == 0) {
			return new Knoten(String.valueOf(myVertexCounter++), 0);
		}
		int attribute = 0;
		while(attribute == 0) {
			attribute = myAttributeMinValue + myRandom.nextInt(myAttributeMaxValue-myAttributeMinValue);
		}
		return new Knoten(String.valueOf(myVertexCounter++), attribute);
	}
	
	/**
	 * Gibt zurück, wieviele Knoten von dieser Fabrik erzeugt wurden.
	 * @return wieviele Knoten erstellt wurden
	 */
	public int getVertexCounter() {
		return myVertexCounter;
	}
}
