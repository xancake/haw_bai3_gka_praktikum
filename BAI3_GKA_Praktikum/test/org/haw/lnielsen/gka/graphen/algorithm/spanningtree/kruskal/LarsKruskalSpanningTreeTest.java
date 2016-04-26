package org.haw.lnielsen.gka.graphen.algorithm.spanningtree.kruskal;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithmTest_A;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.jgrapht.graph.DefaultEdge;

public class LarsKruskalSpanningTreeTest extends SpanningTreeAlgorithmTest_A {
	@Override
	protected SpanningTreeAlgorithm_I<Knoten, DefaultEdge> createSpanningTreeAlgorithm() {
		return new LarsKruskalSpanningTree<>();
	}
}
