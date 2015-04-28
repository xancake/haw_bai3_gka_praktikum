package org.haw.lnielsen.gka.graphen.zugriffszaehler;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;

public class ZugriffszaehlenderGraph<V, E> implements Graph<V, E> {
	private Graph<V, E> myGraph;
	protected int myZugriffsZaehler;
	
	public ZugriffszaehlenderGraph(Graph<V, E> graph) {
		myGraph = Objects.requireNonNull(graph);
	}
	
	public int getZugriffsZaehler() {
		return myZugriffsZaehler;
	}
	
	public void resetZugriffsZaehler() {
		myZugriffsZaehler = 0;
	}
	
	@Override
	public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
		myZugriffsZaehler++;
		return myGraph.getAllEdges(sourceVertex, targetVertex);
	}

	@Override
	public E getEdge(V sourceVertex, V targetVertex) {
		myZugriffsZaehler++;
		return myGraph.getEdge(sourceVertex, targetVertex);
	}

	@Override
	public Set<E> edgeSet() {
		myZugriffsZaehler++;
		return myGraph.edgeSet();
	}

	@Override
	public Set<E> edgesOf(V vertex) {
		myZugriffsZaehler++;
		return myGraph.edgesOf(vertex);
	}

	@Override
	public Set<V> vertexSet() {
		myZugriffsZaehler++;
		return myGraph.vertexSet();
	}

	@Override
	public V getEdgeSource(E e) {
		myZugriffsZaehler++;
		return myGraph.getEdgeSource(e);
	}

	@Override
	public V getEdgeTarget(E e) {
		myZugriffsZaehler++;
		return myGraph.getEdgeTarget(e);
	}

	@Override
	public double getEdgeWeight(E e) {
		myZugriffsZaehler++;
		return myGraph.getEdgeWeight(e);
	}

	@Override
	public EdgeFactory<V, E> getEdgeFactory() {
		return myGraph.getEdgeFactory();
	}

	@Override
	public E addEdge(V sourceVertex, V targetVertex) {
		return myGraph.addEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean addEdge(V sourceVertex, V targetVertex, E e) {
		return myGraph.addEdge(sourceVertex, targetVertex, e);
	}

	@Override
	public boolean addVertex(V v) {
		return myGraph.addVertex(v);
	}

	@Override
	public boolean containsEdge(V sourceVertex, V targetVertex) {
		return myGraph.containsEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean containsEdge(E e) {
		return myGraph.containsEdge(e);
	}

	@Override
	public boolean containsVertex(V v) {
		return myGraph.containsVertex(v);
	}

	@Override
	public boolean removeAllEdges(Collection<? extends E> edges) {
		return myGraph.removeAllEdges(edges);
	}

	@Override
	public Set<E> removeAllEdges(V sourceVertex, V targetVertex) {
		return myGraph.removeAllEdges(sourceVertex, targetVertex);
	}

	@Override
	public boolean removeAllVertices(Collection<? extends V> vertices) {
		return myGraph.removeAllVertices(vertices);
	}

	@Override
	public E removeEdge(V sourceVertex, V targetVertex) {
		return myGraph.removeEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean removeEdge(E e) {
		return myGraph.removeEdge(e);
	}

	@Override
	public boolean removeVertex(V v) {
		return myGraph.removeVertex(v);
	}
}
