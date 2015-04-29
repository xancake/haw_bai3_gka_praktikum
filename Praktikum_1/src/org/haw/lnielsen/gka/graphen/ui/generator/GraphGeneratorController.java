package org.haw.lnielsen.gka.graphen.ui.generator;

import java.util.ArrayList;
import java.util.List;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.haw.lnielsen.gka.graphen.generator.KnotenFactory;
import org.haw.lnielsen.gka.graphen.generator.RandomAttributedKnotenFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.CompleteBipartiteGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.EmptyGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.GraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.LinearGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.RandomGraphGeneratorFactory;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.pattern.listener.Event_A;
import de.xancake.ui.mvc.window.WindowController_A;

/**
 * Der Controller für die Graph-Generator-View.
 * 
 * @author Lars Nielsen
 */
public class GraphGeneratorController
		extends WindowController_A<Void, GraphGeneratorViewListener_I, GraphGeneratorView_I, GraphGeneratorControllerListener_I>
		implements GraphGeneratorViewListener_I {
	public GraphGeneratorController() {
		super(null, new GraphGeneratorViewSwing());
		List<GraphGeneratorFactory<Knoten, DefaultEdge, Knoten>> generatoren = new ArrayList<>();
		generatoren.add(new RandomGraphGeneratorFactory<Knoten, DefaultEdge>());
		generatoren.add(new EmptyGraphGeneratorFactory<Knoten, DefaultEdge>());
		generatoren.add(new LinearGraphGeneratorFactory<Knoten, DefaultEdge>());
		generatoren.add(new CompleteBipartiteGraphGeneratorFactory<Knoten, DefaultEdge>());
		getView().setGraphGenerators(generatoren);
	}
	
	@Override
	public void onViewOpened() {
		
	}
	
	@Override
	public void onViewClosed() {
		
	}
	
	@Override
	public void onGeneratorSelected(GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> generator) {
		generator.getParameterCount();
		
		
		
	}
	
	@Override
	public void onGenerateGraph(boolean attributed, boolean directed, boolean weighted, GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> factory, Integer... parameter) {
		Graph<Knoten, DefaultEdge> graph = GraphFactory.createGraph(directed, weighted);
		
		if(parameter != null && factory.getParameterCount() == parameter.length) {
			GraphGenerator<Knoten, DefaultEdge, Knoten> generator = factory.createGenerator(parameter);
			VertexFactory<Knoten> vertexFactory = attributed ? new RandomAttributedKnotenFactory(100) : new KnotenFactory();
			generator.generateGraph(graph, vertexFactory, null);
			
			fireEvent(new GenerateEvent(graph));
		} else {
			getView().showFehlermeldung("Die Menge der angegebenen Parameter stimmt nicht mit der erwarteten Menge überein.\nBitte geben Sie " + factory.getParameterCount() + " Parameter an.");
		}
	}
	
	@Override
	public void onCancel() {
		fireEvent(new CancelEvent());
	}
	
	@Override
	protected GraphGeneratorViewListener_I getViewListener() {
		return this;
	}
	
	private class GenerateEvent extends Event_A<GraphGeneratorControllerListener_I> {
		private Graph<Knoten, DefaultEdge> myGraph;
		
		public GenerateEvent(Graph<Knoten, DefaultEdge> graph) {
			myGraph = graph;
		}
		
		@Override
		public void fireEvent(GraphGeneratorControllerListener_I l) {
			l.onGraphGenerated(myGraph);
		}
	}
	
	private class CancelEvent extends Event_A<GraphGeneratorControllerListener_I> {
		@Override
		public void fireEvent(GraphGeneratorControllerListener_I l) {
			l.onCancel();
		}
	}
}
