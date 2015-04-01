package org.haw.lnielsen.gka.graphen.ui.simple;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.loader.GraphParser_GKA;
import org.haw.lnielsen.gka.graphen.loader.GraphParser_I;
import org.jgraph.JGraph;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;

public class SimpleGraphFrameSwing implements SimpleGraphUI_I {
	private JFileChooser myChooser;
	private JFrame myFrame;
	private JGraph myGraphComponent;
	
	public SimpleGraphFrameSwing() {
		try {
			myChooser = new JFileChooser(new File(ClassLoader.getSystemResource("loader").toURI()));
		} catch(URISyntaxException e) {
			throw new RuntimeException("Fehler beim Laden des 'loader'-Ordners", e);
		}
		myChooser.setMultiSelectionEnabled(false);
		myChooser.setFileFilter(new FileFilter() {
			private static final String FILE_ENDING = ".graph";
			
			@Override
			public String getDescription() {
				return FILE_ENDING;
			}
			
			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(FILE_ENDING);
			}
		});
		myFrame = new JFrame("Graph");
		myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		myFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					selectGraphFromFile();
				} catch(FileNotFoundException exc) {
					exc.printStackTrace();
				} catch(IOException exc) {
					exc.printStackTrace();
				}
			}
		});
		myGraphComponent = new JGraph();
		myGraphComponent.setModel(null);
		myFrame.setContentPane(myGraphComponent);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);
	}
	
	@Override
	public void showGraph(Graph<Knoten, DefaultEdge> graph) {
		JGraphModelAdapter<Knoten, DefaultEdge> modelAdapter = new JGraphModelAdapter<Knoten, DefaultEdge>(graph);
		myGraphComponent.setModel(modelAdapter);
	}
	
	public void selectGraphFromFile() throws FileNotFoundException, IOException {
		switch(myChooser.showOpenDialog(myFrame)) {
			case JFileChooser.APPROVE_OPTION:
				GraphParser_I parser = new GraphParser_GKA();
				showGraph(parser.parseGraph(new FileInputStream(myChooser.getSelectedFile())));
				break;
			default:
				System.exit(0);
		}
	}
	
	public static void main(String... args) throws Exception {
		SimpleGraphFrameSwing view = new SimpleGraphFrameSwing();
		view.selectGraphFromFile();
	}
}
