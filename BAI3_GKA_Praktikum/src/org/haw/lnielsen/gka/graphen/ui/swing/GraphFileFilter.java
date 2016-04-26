package org.haw.lnielsen.gka.graphen.ui.swing;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Ein {@link FileFilter} für .graph-Dateien zur Verwendung in einem {@link JFileChooser}.
 * 
 * @author Lars Nielsen
 */
public class GraphFileFilter extends FileFilter {
	private static final String FILE_EXTENSION = ".graph";
	
	/**
	 * Gibt den Anzeigetext des Filters zurück.
	 * @return der Anzeigetext des Filters
	 */
	@Override
	public String getDescription() {
		return "Graph files (" + FILE_EXTENSION + ")";
	}
	
	/**
	 * Prüft, ob die übergebene Datei vom FileChooser angezeigt werden soll.
	 * Verzeichnisse müssen immer angezeigt werden, da die Navigation durch das Dateisystem
	 * sonst nicht möglich ist.
	 * @param f Die Datei, die geprüft werden soll.
	 * @return {@code wahr}, wenn die Datei angezeigt werden soll, ansonsten {@code false}
	 */
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || f.getName().endsWith(FILE_EXTENSION);
	}
}
