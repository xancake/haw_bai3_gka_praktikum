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
	public void testParseGraph_DirectedTypo() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed typo.graph"));
	}
	
	/**
	 * Attributiert/Gewichtet: wird kein Attribut zugeordnet
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedAttributedWeightedFail() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed attributed weighted fail.graph"));
	}
	
	/**
	 * Attributiert/Gewichtet: wird kein Gewicht zugeordnet
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedAttributedWeightedFail2() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed attributed weighted fail 2.graph"));
	}
	
	/**
	 * Gewichtet: Attribut statt Gewicht angehängt
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedWeightedFail() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed weighted fail.graph"));
	}
	
	/**
	 * Attributiert: Statt Attribut Gewicht hinzugefügt
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_DirectedAttributedFail() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/directed attributed fail.graph"));
	}
	
	//Ungerichtete Graphen
	
	/**
	 * Attributiert/Gewichtet: wird bei einer Kante kein Gewicht zugeordnet
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedAttributedWeightedFail() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected attributed weighted fail.graph"));
	}
	
	/**
	 * Attributiert/Gewichtet: bei einem Knoten kein Attribut
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedAttributedWeightedFail2() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected attributed weighted fail2.graph"));
	}
	
	/**
	 * Nicht Attributiert/ Nicht Gewichtet: Gewicht wird trotzdem zugeordnet
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedFailWeighted() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected fail weighted.graph"));
	}
	
	/**
	 * Nicht Attributiert/Nicht Gewichtet: Attribut wird trotzdem an einem Knoten eingefügt
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedFailAttributed() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected fail attributed.graph"));
	}
	
	/**
	 * Attributiert/Gewichtet: Statt zwei Doppelpunkten, wurde nur einer gemacht beim Kantengewicht
	 */
	@Test(expected=GraphParseException.class)
	public void testParseGraph_UndirectedAttributedWeightedTypo() throws Exception {
		myGraphParser.parseGraph(ClassLoader.getSystemResourceAsStream("loader/negative files/undirected attributed weighted typo.graph"));
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
}
