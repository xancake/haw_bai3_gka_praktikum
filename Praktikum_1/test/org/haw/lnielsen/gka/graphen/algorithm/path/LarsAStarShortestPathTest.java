package org.haw.lnielsen.gka.graphen.algorithm.path;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.path.astar.KnotenAStarProvider;
import org.haw.lnielsen.gka.graphen.algorithm.path.astar.LarsAStarShortestPath;
import org.jgrapht.graph.DefaultEdge;

public class LarsAStarShortestPathTest extends ShortestPathTest_A {
	@Override
	protected ShortestPath_I<Knoten, DefaultEdge> createShortestPathAlgorithm() {
		return new LarsAStarShortestPath<Knoten, DefaultEdge>(new KnotenAStarProvider());
	}
}
