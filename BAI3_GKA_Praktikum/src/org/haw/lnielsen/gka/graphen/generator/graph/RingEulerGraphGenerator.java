package org.haw.lnielsen.gka.graphen.generator.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.RingGraphGenerator;

/**
 * Generator für Eulergraphen der mit der Anzahl zu generierenden Knoten und
 * internen Kreisen parameterisiert wird. Der Generator erzeugt zuerst einen
 * Kreis dem alle Knoten angehören. Anschließend werden zufällige Knoten
 * gewählt, die wiederum Kreise formen sollen und entsprechend verbunden.
 * 
 * @author Lars Nielsen
 */
public class RingEulerGraphGenerator<V, E> implements GraphGenerator<V, E, V> {
	private int myVertexCount;
	private int myInternalCircles;
	
	public RingEulerGraphGenerator(int vertexCount, int internalCircles) {
		if(vertexCount < 3) {
			throw new IllegalArgumentException("Es müssen mindestens 3 Knoten generiert werden, um einen Kreis zu erzeugen.");
		}
		if(internalCircles < 0) {
			throw new IllegalArgumentException("Es kann keine negative Anzahl an Kreisen generiert werden!");
		}
		myVertexCount = vertexCount;
		myInternalCircles = internalCircles;
	}
	
	@Override
	public void generateGraph(Graph<V, E> target, VertexFactory<V> vertexFactory, Map<String, V> resultMap) {
		RingGraphGenerator<V, E> generator = new RingGraphGenerator<>(myVertexCount);
		generator.generateGraph(target, vertexFactory, resultMap);
		
		for(int i=0; i<myInternalCircles; i++) {
			generateCircle(target, (int)(3 + Math.random() * (target.vertexSet().size()/2-3)));
		}
	}
	
	private void generateCircle(Graph<V, E> target, int circleSize) {
		List<V> vertices = new ArrayList<>(target.vertexSet());
		V start = vertices.get((int)(Math.random()*vertices.size()));
		V current = start;
		int i=1; // Bei 1 anfangen, da der Startknoten bereits gewählt wurde
		while(i<circleSize) {
			V newVertex = vertices.get((int)(Math.random()*vertices.size()));
			if(!current.equals(newVertex) && !target.containsEdge(current, newVertex)) {
				target.addEdge(current, newVertex);
				current = newVertex;
				i++;
			}
		}
		
		target.addEdge(current, start);
	}
}
