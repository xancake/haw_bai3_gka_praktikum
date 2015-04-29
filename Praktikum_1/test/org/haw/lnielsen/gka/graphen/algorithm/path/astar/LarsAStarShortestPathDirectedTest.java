package org.haw.lnielsen.gka.graphen.algorithm.path.astar;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPathDirectedTest_A;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.jgrapht.graph.DefaultEdge;

public class LarsAStarShortestPathDirectedTest extends ShortestPathDirectedTest_A {
	@Override
	protected ShortestPath_I<Knoten, DefaultEdge> createShortestPathAlgorithm() {
		return new LarsAStarShortestPath<Knoten, DefaultEdge>(new KnotenHeuristikProvider());
	}
}
