package com.eris4.benchdb.core.reporter;

import com.eris4.benchdb.core.Database;

public abstract class Reporter {
	
	private Database database;

	public abstract void stop();

	public abstract void start();	
	
	public abstract void report();

	public void setDatabase(Database database) {
		this.database = database;		
	}
	
	public Database getDatabase() {
		return database;
	}

	public abstract void addDescription(String description);

}
