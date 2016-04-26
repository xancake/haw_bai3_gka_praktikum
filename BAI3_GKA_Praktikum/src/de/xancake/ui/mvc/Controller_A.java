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
	 * Gibt das von diesem Controller verwaltete Model zur�ck.
	 * @return Das Model
	 */
	public final M getModel() {
		return myModel;
	}
	
	/**
	 * Registriert das �bergebene Model an diesem Controller.
	 * Sollte zu diesem Controller schon eine {@link View_I} registriert sein, wird die {@link View_I} mit dem Model bef�llt.
	 * @param model Das zu verwaltende Model
	 * @see View_I#fillViewWithModel(Object)
	 */
	public final void setModel(M model) {
		myModel = model;
		update();
	}
	
	/**
	 * Gibt die von diesem Controller verwaltete {@link View_I} zur�ck.
	 * @return Die {@link View_I}
	 */
	public final V getView() {
		return myView;
	}
	
	/**
	 * Registriert die �bergebene {@link View_I} an diesem Controller.
	 * Dabei registriert dieser Controller wiederum einen {@link ViewListener_I} auf der {@link View_I}
	 * und unregestriert sich an einer gebenenfalls vorher registrierten {@link View_I}.
	 * Sollte zu diesem Controller schon ein Model registriert sein, wird die {@link View_I} mit dem Model bef�llt.
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
	 * Aktualisiert die Oberfl�che mit den aktuellen Daten aus dem Model.
	 */
	protected void update() {
		if(myView != null) {
			myView.fillViewWithModel(myModel);
		}
	}
	
	/**
	 * F�gt dem Controller einen Listener hinzu.
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
	 * Gibt eine Liste aller Listener dieses Controllers zur�ck.
	 * @return Eine Liste der Listener dieses Controllers
	 */
	protected List<T> getListeners() {
		return myEventExecutor.getListeners();
	}
	
	/**
	 * Benachrichtigt alle Listener �ber das Auftreten des �bergebenen {@link Event_A Events}.
	 * @param event Das Event, �ber das benachrichtigt werden soll
	 */
	protected void fireEvent(Event_A<T> event) {
		myEventExecutor.fireEvent(event);
	}
	
	/**
	 * Gibt den {@link ViewListener_I} zur�ck, der auf der {@link View_I} die von diesem Controller verwaltet werden soll registriert wird.
	 * Es muss gew�hrleistet werden, dass diese Methode immer den selben {@link ViewListener_I} zur�ckgibt.
	 * @return Der {@link ViewListener_I}
	 */
	protected abstract L getViewListener();
}
