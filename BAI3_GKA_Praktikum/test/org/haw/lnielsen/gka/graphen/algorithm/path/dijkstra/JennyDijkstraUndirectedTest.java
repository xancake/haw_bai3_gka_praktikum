package org.haw.lnielsen.gka.graphen.algorithm.path.dijkstra;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPathUndirectedTest_A;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.haw.lnielsen.gka.graphen.algorithm.path.dijkstra.JennyDijkstra;
import org.jgrapht.graph.DefaultEdge;

public class JennyDijkstraUndirectedTest extends ShortestPathUndirectedTest_A {
	@Override
	protected ShortestPath_I<Knoten, DefaultEdge> createShortestPathAlgorithm() {
		return new JennyDijkstra<>();
	}
}
