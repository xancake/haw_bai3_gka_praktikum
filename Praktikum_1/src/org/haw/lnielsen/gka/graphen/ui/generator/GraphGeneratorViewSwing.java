package org.haw.lnielsen.gka.graphen.ui.generator;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.GraphGeneratorFactory;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.window.SwingWindowView_A;

/**
 * Die Swing-Implementation der Graph-Generator-View.
 * 
 * @author Lars Nielsen
 */
public class GraphGeneratorViewSwing
		extends SwingWindowView_A<Void, GraphGeneratorViewListener_I>
		implements GraphGeneratorView_I {
	private JCheckBox myAttributedCheckBox;
	private JCheckBox myDirectedCheckBox;
	private JCheckBox myWeightedCheckBox;
	
	private JPanel myAttributePanel;
	private JSpinner myAttributeMinValue;
	private JSpinner myAttributeMaxValue;
	
	private JPanel myWeightPanel;
	
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
		
		myAttributePanel = new JPanel();
		myAttributeMinValue = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		myAttributeMaxValue = new JSpinner(new SpinnerNumberModel(100, 1, Integer.MAX_VALUE, 1));
		
		myWeightPanel = new JPanel();
		
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
		myParameterLabel.setForeground(Color.RED);
		
		myAttributeMinValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, myNewButton.getPreferredSize().height));
		myAttributeMaxValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, myNewButton.getPreferredSize().height));
		myGeneratorFactories.setMaximumSize(new Dimension(Integer.MAX_VALUE, myNewButton.getPreferredSize().height));
		myParameterLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, myParameterLabel.getPreferredSize().height));
		myParameterField.setMaximumSize(new Dimension(Integer.MAX_VALUE, myNewButton.getPreferredSize().height));
		
		myParameterLabel.setAlignmentX(SwingConstants.LEFT);
		
		myAttributePanel.setBorder(BorderFactory.createTitledBorder("Attributierung"));
		myAttributePanel.setLayout(new BoxLayout(myAttributePanel, BoxLayout.PAGE_AXIS));
		myAttributePanel.add(labelComponent(myAttributeMinValue, "Minimum: "));
		myAttributePanel.add(Box.createVerticalStrut(5));
		myAttributePanel.add(labelComponent(myAttributeMaxValue, "Maximum: "));
		myAttributePanel.add(Box.createHorizontalGlue());
		
		myWeightPanel.setBorder(BorderFactory.createTitledBorder("Gewichtung"));
		myWeightPanel.setLayout(new BoxLayout(myWeightPanel, BoxLayout.PAGE_AXIS));
		
		myWeightPanel.add(Box.createHorizontalGlue());
		
		JPanel graphPanel = new JPanel();
		graphPanel.setBorder(BorderFactory.createTitledBorder("Graph Eigenschaften"));
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.LINE_AXIS));
		graphPanel.add(myAttributedCheckBox);
		graphPanel.add(myDirectedCheckBox);
		graphPanel.add(myWeightedCheckBox);
		graphPanel.add(Box.createHorizontalGlue());
		
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.LINE_AXIS));
		propertiesPanel.add(myAttributePanel);
//		propertiesPanel.add(Box.createRigidArea(new Dimension(5, 5)));
//		propertiesPanel.add(myWeightPanel);
		
		JPanel generatePanel = new JPanel();
		generatePanel.setBorder(BorderFactory.createTitledBorder("Generator"));
		generatePanel.setLayout(new BoxLayout(generatePanel, BoxLayout.PAGE_AXIS));
		generatePanel.add(labelComponent(myGeneratorFactories, "Generator: "));
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
		
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		content.add(graphPanel);
		content.add(Box.createVerticalStrut(5));
		content.add(propertiesPanel);
		content.add(Box.createVerticalStrut(5));
		content.add(generatePanel);
		content.add(Box.createVerticalStrut(11));
		content.add(Box.createVerticalGlue());
		content.add(buttonPanel);
		
		myFrame.setSize(450, 300);
		myFrame.setLocationRelativeTo(null);
	}
	
	private static JPanel labelComponent(Component component, String label) {
		JLabel jlabel = new JLabel(label);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(jlabel);
		panel.add(component);
		return panel;
	}
	
	@Override
	protected void initListeners() {
		myAttributedCheckBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				myListener.onAttributedSelected(myAttributedCheckBox.isSelected());
			}
		});
		myDirectedCheckBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				myListener.onDirectedSelected(myDirectedCheckBox.isSelected());
			}
		});
		myWeightedCheckBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				myListener.onWeightedSelected(myWeightedCheckBox.isSelected());
			}
		});
		myGeneratorFactories.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myListener.onGeneratorSelected(getSelectedGeneratorFactory());
			}
		});
		myNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GraphGeneratorFactory<Knoten, DefaultEdge, Knoten> generator = getSelectedGeneratorFactory();
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
		setGeneratorParameters(getSelectedGeneratorFactory().getParameterNames());
	}
	
	@Override
	public void setGeneratorParameters(String[] parameters) {
		String text = "";
		for(String param : parameters) {
			text += "<" + param + ">,";
		}
		text = text.substring(0, text.length()-1);
		myParameterLabel.setText(text);
	}
	
	@Override
	public void enableAttributeConfiguration(boolean enable) {
		myAttributeMinValue.setEnabled(enable);
		myAttributeMaxValue.setEnabled(enable);
	}
	
	@Override
	public void enableWeightConfiguration(boolean enable) {
		myWeightPanel.setEnabled(enable);
	}
	
	@Override
	public int getAttributeMinValue() {
		return (Integer)myAttributeMinValue.getValue();
	}
	
	@Override
	public int getAttributeMaxValue() {
		return (Integer)myAttributeMaxValue.getValue();
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
