package de.xancake.ui.mvc.window;

import de.xancake.ui.mvc.ControllerListener_I;
import de.xancake.ui.mvc.Controller_A;

public abstract class WindowController_A<M, L extends WindowViewListener_I, V extends WindowView_I<M, L>, T extends ControllerListener_I> extends Controller_A<M, L, V, T> {
	public WindowController_A(M model, V view) {
		super(model, view);
	}
	
	/**
	 * Zeigt die View an.
	 */
	public void show() {
		getView().setVisible(true);
	}
	
	/**
	 * Versteckt die View.
	 */
	public void hide() {
		getView().setVisible(false);
	}
}
