package de.xancake.ui.mvc;

public abstract class View_A<M, L extends ViewListener_I> implements View_I<M, L> {
	protected L myListener;
	
	public View_A() {
		this(null);
	}
	
	public View_A(L l) {
		setViewListener(l);
	}
	
	@Override
	public L getViewListener() {
		return myListener;
	}
	
	@Override
	public void setViewListener(L l) {
		myListener = l;
	}
}
