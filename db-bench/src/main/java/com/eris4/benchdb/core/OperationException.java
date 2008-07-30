package com.eris4.benchdb.core;

public class OperationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8636093532113232504L;

	public OperationException(Exception e) {
		super(e);
	}

	public OperationException(String message) {
		super(message);
	}

}
