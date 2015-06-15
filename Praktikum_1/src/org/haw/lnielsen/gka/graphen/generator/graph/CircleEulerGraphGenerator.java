package org.haw.lnielsen.gka.graphen.generator.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GraphGenerator;

/**
 * Generator für Eulergraphen, der mit der Anzahl der zu generierenden Knoten
 * und der Kreisgröße parameterisiert wird. Der Generator erzeugt nun solange
 * Kreise der entsprechenden Kreisgröße, bis die Zielmenge an Knoten erreicht
 * ist. Dabei werden immer Kreise ausgehend von bestehenden Knoten aufgebaut,
 * wodurch ein zusammenhängender Graph aus mehreren verbundenen Kreisen
 * entsteht, von denen jeweils zwei immer über genau einen Knoten verbunden
 * sind.
 * 
 * @author Lars Nielsen
 */
public class CircleEulerGraphGenerator<V, E> implements GraphGenerator<V, E, V> {
	private static final int CIRCLE_SIZE_MIN = 3;
	
	private int myVertexCount;
	private int myCircleSize;
	private int myCircleSizeMin;
	
	public CircleEulerGraphGenerator(int vertexCount, int circleSize) {
		if(vertexCount < 3) {
			throw new IllegalArgumentException("Die Anzahl der Knoten darf nicht kleiner als 3 sein!");
		}
		if(circleSize < -1 || (circleSize > -1 && circleSize < CIRCLE_SIZE_MIN)) {
			throw new IllegalArgumentException("Die Größe der Kreise darf nicht kleiner als " + CIRCLE_SIZE_MIN + " sein, ausgenommen -1 für zufällige Größen!");
		}
		myVertexCount = vertexCount;
		myCircleSize = circleSize;
		myCircleSizeMin = myCircleSize == -1 ? CIRCLE_SIZE_MIN : myCircleSize;
	}
	
	
	@Override
	public void generateGraph(Graph<V, E> target, VertexFactory<V> vertexFactory, Map<String, V> resultMap) {
		target.addVertex(vertexFactory.createVertex());
		
		while(target.vertexSet().size() < myVertexCount) {
			int circleSize = circleSize(myVertexCount - target.vertexSet().size());
			
			List<V> vertices = new ArrayList<>(target.vertexSet());
			V start = vertices.get((int)(Math.random()*vertices.size()));
			V current = start;
			for(int i=1; i<circleSize; i++) { // fängt bei 1 an, da der Startknoten bereits existiert
				V newVertex = vertexFactory.createVertex();
				target.addVertex(newVertex);
				target.addEdge(current, newVertex);
				current = newVertex;
			}
			
			target.addEdge(current, start);
		}
		
	}
	
	/**
	 * Ermittelt die nächste Kreisgröße.
	 * @param remaining Die Anzahl der noch anzulegenden Knoten
	 * @return Die größe des nächsten Kreises
	 */
	private int circleSize(int remaining) {
		int circleSize = (myCircleSize == -1
				? (int)(CIRCLE_SIZE_MIN + Math.random()*(remaining - CIRCLE_SIZE_MIN))
				: myCircleSize);
		
		if(remaining - (circleSize-1) < myCircleSizeMin-1) {
			circleSize = remaining-1;
		}
		
		return circleSize;
	}
}
