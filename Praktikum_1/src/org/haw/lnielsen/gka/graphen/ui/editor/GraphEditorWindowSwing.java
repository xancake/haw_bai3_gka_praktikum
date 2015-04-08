package org.haw.lnielsen.gka.graphen.ui.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.ui.swing.GraphFileFilter;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphSelectionModel;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import de.xancake.ui.mvc.window.SwingWindowView_A;

/**
 * Die Swing-Implementation des Hauptfensters der Benutzeroberfläche.
 * 
 * @author Lars Nielsen
 */
public class GraphEditorWindowSwing extends SwingWindowView_A<Graph<Knoten, DefaultEdge>, GraphEditorWindowListener_I> implements GraphEditorWindow_I {
	private JButton myNewButton;
	private JButton myLoadButton;
	private JButton myStoreButton;
	private JButton myShortestPathButton;
	
	private JFileChooser myChooser;
	private JGraph myGraphComponent;
	
	public GraphEditorWindowSwing() {
		super("Graph");
	}
	
	@Override
	protected void initComponents() {
		File chooserCurrentDirectory = null;
		try {
			chooserCurrentDirectory = new File(ClassLoader.getSystemResource("loader").toURI());
		} catch(URISyntaxException e) {}
		myChooser = new JFileChooser(chooserCurrentDirectory);
		myChooser.setMultiSelectionEnabled(false);
		myChooser.setFileFilter(new GraphFileFilter());
		myGraphComponent = new JGraph();
		myGraphComponent.setEditable(false);
		myNewButton = new JButton("Neu...");
		myLoadButton = new JButton("Laden");
		myStoreButton = new JButton("Speichern");
		myShortestPathButton = new JButton("Kürzester Weg");
	}
	
	@Override
	protected void initLayout(Container content) {
		JToolBar toolbar = new JToolBar();
		toolbar.add(myNewButton);
		toolbar.add(myLoadButton);
		toolbar.add(myStoreButton);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(myNewButton);
		buttonPanel.add(myLoadButton);
		buttonPanel.add(myStoreButton);
		buttonPanel.add(Box.createHorizontalStrut(11));
		buttonPanel.add(myShortestPathButton);
		buttonPanel.add(Box.createHorizontalGlue());
		
		content.add(buttonPanel, BorderLayout.PAGE_START);
		content.add(myGraphComponent, BorderLayout.CENTER);
		myFrame.setSize(800, 600);
		myFrame.setLocationRelativeTo(null);
	}
	
	@Override
	protected void initListeners() {
		myNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myListener.onNewGraph();
			}
		});
		myLoadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(myChooser.showOpenDialog(myFrame) == JFileChooser.APPROVE_OPTION) {
					myListener.onLoadGraph(myChooser.getSelectedFile());
				}
			}
		});
		myStoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(myChooser.showSaveDialog(myFrame) == JFileChooser.APPROVE_OPTION) {
					myListener.onStoreGraph(myChooser.getSelectedFile());
				}
			}
		});
		myShortestPathButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphSelectionModel selectionModel = myGraphComponent.getSelectionModel();
				if(selectionModel.getSelectionCount() == 2) {
					Object[] selectedElements = selectionModel.getSelectionCells();
					Knoten start = (Knoten)((DefaultGraphCell)selectedElements[0]).getUserObject();
					Knoten end = (Knoten)((DefaultGraphCell)selectedElements[1]).getUserObject();
					myListener.onCalculateShortestPath(start, end);
				} else {
					JOptionPane.showMessageDialog(myFrame, "Es kann nur der k�rzeste Pfad zwischen zwei Knoten berechnet werden. Bitte w�hlen Sie genau zwei Knoten aus (Strg+Mausklick).", "Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	@Override
	public void fillViewWithModel(Graph<Knoten, DefaultEdge> model) {
		myGraphComponent.clearSelection();
		myGraphComponent.setModel(model != null ? new JGraphModelAdapter<Knoten, DefaultEdge>(model) : null);
		myGraphComponent.setEnabled(model != null);
	}
	
	@Override
	public void showPath(GraphPath<Knoten, DefaultEdge> path) {
		StringBuilder message = new StringBuilder();
		if(path != null) {
			Graph<Knoten, DefaultEdge> graph = path.getGraph();
			message.append("<html>Der kürzeste Weg von ");
			message.append(path.getStartVertex());
			message.append(" nach ");
			message.append(path.getEndVertex());
			message.append(" führt über die Kanten <ol>");
			Knoten start = path.getStartVertex();
			for(DefaultEdge edge : path.getEdgeList()) {
				Knoten edgeStart = graph.getEdgeSource(edge);
				Knoten edgeTarget = graph.getEdgeTarget(edge);
				message.append("<li>");
				message.append(start.equals(edgeStart) ? edgeStart : edgeTarget);
				message.append(" - ");
				message.append(start.equals(edgeStart) ? edgeTarget : edgeStart);
				message.append("</li>");
				start = (start.equals(edgeStart) ? edgeTarget : edgeStart);
			}
			message.append("</ol> und hat ein Gewicht von ");
			message.append((int)path.getWeight());
			message.append("</html>");
		} else {
			Object[] selectedElements = myGraphComponent.getSelectionModel().getSelectionCells();
			message.append("Es gibt keinen Pfad von ");
			message.append(selectedElements[0]);
			message.append(" nach ");
			message.append(selectedElements[1]);
		}
		JOptionPane.showMessageDialog(myFrame, message.toString(), "Kürzester Weg", JOptionPane.PLAIN_MESSAGE);
	}
}
