package org.haw.lnielsen.gka.graphen.ui.editor;

import java.io.File;
import org.haw.lnielsen.gka.graphen.Knoten;
import de.xancake.ui.mvc.window.WindowViewListener_I;

public interface GraphEditorWindowListener_I extends WindowViewListener_I {
	void onNewGraph();
	void onLoadGraph(File file);
	void onStoreGraph(File file);
	void onCalculateShortestPath(Knoten start, Knoten end);
}
