package org.haw.lnielsen.gka.graphen.ui.swing;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Ein {@link FileFilter} f�r .graph-Dateien zur Verwendung in einem {@link JFileChooser}.
 * 
 * @author Lars Nielsen
 */
public class GraphFileFilter extends FileFilter {
	private static final String FILE_EXTENSION = ".graph";
	
	/**
	 * Gibt den Anzeigetext des Filters zur�ck.
	 * @return der Anzeigetext des Filters
	 */
	@Override
	public String getDescription() {
		return "Graph files (" + FILE_EXTENSION + ")";
	}
	
	/**
	 * Pr�ft, ob die �bergebene Datei vom FileChooser angezeigt werden soll.
	 * Verzeichnisse m�ssen immer angezeigt werden, da die Navigation durch das Dateisystem
	 * nicht m�glich ist.
	 * @param f Die Datei, die gepr�ft werden soll.
	 * @return {@code wahr}, wenn die Datei angezeigt werden soll, ansonsten {@code false}
	 */
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().endsWith(FILE_EXTENSION);
	}
}
