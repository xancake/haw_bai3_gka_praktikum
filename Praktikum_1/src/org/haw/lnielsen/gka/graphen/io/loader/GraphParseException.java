package org.haw.lnielsen.gka.graphen.io.loader;

/**
 * Exception-Typ f�r Fehler, die beim Parsen eines Graphen auftreten.
 * 
 * @author Lars Nielsen
 */
public class GraphParseException extends Exception {
	private static final long serialVersionUID = 4953698069943421947L;
	
	/**
	 * Initialisiert diese Exception mit einer Fehlernachricht.
	 * @param message Die Fehlernachricht
	 */
	public GraphParseException(String message) {
		super(message);
	}
	
	/**
	 * Initialisiert diese Exception mit einer Fehlernachricht und einem Ausl�ser.
	 * @param message Die Fehlernachricht
	 * @param cause Der Ausl�ser
	 */
	public GraphParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
