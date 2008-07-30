package com.eris4.benchdb.core;

public abstract class Operation {
	
	private int repetition = 1;
		
	public void setRepetition(int repetition) {
		this.repetition = repetition;
	}
	
	public void execute() throws OperationException, TestDriverException {
		for (int i = 0; i < repetition; i++) {
			doOperation();			
		}		
	}

	
	public abstract void setDatabase(Database database) throws NoSuitableDriverException;
	public abstract void setUp() throws OperationException, TestDriverException;
	public abstract void doOperation() throws OperationException, TestDriverException;
	public abstract void tearDown() throws OperationException, TestDriverException;
	public abstract void warmUp() throws OperationException, TestDriverException;

}
