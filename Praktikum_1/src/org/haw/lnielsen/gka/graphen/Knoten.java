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
