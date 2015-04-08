package org.haw.lnielsen.gka.graphen;

/**
 * Modellklasse für Knoten von Graphen. Ein Knoten kann einen Namen sowie ein ganzzahliges Attribut besitzen.
 * Knoten sind immutabel damit es keine Komplikationen mit auf Hashes arbeitenden Datenstrukturen gibt.
 * 
 * @author Lars Nielsen
 */
public class Knoten {
	private String myName;
	private int myAttribut;
	private boolean bAttributiert;
	private int myWert;
	
	/**
	 * Initialisiert einen nicht attributierten Knoten mit einem Namen. 
	 * @param name Der Name des Knotens
	 */
	public Knoten(String name) {
		this(name, 0, false);
	}
	
	/**
	 * Initialisiert einen attributierten Knoten mit einem Namen und dem Attribut.
	 * @param name Der Name des Knotens
	 * @param attribut Das Attribut des Knotens
	 */
	public Knoten(String name, int attribut) {
		this(name, attribut, true);
	}
	
	private Knoten(String name, int attribut, boolean attributiert) {
		myName = name;
		myAttribut = attribut;
		bAttributiert = attributiert;
	}
	
	/**
	 * Gibt den Namen des Knotens zurück.
	 * @return Der Name
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * Gibt das Attribut des Knotens zurück.
	 * @return Das Attribut
	 */
	public int getAttribut() {
		return myAttribut;
	}
	
	/**
	 * Gibt zurück, ob der Knoten attributiert ist.
	 * @return {@code true}, wenn der Knoten attributiert ist, ansonsten {@code false}
	 */
	public boolean isAttributiert() {
		return bAttributiert;
	}
	
	@Override
	public String toString() {
		return getName() + (isAttributiert() ? " (" + getAttribut() + ")" : "");
	}
	
	/**
	 * Zeichnet den Knoten mit einem Wert aus, den man für die BFS braucht
	 * @param der ausgezeichnete Wert
	 */
	public void setzeWert(int wert){
		myWert = wert;
	}
	
	/**
	 * Gibt den ausgezeichneten Wert zurück
	 * return der Ausgezeichnete Wert
	 */
	public int getWert(){
		return myWert;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bAttributiert ? 1231 : 1237);
		result = prime * result + myAttribut;
		result = prime * result + ((myName == null) ? 0 : myName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Knoten other = (Knoten)obj;
		if(bAttributiert != other.bAttributiert)
			return false;
		if(myAttribut != other.myAttribut)
			return false;
		if(myName == null) {
			if(other.myName != null)
				return false;
		} else if(!myName.equals(other.myName))
			return false;
		return true;
	}
}
