package org.haw.lnielsen.gka.graphen.ui.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.algorithm.path.ShortestPath_I;
import org.haw.lnielsen.gka.graphen.algorithm.spanningtree.SpanningTreeAlgorithm_I;
import org.haw.lnielsen.gka.graphen.ui.swing.GraphFileFilter;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphSelectionModel;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;

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
	private JComboBox<ShortestPath_I<Knoten, DefaultEdge>> myShortestPathAlgorithms;
	private JButton myShortestPathButton;
	private JButton myTraverseButton;
	private JComboBox<SpanningTreeAlgorithm_I<Knoten, DefaultEdge>> mySpanningTreeAlgorithms;
	private JButton mySpanningTreeButton;
	
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
		myShortestPathAlgorithms = new JComboBox<>();
		mySpanningTreeAlgorithms = new JComboBox<>();
		myGraphComponent = new JGraph();
		myNewButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/file_new.gif")));
		myLoadButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/file_open.gif")));
		myStoreButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/file_save.gif")));
		myShortestPathButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/shortestpath2.png")));
		myTraverseButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/traversieren2.png")));
		mySpanningTreeButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("img/spanningtree2.png")));
		
		myNewButton.setToolTipText("Neu...");
		myLoadButton.setToolTipText("Laden");
		myStoreButton.setToolTipText("Speichern");
		myShortestPathButton.setToolTipText("Kürzester Weg");
		myTraverseButton.setToolTipText("Traversieren");
		mySpanningTreeButton.setToolTipText("Spannbaum");
		
		myGraphComponent.setEditable(false);
		myShortestPathButton.setEnabled(false);
		mySpanningTreeButton.setEnabled(false);
	}
	
	@Override
	protected void initLayout(JPanel content) {
		content.setLayout(new BorderLayout());
		
		JToolBar toolbar = new JToolBar();
		toolbar.add(myNewButton);
		toolbar.add(myLoadButton);
		toolbar.add(myStoreButton);
		toolbar.addSeparator();
		toolbar.add(myShortestPathAlgorithms);
		toolbar.add(myShortestPathButton);
		toolbar.add(myTraverseButton);
		toolbar.add(mySpanningTreeAlgorithms);
		toolbar.add(mySpanningTreeButton);
		toolbar.add(Box.createGlue());
		
		content.add(toolbar, BorderLayout.PAGE_START);
		content.add(new JScrollPane(myGraphComponent), BorderLayout.CENTER);
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
		myShortestPathAlgorithms.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myShortestPathButton.setEnabled(myShortestPathAlgorithms.getSelectedItem() != null);
			}
		});
		mySpanningTreeAlgorithms.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mySpanningTreeButton.setEnabled(mySpanningTreeAlgorithms.getSelectedItem() != null);
			}
		});
		myShortestPathButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphSelectionModel selectionModel = myGraphComponent.getSelectionModel();
				if(selectionModel.getSelectionCount() == 2) {
					Object[] selectedElements = selectionModel.getSelectionCells();
					Object firstUserObject  = ((DefaultGraphCell)selectedElements[0]).getUserObject();
					Object secondUserObject = ((DefaultGraphCell)selectedElements[1]).getUserObject();
					if(firstUserObject instanceof Knoten && secondUserObject instanceof Knoten) {
						Knoten start = (Knoten)firstUserObject;
						Knoten end = (Knoten)secondUserObject;
						myListener.onCalculateShortestPath((ShortestPath_I<Knoten, DefaultEdge>)myShortestPathAlgorithms.getSelectedItem(), start, end);
					} else {
						showFehlermeldung("Sie müssen zwei 'Knoten' auswählen.");
					}
				} else {
					showFehlermeldung("Es kann nur der kürzeste Pfad zwischen zwei Knoten berechnet werden. Bitte wählen Sie genau zwei Knoten aus (Strg+Mausklick).");
				}
			}
		});
		myTraverseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphSelectionModel selectionModel = myGraphComponent.getSelectionModel();
				if(selectionModel.getSelectionCount() == 1) {
					Object selectedElement = ((DefaultGraphCell)selectionModel.getSelectionCell()).getUserObject();
					if(selectedElement instanceof Knoten) {
						Knoten start = (Knoten)selectedElement;
						myListener.onTraverse(start);
					} else {
						showFehlermeldung("Sie müssen einen 'Knoten' auswählen.");
					}
				} else {
					showFehlermeldung("Es kann nur ein Knoten als Startknoten ausgewählt werden. Bitte wählen Sie genau einen Knoten aus.");
				}
			}
		});
		mySpanningTreeButton.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				SpanningTreeAlgorithm_I<Knoten, DefaultEdge> algorithm = (SpanningTreeAlgorithm_I<Knoten, DefaultEdge>)mySpanningTreeAlgorithms.getSelectedItem();
				myListener.onCalculateSpanningTree(algorithm);
			}
		});
	}

	@Override
	public void fillViewWithModel(Graph<Knoten, DefaultEdge> model) {
		myGraphComponent.clearSelection();
		myGraphComponent.setModel(model != null ? new JGraphModelAdapter<Knoten, DefaultEdge>(model) : null);
		myGraphComponent.setEnabled(model != null);
		
		if(model != null) {
			JGraphFacade facade = new JGraphFacade(myGraphComponent);
			JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE);
			layout.run(facade);
			myGraphComponent.getGraphLayoutCache().edit(facade.createNestedMap(true, true));
		}
	}
	
	@Override
	public void setShortestPathAlgorithms(List<ShortestPath_I<Knoten, DefaultEdge>> algorithms) {
		DefaultComboBoxModel<ShortestPath_I<Knoten, DefaultEdge>> model = new DefaultComboBoxModel<>();
		for(ShortestPath_I<Knoten, DefaultEdge> algorithm : algorithms) {
			model.addElement(algorithm);
		}
		myShortestPathAlgorithms.setModel(model);
		if(!algorithms.isEmpty()) {
			model.setSelectedItem(algorithms.get(0));
			myShortestPathButton.setEnabled(true);
		}
	}
	
	@Override
	public void setSpanningTreeAlgorithms(List<SpanningTreeAlgorithm_I<Knoten, DefaultEdge>> algorithms) {
		DefaultComboBoxModel<SpanningTreeAlgorithm_I<Knoten, DefaultEdge>> model = new DefaultComboBoxModel<>();
		for(SpanningTreeAlgorithm_I<Knoten, DefaultEdge> algorithm : algorithms) {
			model.addElement(algorithm);
		}
		mySpanningTreeAlgorithms.setModel(model);
		if(!algorithms.isEmpty()) {
			model.setSelectedItem(algorithms.get(0));
			mySpanningTreeButton.setEnabled(true);
		}
	}
	
	@Override
	public void showPath(GraphPath<Knoten, DefaultEdge> path, int anzahlZugriffe) {
		StringBuilder message = new StringBuilder();
		if(path != null) {
			Graph<Knoten, DefaultEdge> graph = path.getGraph();
			message.append("<html>Der kürzeste Weg von <b>");
			message.append(path.getStartVertex());
			message.append("</b> nach <b>");
			message.append(path.getEndVertex());
			message.append("</b> führt über die Kanten <ol>");
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
			message.append("</ol> und hat ein Gewicht von <b>");
			message.append((int)path.getWeight());
			message.append("</b><br />Für diese Berechnung wurden <b>");
			message.append(anzahlZugriffe);
			message.append("</b> Zugriffe auf den Graphen durchgeführt!");
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
	
	@Override
	public void showTraverseTrace(List<Knoten> trace) {
		StringBuilder message = new StringBuilder();
		message.append("<html>Der Graph wurde in folgender Knotenreihenfolge traversiert:<ol>");
		for(Knoten k : trace) {
			message.append("<li>");
			message.append(k);
			message.append("</li>");
		}
		message.append("</ol></html>");
		JOptionPane.showMessageDialog(myFrame, message.toString(), "Traversierung", JOptionPane.PLAIN_MESSAGE);
	}
	
	@Override
	public void showFehlermeldung(String message) {
		JOptionPane.showMessageDialog(myFrame, message, "Fehler", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void showFehlermeldung(Throwable exception, boolean showTrace) {
		String message = "<html>";
		while(exception != null) {
			message += exception.getMessage() + "<br />";
			if(showTrace) {
				for(StackTraceElement traceElement : exception.getStackTrace()) {
					message += traceElement.toString() + "<br />";
				}
			}
			exception = exception.getCause();
		}
		message += "</html>";
		showFehlermeldung(message);
	}
}
