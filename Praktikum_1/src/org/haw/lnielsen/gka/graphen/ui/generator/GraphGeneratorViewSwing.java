package org.haw.lnielsen.gka.graphen.ui.generator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.GraphGeneratorFactory;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.window.SwingWindowView_A;

public class GraphGeneratorViewSwing
		extends SwingWindowView_A<Void, GraphGeneratorViewListener_I>
		implements GraphGeneratorView_I {
	private JCheckBox myAttributedCheckBox;
	private JCheckBox myDirectedCheckBox;
	private JCheckBox myWeightedCheckBox;
	
	private JComboBox<GraphGeneratorFactory<Knoten, DefaultEdge, Knoten>> myGeneratorFactories;
	private JLabel myParameterLabel;
	private JTextField myParameterField;
	
	private JButton myNewButton;
	private JButton myCancelButton;
	
	public GraphGeneratorViewSwing() {
		super("Graph Generator");
	}
	
	@Override
	protected void initComponents() {
		myAttributedCheckBox = new JCheckBox("Attributiert");
		myDirectedCheckBox = new JCheckBox("Gerichtet");
		myWeightedCheckBox = new JCheckBox("Gewichtet");
		myGeneratorFactories = new JComboBox<>();
		myParameterLabel = new JLabel("<Parameter-Template>", SwingConstants.LEFT);
		myParameterField = new JTextField();
		myNewButton = new JButton("Erzeugen");
		myCancelButton = new JButton("Abbrechen");
		
		myAttributedCheckBox.setToolTipText("Gibt an, ob die Knoten des zu erzeugenden Graphen attributiert sein sollen");
		myDirectedCheckBox.setToolTipText("Gibt an, ob der zu erzeugende Graph gerichtet sein soll");
		myWeightedCheckBox.setToolTipText("Gibt an, ob der zu erzeugende Graph gewichtet sein soll");
	}
	
	@Override
	protected void initLayout(JPanel content) {
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		myGeneratorFactories.setMaximumSize(new Dimension(Integer.MAX_VALUE, myNewButton.getPreferredSize().height));
		myParameterLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, myParameterLabel.getPreferredSize().height));
		myParameterField.setMaximumSize(new Dimension(Integer.MAX_VALUE, myNewButton.getPreferredSize().height));
		
		myParameterLabel.setAlignmentX(SwingConstants.LEFT);
		
		JPanel graphPanel = new JPanel();
		graphPanel.setBorder(BorderFactory.createTitledBorder("Graph Eigenschaften"));
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.LINE_AXIS));
		graphPanel.setAlignmentX(SwingConstants.LEFT);
		graphPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, graphPanel.getMaximumSize().height));
		graphPanel.add(myAttributedCheckBox);
		graphPanel.add(myDirectedCheckBox);
		graphPanel.add(myWeightedCheckBox);
		
		JPanel factoryPanel = new JPanel();
		factoryPanel.setLayout(new BoxLayout(factoryPanel, BoxLayout.LINE_AXIS));
		factoryPanel.add(new JLabel("Generator: "));
		factoryPanel.add(myGeneratorFactories);
		
		JPanel generatePanel = new JPanel();
		generatePanel.setBorder(BorderFactory.createTitledBorder("Graph Generieren"));
		generatePanel.setLayout(new BoxLayout(generatePanel, BoxLayout.PAGE_AXIS));
		generatePanel.add(factoryPanel);
		generatePanel.add(Box.createVerticalStrut(5));
		generatePanel.add(myParameterLabel);
		generatePanel.add(myParameterField);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, myNewButton.getMaximumSize().height));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(myNewButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(myCancelButton);
		
		content.add(graphPanel);
		content.add(Box.createVerticalStrut(11));
		content.add(generatePanel);
		content.add(Box.createVerticalGlue());
		content.add(buttonPanel);
		myFrame.setSize(600, 450);
		myFrame.setLocationRelativeTo(null);
	}
	
	@Override
	protected void initListeners() {
		myGeneratorFactories.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> factory = getSelectedGeneratorFactory();
				updateParameterLabel(factory);
				myListener.onGeneratorSelected(factory);
			}
		});
		myNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> generator = getSelectedGeneratorFactory();
				try {
					Integer[] parameters = getParameters();
					myListener.onGenerateGraph(myAttributedCheckBox.isSelected(), myDirectedCheckBox.isSelected(), myWeightedCheckBox.isSelected(), generator, parameters);
				} catch(NumberFormatException ex) {
					showFehlermeldung("Bitte geben Sie nur Zahlen an.");
				}
			}
		});
		myCancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myListener.onCancel();
			}
		});
	}
	
	private Integer[] getParameters() {
		String[] paramStr = myParameterField.getText().split(",");
		Integer[] parameters = new Integer[paramStr.length];
		for(int i=0; i<paramStr.length; i++) {
			parameters[i] = Integer.parseInt(paramStr[i].trim());
		}
		return parameters;
	}
	
	@SuppressWarnings("unchecked")
	private GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> getSelectedGeneratorFactory() {
		return (GraphGeneratorFactory<Knoten, DefaultEdge, Knoten>)myGeneratorFactories.getSelectedItem();
	}
	
	private void updateParameterLabel(GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> factory) {
		String text = "";
		for(String param : factory.getParameterNames()) {
			text += "<" + param + ">,";
		}
		text = text.substring(0, text.length()-1);
		myParameterLabel.setText(text);
	}

	@Override
	public void fillViewWithModel(Void model) {
		
	}
	
	@Override
	public void setGraphGenerators(List<GraphGeneratorFactory<Knoten, DefaultEdge, Knoten>> generators) {
		DefaultComboBoxModel<GraphGeneratorFactory<Knoten, DefaultEdge, Knoten>> model = new DefaultComboBoxModel<GraphGeneratorFactory<Knoten,DefaultEdge,Knoten>>();
		for(GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> factory : generators) {
			model.addElement(factory);
		}
		myGeneratorFactories.setModel(model);
		updateParameterLabel(getSelectedGeneratorFactory());
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
