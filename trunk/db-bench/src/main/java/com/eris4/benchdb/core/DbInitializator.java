package com.eris4.benchdb.core;

public abstract class DbInitializator {
	
	private int numberOfObjects;

	public void setNumberOfObjects(int numberOfObjects) {
		this.numberOfObjects = numberOfObjects;
	}

	public void init(Database database) throws NoSuitableDriverException, TestDriverException {
		init(database,numberOfObjects);
	}

	public abstract void init(Database database, int numberOfObjects) throws NoSuitableDriverException, TestDriverException;

}
