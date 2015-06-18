package org.haw.lnielsen.gka.graphen.generator.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GraphGenerator;

/**
 * Generator für Eulergraphen, der mit der Anzahl der zu generierenden Knoten
 * und der Kreisgröße parameterisiert wird. Der Generator erzeugt nun solange
 * Kreise der entsprechenden Kreisgröße, bis die Zielmenge an Knoten erreicht
 * ist. Dabei werden immer Kreise ausgehend von zwei bestehenden Knoten
 * aufgebaut, wodurch ein zusammenhängender Graph aus mehreren verbundenen
 * Kreisen entsteht. Zwischen den entstehenden Kreisen gibt es genau zwei
 * Schnittknoten.
 * 
 * @author Lars Nielsen
 */
public class IncrementalEulerGraphGenerator<V, E> implements GraphGenerator<V, E, V> {
	private static final int CIRCLE_SIZE_MIN = 3;
	
	private int myVertexCount;
	private int myCircleSize;
//	private int myCircleSizeMin;
	
	public IncrementalEulerGraphGenerator(int vertexCount, int circleSize) {
		if(vertexCount < 3) {
			throw new IllegalArgumentException("Die Anzahl der Knoten darf nicht kleiner als 3 sein!");
		}
		if(circleSize < -1 || (circleSize > -1 && circleSize < CIRCLE_SIZE_MIN) || circleSize > vertexCount) {
			throw new IllegalArgumentException("Die Größe der Kreise darf nicht kleiner als " + CIRCLE_SIZE_MIN + " sein, ausgenommen -1 für zufällige Größen!");
		}
		myVertexCount = vertexCount;
		myCircleSize = circleSize;
//		myCircleSizeMin = myCircleSize == -1 ? CIRCLE_SIZE_MIN : myCircleSize;
	}
	
	
	@Override
	public void generateGraph(Graph<V, E> target, VertexFactory<V> vertexFactory, Map<String, V> resultMap) {
		
		// Zwei Startknoten definieren
		V first  = vertexFactory.createVertex();
		V second = vertexFactory.createVertex();
		target.addVertex(first);
		target.addVertex(second);
		
		while(target.vertexSet().size() < myVertexCount) {
			int circleSize = circleSize(myVertexCount - target.vertexSet().size());
			
			List<V> vertices = new ArrayList<>(target.vertexSet());
			V v1 = vertices.remove((int)(Math.random()*vertices.size()));
			V v2 = vertices.remove((int)(Math.random()*vertices.size()));
			
			LinkedList<V> circle = new LinkedList<>();
			circle.add(v1);
			while(circleSize/2>circle.size()) {
				circle.add(vertexFactory.createVertex());
			}
			circle.add(v2);
			while(circleSize>circle.size()) {
				circle.add(vertexFactory.createVertex());
			}
			circle.add(v1);
			
			for(int i=0; i<circle.size()-1; i++) {
				V va = circle.get(i);
				V vb = circle.get(i+1);
				target.addVertex(va);
				target.addVertex(vb);
				if(target.containsEdge(va, vb)) {
					target.removeEdge(va, vb);
				} else {
					target.addEdge(va, vb);
				}
			}
		}
	}
	
	/**
	 * Ermittelt die nächste Kreisgröße.
	 * @param remaining Die Anzahl der noch anzulegenden Knoten
	 * @return Die größe des nächsten Kreises
	 */
	private int circleSize(int remaining) {
		int circleSize = (myCircleSize == -1
				? (int)(CIRCLE_SIZE_MIN + Math.random()*(myVertexCount - CIRCLE_SIZE_MIN))
				: myCircleSize);
		
		// TODO: Deckelung, sodass der Fall, dass nicht genügend Knoten für einen Kreis zur Verfügung stehen behandelt wird
//		if(remaining - circleSize < myCircleSizeMin) {
//			circleSize += remaining;
//		}
		
		return circleSize;
	}
}
