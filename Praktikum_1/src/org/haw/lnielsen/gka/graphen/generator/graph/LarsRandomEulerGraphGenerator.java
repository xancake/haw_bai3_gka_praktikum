package org.haw.lnielsen.gka.graphen.generator.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GraphGenerator;

/**
 * Ein Generator der zufällige Euler-Graphen erzeugt. Der Generator arbeitet in mehreren Stufen.
 * <ol>
 *   <li>Erzeugen aller Knoten und Hinzufügen zum Graphen</li>
 *   <li>Bilden einer Komponente, indem nach und nach die freien Knoten der Komponente hinzugefügt werden</li>
 *   <li>Ermitteln der Knoten mit ungeradem Knotengrad</li>
 *   <li>Untereinander verbinden der Knoten mit ungeradem Knotengrad, bis dies nicht mehr möglich ist</li>
 *   <li>Formen eines Kreises aus den verbliebenen Knoten mit ungeradem Knotengrad und beliebigen Knoten mit geradem Knotengrad</li>
 * </ol>
 * 
 * @author Lars Nielsen
 */
public class LarsRandomEulerGraphGenerator<V, E> implements GraphGenerator<V, E, V> {
	private int myVertexCount;
	
	public LarsRandomEulerGraphGenerator(int vertexCount) {
		if(vertexCount < 0) {
			throw new IllegalArgumentException("Die Anzahl der Knoten darf nicht kleiner als 0 sein!");
		}
		myVertexCount = vertexCount;
	}
	
	@Override
	public void generateGraph(Graph<V, E> target, VertexFactory<V> vertexFactory, Map<String, V> resultMap) {
		if(!(target instanceof UndirectedGraph)) {
			throw new IllegalArgumentException("Der Generator kann nur ungerichtete Graphen generieren!");
		}
		
		// Knoten erzeugen
		for(int i=0; i<myVertexCount; i++) {
			target.addVertex(vertexFactory.createVertex());
		}
		
		// Alle Knoten so verbinden, dass sie eine Komponente formen
		Queue<V> loose    = new LinkedList<>(target.vertexSet());
		List<V> connected = new ArrayList<>(target.vertexSet().size());
		connected.add(loose.poll()); // Startknoten wählen
		while(!loose.isEmpty()) {
			V looseVertex     = loose.poll();
			V connectedVertex = connected.get((int)(Math.random()*connected.size()));
			target.addEdge(looseVertex, connectedVertex);
			connected.add(looseVertex);
		}
		
		// Knoten mit ungeradem Knotengrad ermitteln
		List<V> odd = new ArrayList<>();
		for(V v : connected) {
			if(((UndirectedGraph<V, E>)target).degreeOf(v)%2 == 1) {
				odd.add(v);
			}
		}
		
		// Knoten mit ungeradem Knotengrad miteinander verbinden, bis keine mehr verbunden werden können
		for(int i=0; i<odd.size(); i++) {
			for(int j=i+1; j<odd.size(); j++) {
				V vi = odd.get(i);
				V vj  = odd.get(j);
				if(!target.containsEdge(vi, vj)) {
					target.addEdge(vi, vj);
					odd.remove(j);
					odd.remove(i);
					j -= 2;
					i -= 1;
					break;
				}
			}
		}
		
		// Verbleibende Knoten mit ungeradem Knotengrad mit irgendwelchen Knoten verbinden
		
		
		
		
		
		boolean test = false;
		test = !test;
		
		
//		boolean foundMatch;
//		do {
//			foundMatch = false;
//			for(int i=1; i<odd.size(); i++) {
//				V v0 = odd.get(0);
//				V vi  = odd.get(i);
//				if(!target.containsEdge(v0, vi)) {
//					target.addEdge(v0, vi);
//					odd.remove(i);
//					odd.remove(0);
//					foundMatch = true;
//					break;
//				}
//			}
//		} while(foundMatch);
		
		
	}
}
