package de.xancake.ui.mvc;

public interface View_I<M, L extends ViewListener_I> {
	/**
	 * Gibt den {@link ViewListener_I} zurück, der aktuell auf diese View lauscht.
	 * @return Der aktuell auf diese View lauschende {@link ViewListener_I}
	 */
	L getViewListener();
	
	/**
	 * Registriert den übergebenen {@link ViewListener_I}, auf dieser View.
	 * @param l Der zu registrierende {@link ViewListener_I}
	 */
	void setViewListener(L l);
	
	/**
	 * Füllt diese View mit den Daten aus dem Model.
	 * @param model Das Model
	 */
	void fillViewWithModel(M model);
}
