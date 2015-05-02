package org.haw.lnielsen.gka.graphen.ui.generator;

import java.util.List;

import org.haw.lnielsen.gka.graphen.Knoten;
import org.haw.lnielsen.gka.graphen.ui.generator.factory.GraphGeneratorFactory;
import org.jgrapht.graph.DefaultEdge;

import de.xancake.ui.mvc.window.WindowView_I;

/**
 * Schnittstelle für das Fenster des Graph-Generators.
 * 
 * @author Lars Nielsen
 */
public interface GraphGeneratorView_I extends WindowView_I<Void, GraphGeneratorViewListener_I> {
	/**
	 * Legt fest, welche GraphGeneratoren auf der Oberfläche angeboten werden sollen.
	 * @param generators Eine Liste der Generatoren
	 */
	void setGraphGenerators(List<GraphGeneratorFactory<Knoten, DefaultEdge, Knoten>> generators);
	
	/**
	 * Zeigt die Parameter an, die für einen Generator gebraucht werden.
	 * @param parameters Die Parameter
	 */
	void setGeneratorParameters(String[] parameters);
	
	/**
	 * Ermöglicht dem Benutzer das Konfigurieren von Attributen.
	 * @param enable {@code true}, wenn das Konfigurieren erlaubt werden soll, ansonsten {@code false}
	 */
	void enableAttributeConfiguration(boolean enable);
	
	/**
	 * Ermöglicht dem Benutzer das Konfigurieren von Gewichten.
	 * @param enable {@code true}, wenn das Konfigurieren erlaubt werden soll, ansonsten {@code false}
	 */
	void enableWeightConfiguration(boolean enable);
	
	/**
	 * Gibt den ausgewählten Minimalwert für die Attributierung von Knoten zurück.
	 * @return Der Minimalwert für die Attributierung von Knoten
	 */
	int getAttributeMinValue();
	
	/**
	 * Gibt den ausgewählten Maximalwert für die Attributierung von Knoten zurück.
	 * @return Der Maximalwert für die Attributierung von Knoten
	 */
	int getAttributeMaxValue();
	
	/**
	 * Gibt den Gewichtsmodifikator zurück.
	 * @return Der Gewichtsmodifikator
	 */
	int getWeightModifier();
	
	/**
	 * Zeigt die übergebene Nachricht als Fehlermeldung an.
	 * @param message Die Nachricht
	 */
	void showFehlermeldung(String message);
	
	/**
	 * Zeigt die übergebene Exception an. Dabei kann festgelegt werden, ob der
	 * gesamte Stacktrace ausgegeben werden soll, oder nur die kaskadierenden Nachrichten.
	 * @param exception Die Exception
	 * @param showTrace {@code true}, wenn der Stacktrace angezeigt werden soll, ansonsten {@code false}
	 */
	void showFehlermeldung(Throwable exception, boolean showTrace);
}
