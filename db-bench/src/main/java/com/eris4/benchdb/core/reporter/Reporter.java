package com.eris4.benchdb.core.reporter;

import com.eris4.benchdb.core.Database;

public abstract class Reporter {
	
	private boolean stop = true;
	private Database database;

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

	public void setDatabase(Database database) {
		this.database = database;		
	}
	
	public Database getDatabase() {
		return database;
	}

}
