package de.xancake.ui.mvc.window;

import de.xancake.ui.mvc.ViewListener_I;

public interface WindowViewListener_I extends ViewListener_I {
	/**
	 * Wird von der View aufgerufen, wenn sie angezeigt wird.
	 */
	void onViewOpened();
	
	/**
	 * Wird von der View aufgerufen, wenn sie geschlossen wird.
	 */
	void onViewClosed();
}
