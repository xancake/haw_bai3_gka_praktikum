package org.haw.lnielsen.gka.graphen;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.haw.lnielsen.gka.graphen.ui.editor.GraphEditorWindowController;

public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
		new GraphEditorWindowController().show();
	}
}
