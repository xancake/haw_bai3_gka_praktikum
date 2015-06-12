package org.haw.lnielsen.gka.graphen.generator.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.RandomGraphGenerator;

public class EulerGraphGenerator<V, E> implements GraphGenerator<V, E, V> {
	private int myAnzahlKnoten;
	private int myAnzahlKanten;
	
	public EulerGraphGenerator(int anzahlKnoten, int anzahlKanten) {
		if(anzahlKnoten < 0) {
			throw new IllegalArgumentException("Die Anzahl der Knoten darf nicht kleiner als 0 sein!");
		}
		if(anzahlKanten <= 0) {
			throw new IllegalArgumentException("Die Anzahl der Kanten muss größer als 0 sein!");
		}
		myAnzahlKnoten = anzahlKnoten;
		myAnzahlKanten = anzahlKanten;
	}
	
	@Override
	public void generateGraph(Graph<V, E> target, VertexFactory<V> vertexFactory, Map<String, V> resultMap) {
		GraphGenerator<V, E, V> generator = new RandomGraphGenerator<V, E>(myAnzahlKnoten, myAnzahlKanten);
		generator.generateGraph(target, vertexFactory, resultMap);
		
		List<V> verticesWithOddDegree = new LinkedList<>();
		for(V vertex : target.vertexSet()) {
			if(target.edgesOf(vertex).size()%2 == 1) {
				verticesWithOddDegree.add(vertex);
			}
		}
		
//		for(int i=0; i) {
//			
//		}
	}
}
