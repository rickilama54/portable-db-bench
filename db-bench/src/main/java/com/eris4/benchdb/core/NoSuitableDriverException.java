package com.eris4.benchdb.core;

public class NoSuitableDriverException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6795108606033642243L;

	@SuppressWarnings("unchecked")
	public NoSuitableDriverException(Class testDriver) {
		super(testDriver.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	public NoSuitableDriverException(Class testDriver, Exception e) {
		super(testDriver.getSimpleName(),e);
	}

}
