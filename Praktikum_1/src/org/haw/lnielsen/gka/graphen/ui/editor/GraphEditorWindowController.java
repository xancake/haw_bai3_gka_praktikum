package org.haw.lnielsen.gka.graphen.ui.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParseException;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_GKA;
import org.haw.lnielsen.gka.graphen.io.loader.GraphParser_I;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;

import de.xancake.ui.mvc.ControllerListener_I;
import de.xancake.ui.mvc.window.WindowController_A;

/**
 * Der Controller f�r das Hauptfenster der Benutzeroberfl�che.
 * 
 * @author Lars Nielsen
 */
public class GraphEditorWindowController extends WindowController_A<Graph<Knoten, DefaultEdge>, GraphEditorWindowListener_I, GraphEditorWindow_I, ControllerListener_I> implements GraphEditorWindowListener_I {
	private GraphParser_I myParser;
	
	public GraphEditorWindowController() {
		super(null, new GraphEditorWindowSwing());
		myParser = new GraphParser_GKA();
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
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch(GraphParseException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else if(!file.exists()) {
			
		} else if(file.isDirectory()) {
			
		}
	}
	
	@Override
	public void onStoreGraph(File file) {
		
		
		
	}
	
	@Override
	public void onCalculateShortestPath(Knoten start, Knoten end) {
		DijkstraShortestPath<Knoten, DefaultEdge> algorithm = new DijkstraShortestPath<Knoten, DefaultEdge>(getModel(), start, end);
		GraphPath<Knoten, DefaultEdge> shortestPath = algorithm.getPath();
		getView().showPath(shortestPath);
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
	
	public void ShortestPath(Knoten start, Knoten end) {
		BreadthFirstIterator<Knoten, DefaultEdge> iterator = new BreadthFirstIterator<Knoten, DefaultEdge>(getModel(), start);
		int i = 0;
		start.setzeWert(i);
		for(int j = 0;j<=getModel().vertexSet().size();j++){
			if(iterator.hasNext()){
				i++;
				iterator.next().setzeWert(i);
			}
		}
	}
}
