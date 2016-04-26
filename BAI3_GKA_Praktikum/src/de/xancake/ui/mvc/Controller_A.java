package de.xancake.ui.mvc;

import java.util.List;
import de.xancake.pattern.listener.EventExecutor;
import de.xancake.pattern.listener.Event_A;

public abstract class Controller_A<M, L extends ViewListener_I, V extends View_I<M, L>, T extends ControllerListener_I> {
	private EventExecutor<T> myEventExecutor;
	private M myModel;
	private V myView;
	
	public Controller_A(M model, V view) {
		myEventExecutor = new EventExecutor<>();
		setModel(model);
		setView(view);
	}
	
	/**
	 * Gibt das von diesem Controller verwaltete Model zurück.
	 * @return Das Model
	 */
	public final M getModel() {
		return myModel;
	}
	
	/**
	 * Registriert das übergebene Model an diesem Controller.
	 * Sollte zu diesem Controller schon eine {@link View_I} registriert sein, wird die {@link View_I} mit dem Model befüllt.
	 * @param model Das zu verwaltende Model
	 * @see View_I#fillViewWithModel(Object)
	 */
	public final void setModel(M model) {
		myModel = model;
		update();
	}
	
	/**
	 * Gibt die von diesem Controller verwaltete {@link View_I} zurück.
	 * @return Die {@link View_I}
	 */
	public final V getView() {
		return myView;
	}
	
	/**
	 * Registriert die übergebene {@link View_I} an diesem Controller.
	 * Dabei registriert dieser Controller wiederum einen {@link ViewListener_I} auf der {@link View_I}
	 * und unregestriert sich an einer gebenenfalls vorher registrierten {@link View_I}.
	 * Sollte zu diesem Controller schon ein Model registriert sein, wird die {@link View_I} mit dem Model befüllt.
	 * @param view Die zu verwaltende {@link View_I}
	 * @see #getViewListener()
	 * @see View_I#fillViewWithModel(Object)
	 */
	public final void setView(V view) {
		unregisterOnView(myView);
		myView = view;
		registerOnView(myView);
		update();
	}
	
	private void unregisterOnView(V view) {
		if(view != null && view.getViewListener() == this) {
			view.setViewListener(null);
		}
	}
	
	private void registerOnView(V view) {
		if(view != null && view.getViewListener() != this) {
			view.setViewListener(getViewListener());
		}
	}
	
	/**
	 * Aktualisiert die Oberfläche mit den aktuellen Daten aus dem Model.
	 */
	protected void update() {
		if(myView != null) {
			myView.fillViewWithModel(myModel);
		}
	}
	
	/**
	 * Fügt dem Controller einen Listener hinzu.
	 */
	public void addControllerListener(T listener) {
		myEventExecutor.addListener(listener);
	}
	
	/**
	 * Entfernt einen Listener vom Controller.
	 */
	public void removeControllerListener(T listener) {
		myEventExecutor.removeListener(listener);
	}
	
	/**
	 * Gibt eine Liste aller Listener dieses Controllers zurück.
	 * @return Eine Liste der Listener dieses Controllers
	 */
	protected List<T> getListeners() {
		return myEventExecutor.getListeners();
	}
	
	/**
	 * Benachrichtigt alle Listener über das Auftreten des übergebenen {@link Event_A Events}.
	 * @param event Das Event, über das benachrichtigt werden soll
	 */
	protected void fireEvent(Event_A<T> event) {
		myEventExecutor.fireEvent(event);
	}
	
	/**
	 * Gibt den {@link ViewListener_I} zurück, der auf der {@link View_I} die von diesem Controller verwaltet werden soll registriert wird.
	 * Es muss gewährleistet werden, dass diese Methode immer den selben {@link ViewListener_I} zurückgibt.
	 * @return Der {@link ViewListener_I}
	 */
	protected abstract L getViewListener();
}
