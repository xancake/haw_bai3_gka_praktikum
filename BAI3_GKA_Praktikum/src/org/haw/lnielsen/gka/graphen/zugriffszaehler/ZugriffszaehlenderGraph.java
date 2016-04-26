package org.haw.lnielsen.gka.graphen.zugriffszaehler;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;

/**
 * Eine Klasse die es ermöglicht, Zugriffe auf einen Graphen zu zählen.
 * Ein Objekt dieser Klasse umschließt einen Graphen und delegiert die Methodenaufrufe
 * an das interne Objekt. Dabei wird bei allen Methodenaufrufen ein Zähler hochgezählt.
 * 
 * @author Lars Nielsen
 */
public class ZugriffszaehlenderGraph<V, E> implements Graph<V, E> {
	private Graph<V, E> myGraph;
	protected int myLesenZaehler;
	protected int mySchreibenZaehler;
	
	/**
	 * Initialisiert den zugriffszählenden Graphen mit dem übergebenen Graphen.
	 * @param graph Ein Graph, darf nicht {@code null} sein
	 */
	public ZugriffszaehlenderGraph(Graph<V, E> graph) {
		myGraph = Objects.requireNonNull(graph);
	}
	
	/**
	 * Gibt zurück, wie oft lesend auf den Graphen zugegriffen wurde.
	 * @return Die Häufigkeit der lesenden Zugriffe
	 */
	public int getLesenZaehler() {
		return myLesenZaehler;
	}
	
	/**
	 * Gibt zurück, wie oft schreibend auf den Graphen zugegriffen wurde.
	 * @return Die Häufigkeit der schreibenden Zugriffe
	 */
	public int getSchreibenZaehler() {
		return mySchreibenZaehler;
	}
	
	/**
	 * Gibt zurück, wie oft insgesamt auf den Graphen zugegriffen wurde.
	 * @return Die Häufigkeit aller Zugriffe
	 */
	public int getZugriffsZaehler() {
		return myLesenZaehler + mySchreibenZaehler;
	}
	
	/**
	 * Setzt alle Zähler zurück.
	 */
	public void resetZaehler() {
		myLesenZaehler = 0;
		mySchreibenZaehler = 0;
	}
	
	@Override
	public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
		myLesenZaehler++;
		return myGraph.getAllEdges(sourceVertex, targetVertex);
	}

	@Override
	public E getEdge(V sourceVertex, V targetVertex) {
		myLesenZaehler++;
		return myGraph.getEdge(sourceVertex, targetVertex);
	}

	@Override
	public Set<E> edgeSet() {
		myLesenZaehler++;
		return myGraph.edgeSet();
	}

	@Override
	public Set<E> edgesOf(V vertex) {
		myLesenZaehler++;
		return myGraph.edgesOf(vertex);
	}

	@Override
	public Set<V> vertexSet() {
		myLesenZaehler++;
		return myGraph.vertexSet();
	}

	@Override
	public V getEdgeSource(E e) {
		myLesenZaehler++;
		return myGraph.getEdgeSource(e);
	}

	@Override
	public V getEdgeTarget(E e) {
		myLesenZaehler++;
		return myGraph.getEdgeTarget(e);
	}

	@Override
	public double getEdgeWeight(E e) {
		myLesenZaehler++;
		return myGraph.getEdgeWeight(e);
	}

	@Override
	public boolean containsEdge(V sourceVertex, V targetVertex) {
		myLesenZaehler++;
		return myGraph.containsEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean containsEdge(E e) {
		myLesenZaehler++;
		return myGraph.containsEdge(e);
	}

	@Override
	public boolean containsVertex(V v) {
		myLesenZaehler++;
		return myGraph.containsVertex(v);
	}

	@Override
	public EdgeFactory<V, E> getEdgeFactory() {
		myLesenZaehler++;
		return myGraph.getEdgeFactory();
	}

	@Override
	public E addEdge(V sourceVertex, V targetVertex) {
		mySchreibenZaehler++;
		return myGraph.addEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean addEdge(V sourceVertex, V targetVertex, E e) {
		mySchreibenZaehler++;
		return myGraph.addEdge(sourceVertex, targetVertex, e);
	}

	@Override
	public boolean addVertex(V v) {
		mySchreibenZaehler++;
		return myGraph.addVertex(v);
	}

	@Override
	public boolean removeAllEdges(Collection<? extends E> edges) {
		mySchreibenZaehler++;
		return myGraph.removeAllEdges(edges);
	}

	@Override
	public Set<E> removeAllEdges(V sourceVertex, V targetVertex) {
		mySchreibenZaehler++;
		return myGraph.removeAllEdges(sourceVertex, targetVertex);
	}

	@Override
	public boolean removeAllVertices(Collection<? extends V> vertices) {
		mySchreibenZaehler++;
		return myGraph.removeAllVertices(vertices);
	}

	@Override
	public E removeEdge(V sourceVertex, V targetVertex) {
		mySchreibenZaehler++;
		return myGraph.removeEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean removeEdge(E e) {
		mySchreibenZaehler++;
		return myGraph.removeEdge(e);
	}

	@Override
	public boolean removeVertex(V v) {
		mySchreibenZaehler++;
		return myGraph.removeVertex(v);
	}
}
