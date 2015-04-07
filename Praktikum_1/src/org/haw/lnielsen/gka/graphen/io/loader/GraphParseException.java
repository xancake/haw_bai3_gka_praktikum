package org.haw.lnielsen.gka.graphen.io.loader;

public class GraphParseException extends Exception {
	private static final long serialVersionUID = 4953698069943421947L;
	
	public GraphParseException(String message) {
		super(message);
	}
	
	public GraphParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
