package org.haw.lnielsen.gka.graphen.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.generate.GraphGenerator;

public class RandomWeightedAttributedGraphGenerator<V, E> implements GraphGenerator<V, E, V> {
	private int myVertexCount;
	private int myEdgeCount;
	
	public RandomWeightedAttributedGraphGenerator(int vertexCount, int edgeCount) {
		if(vertexCount < 0) {
			throw new IllegalArgumentException("Die Knotenanzahl darf nicht kleiner als 0 sein!");
		}
		if(edgeCount < 0) {
			throw new IllegalArgumentException("Die Kantenzahl darf nicht kleiner als 0 sein!");
		}
		myVertexCount = vertexCount;
		myEdgeCount = edgeCount;
	}
	
	@Override
	public void generateGraph(Graph<V, E> graph, VertexFactory<V> vertexFactory, Map<String, V> map) {
		for(int i=0; i<myVertexCount; i++) {
			graph.addVertex(vertexFactory.createVertex());
		}
		
		Random random = new Random();
		
		List<V> vertices = new ArrayList<>(graph.vertexSet());
		for(int i=0; i<myEdgeCount; i++) {
			V source = vertices.get(random.nextInt(myVertexCount));
			V target = vertices.get(random.nextInt(myVertexCount));
			
			try {
				E edge = graph.addEdge(source, target);
				if(edge == null) {
					// Kante wurde nicht hinzugefügt
				} else {
					// Kante wurde hinzugefügt
					if(graph instanceof WeightedGraph) {
						((WeightedGraph<V, E>)graph).setEdgeWeight(edge, weight);
					}
				}
			} catch(Exception e) {
				// Kante wurde nicht hinzugefügt
			}
		}
		
		V start = vertices.get(random.nextInt(myVertexCount));
		
		
		
		
	}
}
