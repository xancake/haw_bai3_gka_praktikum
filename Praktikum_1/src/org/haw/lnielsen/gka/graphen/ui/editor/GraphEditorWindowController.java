package org.haw.lnielsen.gka.graphen.ui.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.KnotenAStarProvider;
import org.haw.lnielsen.gka.graphen.algorithm.path.JGraphTDijkstraAdapter;
import org.haw.lnielsen.gka.graphen.algorithm.path.LarsAStarShortestPath;
import org.haw.lnielsen.gka.graphen.algorithm.path.LarsDijkstraShortestPath;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.haw.lnielsen.gka.graphen.io.loader.GKAGraphParser;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParseException;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_I;
import org.haw.lnielsen.gka.graphen.io.store.GKAGraphFileStorer;
import org.haw.lnielsen.gka.graphen.io.store.GraphStorer_I;
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
public class GraphEditorWindowController extends WindowController_A<Graph<Knoten, DefaultEdge>, GraphEditorWindowListener_I, GraphEditorWindow_I, ControllerListener_I> implements GraphEditorWindowListener_I {
	private GraphParser_I myParser;
	private GraphStorer_I myStorer;
	private List<ShortestPath_I<Knoten, DefaultEdge>> myShortestPathAlgorithms;
	
	public GraphEditorWindowController() {
		super(null, new GraphEditorWindowSwing());
		myParser = new GKAGraphParser();
		myStorer = new GKAGraphFileStorer();
		myShortestPathAlgorithms = new ArrayList<>();
		myShortestPathAlgorithms.add(new JGraphTDijkstraAdapter<Knoten, DefaultEdge>());
		myShortestPathAlgorithms.add(new LarsDijkstraShortestPath<Knoten, DefaultEdge>());
		myShortestPathAlgorithms.add(new LarsAStarShortestPath<Knoten, DefaultEdge>(new KnotenAStarProvider()));
		getView().setShortestPathAlgorithms(myShortestPathAlgorithms);
	}
	
	@Override
	public void onViewOpened() {
		
	}
	
	@Override
	public void onViewClosed() {
		System.exit(0);
	}
	
	@Override
	public void onNewGraph() {
		
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
		if(file != null && file.exists() && !file.isDirectory()) {
			try {
				myStorer.storeGraph(getModel(), new FileOutputStream(file));
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
	public void onCalculateShortestPath(ShortestPath_I<Knoten, DefaultEdge> algorithm, Knoten start, Knoten end) {
		GraphPath<Knoten, DefaultEdge> shortestPath = algorithm.calculatePath(getModel(), start, end);
		getView().showPath(shortestPath);
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
	protected GraphEditorWindowListener_I getViewListener() {
		return this;
	}
	
	public static void main(String... args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
		new GraphEditorWindowController().show();
	}
}
