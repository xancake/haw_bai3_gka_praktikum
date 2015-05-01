package org.haw.lnielsen.gka.graphen.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.haw.lnielsen.gka.graphen.algorithm.path.astar.HeuristikProvider_I;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.generate.GraphGenerator;

/**
 * Dieser Generator erzeugt einen zufälligen Graphen mit einer vorgegebenen
 * Anzahl an Knoten und Kanten. Dabei verwendet er die Heuristik der Knoten
 * um die Kantengewichte zwischen je zwei Knoten zu bestimmen.
 * 
 * @author Lars Nielsen
 * @author Jennifer Momsen
 */
public class HeuristikGraphGenerator<V, E> implements GraphGenerator<V, E, V> {
	public static final String ZERO_HEURISTIK_VERTEX = "ZERO_VERTEX";
	
	private HeuristikProvider_I<V> myHeuristikProvider;
	private int myVertexCount;
	private int myEdgeCount;
	private int myWeightModifier;
	
	/**
	 * Initialisiert einen neuen Heuristik-Graph-Generator.
	 * @param heuristikProvider Der {@link HeuristikProvider_I}
	 * @param vertexCount Die Anzahl der Knoten (muss >= 0 sein)
	 * @param edgeCount Die Anzahl der Kanten (muss >= 0 sein)
	 * @param weightModifier Der Gewichtsmodifikator (muss >= 0 sein)
	 */
	public HeuristikGraphGenerator(HeuristikProvider_I<V> heuristikProvider, int vertexCount, int edgeCount, int weightModifier) {
		if(vertexCount < 0) {
			throw new IllegalArgumentException("Die Knotenanzahl darf nicht kleiner als 0 sein!");
		}
		if(edgeCount < 0) {
			throw new IllegalArgumentException("Die Kantenzahl darf nicht kleiner als 0 sein!");
		}
		if(weightModifier < 0) {
			throw new IllegalArgumentException("Der Gewichts-Modifikator darf nicht kleiner als 0 sein!");
		}
		myHeuristikProvider = Objects.requireNonNull(heuristikProvider);
		myVertexCount = vertexCount;
		myEdgeCount = edgeCount;
		myWeightModifier = weightModifier;
	}
	
	@Override
	public void generateGraph(Graph<V, E> graph, VertexFactory<V> vertexFactory, Map<String, V> resultMap) {
		int maxEdges = 0;
		for(int i=myVertexCount-1; i>0; i--) {
			maxEdges += i * (graph instanceof DirectedGraph ? 2 : 1);
		}
		
		if(maxEdges < myEdgeCount) {
			// Eine checked-Exception wäre hier schöner, aber durch die JGraphT-Generator-Schnittstelle leider nicht möglich
			throw new IllegalArgumentException("Die Anzahl der möglichen Kanten (" + maxEdges + ") für den Graphen ist kleiner als die gewünschte Anzahl an Kanten (" + myEdgeCount + ")");
		}
		
		for(int i=0; i<myVertexCount; i++) {
			V vertex = vertexFactory.createVertex();
			graph.addVertex(vertex);
			if(resultMap != null && myHeuristikProvider.getHeuristik(vertex) == 0) {
				resultMap.put(ZERO_HEURISTIK_VERTEX, vertex);
			}
		}
		
		Random random = new Random();
		
		List<V> vertices = new ArrayList<>(graph.vertexSet());
		int i=0;
		while(i<myEdgeCount) {
			V source = vertices.get(random.nextInt(myVertexCount));
			V target = vertices.get(random.nextInt(myVertexCount));
			
			try {
				E edge = graph.addEdge(source, target);
				if(edge == null) {
					// Kante wurde nicht hinzugefügt
				} else {
					// Kante wurde hinzugefügt
					if(graph instanceof WeightedGraph) {
						int heuristikSource = myHeuristikProvider.getHeuristik(source);
						int heuristikTarget = myHeuristikProvider.getHeuristik(target);
						
						int entfernungSourceMin = heuristikSource-heuristikTarget;
						int entfernungTargetMin = heuristikTarget-heuristikSource;
						int weight = (int)Math.max(entfernungSourceMin, entfernungTargetMin) + (int)random.nextInt(myWeightModifier);
						((WeightedGraph<V, E>)graph).setEdgeWeight(edge, weight);
					}
					i++;
				}
			} catch(Exception e) {
				// Kante wurde nicht hinzugefügt
			}
		}
	}
}
