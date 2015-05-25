package org.haw.lnielsen.gka.graphen.ui.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.haw.lnielsen.gka.graphen.algorithm.path.astar.KnotenHeuristikProvider;
import org.haw.lnielsen.gka.graphen.algorithm.path.astar.LarsAStarShortestPath;
import org.haw.lnielsen.gka.graphen.algorithm.path.dijkstra.JGraphTDijkstraAdapter;
import org.haw.lnielsen.gka.graphen.algorithm.path.dijkstra.JennyDijkstra;
import org.haw.lnielsen.gka.graphen.algorithm.path.dijkstra.LarsDijkstraShortestPath;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.kruskal.JGraphTKruskalSpanningTreeAdapter;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.kruskal.LarsKruskalSpanningTree;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.prim.JGraphTPrimSpanningTreeAdapter;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.prim.LarsPrimSpanningTree;
import org.haw.lnielsen.gka.graphen.generator.GraphFactory;
import org.haw.lnielsen.gka.graphen.io.loader.GKAGraphParser;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParseException;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_I;
import org.haw.lnielsen.gka.graphen.io.store.GKAGraphStorer;
import org.haw.lnielsen.gka.graphen.io.store.GraphStorer_I;
import org.haw.lnielsen.gka.graphen.ui.generator.GraphGeneratorController;
import org.haw.lnielsen.gka.graphen.ui.generator.GraphGeneratorControllerListener_I;
import org.haw.lnielsen.gka.graphen.util.GraphUtils;
import org.haw.lnielsen.gka.graphen.zugriffszaehler.ZugriffszaehlenderGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

import de.xancake.ui.mvc.ControllerListener_I;
import de.xancake.ui.mvc.window.WindowController_A;

/**
 * Der Controller für das Hauptfenster der Benutzeroberfläche.
 * 
 * @author Lars Nielsen
 */
