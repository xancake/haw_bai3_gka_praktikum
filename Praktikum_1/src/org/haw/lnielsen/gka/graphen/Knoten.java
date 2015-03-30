package org.haw.lnielsen.gka.graphen;

public class Knoten {
	private String myName;
	private int myAttribut;
	private boolean bAttributiert;
	
	public Knoten(String name) {
		this(name, 0, false);
	}
	
	public Knoten(String name, int attribut) {
		this(name, attribut, true);
	}
	
	private Knoten(String name, int attribut, boolean attributiert) {
		myName = name;
		myAttribut = attribut;
		bAttributiert = attributiert;
	}
	
	public String getName() {
		return myName;
	}
	
	public int getAttribut() {
		return myAttribut;
	}
	
	public boolean isAttributiert() {
		return bAttributiert;
	}
	
	@Override
	public String toString() {
		return getName() + (isAttributiert() ? " (" + getAttribut() + ")" : "");
	}
}
