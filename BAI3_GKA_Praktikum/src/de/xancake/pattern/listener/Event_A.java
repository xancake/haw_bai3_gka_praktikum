package de.xancake.pattern.listener;

public abstract class Event_A<L extends Listener_I> {
	public abstract void fireEvent(L l);
}