public class GraphEditorWindowController
		extends WindowController_A<Graph<Knoten, DefaultEdge>, GraphEditorWindowListener_I, GraphEditorWindow_I, ControllerListener_I>
		implements GraphEditorWindowListener_I, GraphGeneratorControllerListener_I {
	private static int OPEN_WINDOWS = 0;
	
	private GraphGeneratorController myGraphGenerator;
	private GraphParser_I myParser;
	private GraphStorer_I myStorer;
	private List<ShortestPath_I<Knoten, DefaultEdge>> myShortestPathAlgorithms;
	private List<SpanningTreeAlgorithm_I<Knoten, DefaultEdge>> mySpanningTreeAlgorithms;
	
	public GraphEditorWindowController() {
		super(null, new GraphEditorWindowSwing());
		myGraphGenerator = new GraphGeneratorController();
		myGraphGenerator.addControllerListener(this);
		myParser = new GKAGraphParser();
		myStorer = new GKAGraphStorer();
		myShortestPathAlgorithms = new ArrayList<>();
		myShortestPathAlgorithms.add(new JGraphTDijkstraAdapter<Knoten, DefaultEdge>());
		myShortestPathAlgorithms.add(new LarsDijkstraShortestPath<Knoten, DefaultEdge>());
		myShortestPathAlgorithms.add(new JennyDijkstra<Knoten, DefaultEdge>());
		myShortestPathAlgorithms.add(new LarsAStarShortestPath<Knoten, DefaultEdge>(new KnotenHeuristikProvider()));
		mySpanningTreeAlgorithms = new ArrayList<>();
		mySpanningTreeAlgorithms.add(new JGraphTKruskalSpanningTreeAdapter<Knoten, DefaultEdge>());
		mySpanningTreeAlgorithms.add(new LarsKruskalSpanningTree<Knoten, DefaultEdge>());
		mySpanningTreeAlgorithms.add(new JGraphTPrimSpanningTreeAdapter<Knoten, DefaultEdge>());
		mySpanningTreeAlgorithms.add(new LarsPrimSpanningTree<Knoten, DefaultEdge>());
		getView().setShortestPathAlgorithms(myShortestPathAlgorithms);
		getView().setSpanningTreeAlgorithms(mySpanningTreeAlgorithms);
	}
	
	@Override
	public void onViewOpened() {
		OPEN_WINDOWS++;
	}
	
	@Override
	public void onViewClosed() {
		OPEN_WINDOWS--;
		if(OPEN_WINDOWS==0) {
			System.exit(0);
		}
	}
	
	@Override
	public void onNewGraph() {
		myGraphGenerator.show();
	}
	
	@Override
	public void onLoadGraph(File file) {
		if(file != null && file.exists() && !file.isDirectory()) {
			try {
				setModel(myParser.parseGraph(new FileInputStream(file)));
			} catch(GraphParseException e) {
				getView().showFehlermeldung(e, false);
			} catch(FileNotFoundException e) {
				getView().showFehlermeldung(e, false);
			} catch(IOException e) {
				getView().showFehlermeldung(e, true);
			}
		} else if(!file.exists()) {
			getView().showFehlermeldung("Die angegebene Datei existiert nicht!");
		} else if(file.isDirectory()) {
			getView().showFehlermeldung("Die angegebene Datei ist ein Verzeichnis!");
		}
	}
	
	@Override
	public void onStoreGraph(File file) {
		if(file != null && !file.isDirectory()) {
			if(!file.getName().endsWith(".graph")) {
				file = new File(file.getPath() + ".graph");
			}
			try {
				myStorer.storeGraph(getModel(), new FileOutputStream(file));
			} catch(FileNotFoundException e) {
				getView().showFehlermeldung(e, false);
			} catch(IOException e) {
				getView().showFehlermeldung(e, true);
			}
		} else if(file.isDirectory()) {
			getView().showFehlermeldung("Die angegebene Datei ist ein Verzeichnis!");
		}
	}
	
	@Override
	public void onCalculateGraphWeight() {
		getView().showGraphWeight(GraphUtils.calculateGraphWeight(getModel()));
	}
	
	@Override
	public void onCalculateShortestPath(ShortestPath_I<Knoten, DefaultEdge> algorithm, Knoten start, Knoten end) {
		ZugriffszaehlenderGraph<Knoten, DefaultEdge> graph = GraphFactory.createZugriffszaehlenderGraph(getModel());
		
		GraphPath<Knoten, DefaultEdge> shortestPath = algorithm.calculatePath(graph, start, end);
		getView().showPath(shortestPath, graph.getZugriffsZaehler());
	}
	
	@Override
	public void onTraverse(Knoten start) {
		BreadthFirstIterator<Knoten, DefaultEdge> iterator = new BreadthFirstIterator<Knoten, DefaultEdge>(getModel(), start);
		List<Knoten> knotenList = new ArrayList<Knoten>(getModel().vertexSet().size());
		while(iterator.hasNext()) {
			knotenList.add(iterator.next());
		}
		getView().showTraverseTrace(knotenList);
	}
	
	@Override
	public void onCalculateSpanningTree(SpanningTreeAlgorithm_I<Knoten, DefaultEdge> algorithm) {
		ZugriffszaehlenderGraph<Knoten, DefaultEdge> graph = GraphFactory.createZugriffszaehlenderGraph(getModel());
		Graph<Knoten, DefaultEdge> spanningTree = GraphFactory.createGraph(getModel());
		
		long before = System.nanoTime();
		algorithm.calculateSpanningTree(graph, spanningTree);
		long after = System.nanoTime() - before;
		
		
		GraphEditorWindowController controller = new GraphEditorWindowController();
		controller.setModel(spanningTree);
		controller.show();
		controller.getView().showFehlermeldung("<html>Der Spannbaum wurde <ul><li>in " + after + "ns</li><li>mit " + graph.getZugriffsZaehler() + " Zugriffen</li><li>und " + GraphUtils.calculateGraphWeight(spanningTree) + " Gesamtgewicht</li></ul> berechnet</html>");
	}
	
	@Override
	public void onGraphGenerated(Graph<Knoten, DefaultEdge> graph) {
		setModel(graph);
	}
	
	@Override
	public void onCancel() {
		myGraphGenerator.hide();
	}
	
	@Override
	protected GraphEditorWindowListener_I getViewListener() {
		return this;
	}
}
