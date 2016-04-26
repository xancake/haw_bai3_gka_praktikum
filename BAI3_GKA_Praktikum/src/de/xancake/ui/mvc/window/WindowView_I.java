package de.xancake.ui.mvc.window;

import de.xancake.ui.mvc.View_I;

public interface WindowView_I<M, L extends WindowViewListener_I> extends View_I<M, L> {
	/**
	 * Gibt zurück, ob die View gerade sichtbar ist.
	 * @return {@code true}, wenn die View sichtbar ist, ansonsten {@code false}
	 */
	boolean isVisible();
	
	/**
	 * Steuert die Sichtbarkeit dieser View.
	 * @param visible {@code true}, wenn die View sichtbar sein soll, ansonsten {@code false}
	 */
	void setVisible(boolean visible);
}
