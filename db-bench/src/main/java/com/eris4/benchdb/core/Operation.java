package com.eris4.benchdb.core;

public abstract class Operation {
	
	private int repetition = 1;
		
	public void setRepetition(int repetition) {
		this.repetition = repetition;
	}
	
	public void execute() {
		for (int i = 0; i < repetition; i++) {
			doOperation();			
		}		
	}

	
	public abstract void setDatabase(Database database) throws NoSuitableDriverException;
	public abstract void setUp() throws TestDriverException;
	public abstract void doOperation();
	public abstract void tearDown();
	public abstract void warmUp();

}
