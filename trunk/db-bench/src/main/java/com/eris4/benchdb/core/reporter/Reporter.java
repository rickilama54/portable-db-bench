package com.eris4.benchdb.core.reporter;

import com.eris4.benchdb.core.Database;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Section;

public abstract class Reporter {
	
	private Database database;

	public abstract void stop();

	public abstract void start();	
	
	public abstract void report(Section section) throws DocumentException;

	public void setDatabase(Database database) {
		this.database = database;		
	}
	
	public Database getDatabase() {
		return database;
	}

	public abstract void addDescription(String description);

	public abstract String getName();
	
	public abstract void setName(String name);


}
