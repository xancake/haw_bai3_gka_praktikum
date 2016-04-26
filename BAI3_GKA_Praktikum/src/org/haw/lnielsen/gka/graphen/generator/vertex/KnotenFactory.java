package org.haw.lnielsen.gka.graphen.generator.vertex;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.jgrapht.VertexFactory;

/**
 * Eine Fabrik, die Knoten erzeugt.
 * 
 * @author Lars Nielsen
 */
public class KnotenFactory implements VertexFactory<Knoten> {
	private int myVertexCounter = 0;
	
	@Override
	public Knoten createVertex() {
		return new Knoten(String.valueOf(myVertexCounter++));
	}
	
	/**
	 * Gibt zurück, wieviele Knoten von dieser Fabrik erzeugt wurden.
	 * @return wieviele Knoten erstellt wurden
	 */
	public int getVertexCounter() {
		return myVertexCounter;
	}
}
