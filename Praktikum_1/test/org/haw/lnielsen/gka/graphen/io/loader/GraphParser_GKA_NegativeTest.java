package org.haw.lnielsen.gka.graphen.io.loader;

import org.junit.Before;
import org.junit.Test;

/**
 * Testklasse mit Negativtests zum {@link GraphParser_GKA}.
 * 
 * @author Jennifer Momsen
 */
public class GraphParser_GKA_NegativeTest {
	private GraphParser_I myGraphParser;
		
	@Before
	public void setUp() {
		myGraphParser = new GraphParser_GKA();
	}
	
	//Gerichtete Graphen:
	
	/**
	 * Kein Gewicht/kein Attribut: zwei Kommata statt einem zwischen Knoten
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedDoubleCommas() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed double commas.graph"));
	}
	
	/**
	 * Attributiert/Gewichtet: wird kein Gewicht zugeordnet
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedWeightedWithoutWeight() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed attributed without weight.graph"));
	}
	
	/**
	 * Attributiert/Gewichtet: wird kein Attribut zugeordnet
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedWeightedWithoutAttribut() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed weighted without attribut.graph"));
	}
	
	/**
	 * Gewichtet: Attribut statt Gewicht angehängt
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedWeightedWithAttributed() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed weighted with attribut.graph"));
	}
	
	/**
	 * Attributiert: Statt Attribut Gewicht hinzugefügt
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedAttributedWithWeight() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed attributed with weight.graph"));
	}
	
	//Ungerichtete Graphen
	
	/**
	 * Attributiert/Gewichtet: wird bei einer Kante kein Gewicht zugeordnet
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedAttributedWeightedWithoutWeight() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected attributed weighted without weight.graph"));
	}
	
	/**
	 * Attributiert/Gewichtet: bei einem Knoten kein Attribut
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedAttributedWeightedWithoutAttribut() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected attributed weighted without attribut.graph"));
	}
	
	/**
	 * Nicht Attributiert/ Nicht Gewichtet: Gewicht wird trotzdem zugeordnet
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedWithWeight() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected with weight.graph"));
	}
	
	/**
	 * Nicht Attributiert/Nicht Gewichtet: Attribut wird trotzdem an einem Knoten eingefügt
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedWithAttribut() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected with attribut.graph"));
	}
	
	/**
	 * Attributiert/Gewichtet: Statt zwei Doppelpunkten, wurde nur einer gemacht beim Kantengewicht
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedColonTypo() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected colon typo.graph"));
	}
	
	/**
	 * Schreibfehler in der Headerzeile bei Attributed
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_AttributedHeaderTypo() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/attributed header typo.graph"));
	}
	
	/**
	 * Schreibfehler in der Headerzeile bei Weighted
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_WeightedHeaderTypo() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/weighted header typo.graph"));
	}
	
	/**
	 * Schreibfehler in der Headerzeile bei Directed
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedHeaderTypo() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed header typo.graph"));
	}
	
	/**
	 * Leere .graph Datei
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_EmptyFile() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/empty file.graph"));
	}
	
	/**
	 * Mehr als eine Headerzeile
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_TwoHeaderlines() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/two headerlines.graph"));
	}
}
