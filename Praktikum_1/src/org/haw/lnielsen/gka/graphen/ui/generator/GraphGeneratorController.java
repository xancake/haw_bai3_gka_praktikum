package org.haw.lnielsen.gka.graphen.ui.generator;

import java.util.ArrayList;
import java.util.List;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.path.astar.KnotenHeuristikProvider;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.haw.lnielsen.gka.graphen.generator.GraphWeighter;
import org.haw.lnielsen.gka.graphen.generator.vertex.KnotenFactory;
import org.haw.lnielsen.gka.graphen.generator.vertex.RandomAttributedKnotenFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.CompleteBipartiteGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.CompleteGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.EmptyGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.GraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.GridGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.LinearGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.RandomGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.RingGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.ScaleFreeGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.StarGraphGeneratorFactory;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.WheelGraphGeneratorFactory;
import org.jgrapht.Graph;
import org.jgrapht.VertexFactory;
import org.jgrapht.WeightedGraph;
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
		generatoren.add(new RingGraphGeneratorFactory<Knoten, DefaultEdge>());
		generatoren.add(new WheelGraphGeneratorFactory<Knoten, DefaultEdge>());
		generatoren.add(new StarGraphGeneratorFactory<Knoten, DefaultEdge>());
		generatoren.add(new GridGraphGeneratorFactory<Knoten, DefaultEdge>());
		generatoren.add(new CompleteGraphGeneratorFactory<Knoten, DefaultEdge>());
		generatoren.add(new CompleteBipartiteGraphGeneratorFactory<Knoten, DefaultEdge>());
		generatoren.add(new ScaleFreeGraphGeneratorFactory<Knoten, DefaultEdge>());
		getView().setGraphGenerators(generatoren);
		getView().enableAttributeConfiguration(false);
		getView().enableWeightConfiguration(false);
	}
	
	@Override
	public void onViewOpened() {
		
	}
	
	@Override
	public void onViewClosed() {
		
	}
	
	@Override
	public void onAttributedSelected(boolean selected) {
		getView().enableAttributeConfiguration(selected);
	}
	
	@Override
	public void onDirectedSelected(boolean selected) {
		
	}
	
	@Override
	public void onWeightedSelected(boolean selected) {
		getView().enableWeightConfiguration(selected);
	}
	
	@Override
	public void onGeneratorSelected(GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> generator) {
		getView().setGeneratorParameters(generator.getParameterNames());
	}
	
	@Override
	public void onGenerateGraph(boolean attributed, boolean directed, boolean weighted, GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> factory, Integer... parameter) {
		if(parameter != null && factory.getParameterCount() == parameter.length) {
			Graph<Knoten, DefaultEdge> graph = GraphFactory.createGraph(directed, weighted);
			
			VertexFactory<Knoten> vertexFactory = null;
			if(attributed) {
				if(getView().getAttributeMinValue() <= getView().getAttributeMaxValue()) {
					vertexFactory = new RandomAttributedKnotenFactory(getView().getAttributeMinValue(), getView().getAttributeMaxValue());
				} else {
					getView().showFehlermeldung("Der Minimalwert für die Attributierung muss kleiner oder gleich dem Maximalwert sein!");
					return;
				}
			} else {
				vertexFactory = new KnotenFactory();
			}
			
			try {
				GraphGenerator<Knoten, DefaultEdge, Knoten> generator = factory.createGenerator(parameter);
				generator.generateGraph(graph, vertexFactory, null);
				
				if(weighted) {
					int weightModifikator = getView().getWeightModifier();
					GraphWeighter<Knoten, DefaultEdge> weighter = attributed
							? new GraphWeighter<Knoten, DefaultEdge>(new KnotenHeuristikProvider(), weightModifikator)
							: new GraphWeighter<Knoten, DefaultEdge>(weightModifikator);
					weighter.appendGraphWeights((WeightedGraph<Knoten, DefaultEdge>)graph);
				}
				
				fireEvent(new GenerateEvent(graph));
			} catch(IllegalArgumentException e) {
				getView().showFehlermeldung(e, false);
			}
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
