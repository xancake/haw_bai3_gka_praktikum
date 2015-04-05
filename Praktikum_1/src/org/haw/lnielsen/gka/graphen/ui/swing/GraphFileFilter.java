package org.haw.lnielsen.gka.graphen.ui.swing;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class GraphFileFilter extends FileFilter {
	private static final String FILE_EXTENSION = ".graph";
	
	@Override
	public String getDescription() {
		return "Graph files (" + FILE_EXTENSION + ")";
	}
	
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().endsWith(FILE_EXTENSION);
	}
}
