package org.haw.lnielsen.gka.graphen.algorithm.path.astar;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPathUndirectedTest_A;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.haw.lnielsen.gka.graphen.algorithm.path.astar.KnotenAStarProvider;
import org.haw.lnielsen.gka.graphen.algorithm.path.astar.LarsAStarShortestPath;
import org.jgrapht.graph.DefaultEdge;

public class LarsAStarShortestPathUndirectedTest extends ShortestPathUndirectedTest_A {
	@Override
	protected ShortestPath_I<Knoten, DefaultEdge> createShortestPathAlgorithm() {
		return new LarsAStarShortestPath<Knoten, DefaultEdge>(new KnotenAStarProvider());
	}
}
