package com.eris4.benchdb.core.reporter;

public abstract class Reporter {
	
	private boolean stop = true;

	public void notifyStart() {
		if (stop){
			stop = false;
			start();
		}
	}
	
	public void notifyStop() {
		if (!stop){
			stop = true;
			stop();
		}
	}

	public abstract void stop();

	public abstract void start();	
	
	public abstract void report();

}
