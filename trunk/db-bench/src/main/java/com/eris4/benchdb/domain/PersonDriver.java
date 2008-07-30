package com.eris4.benchdb.domain;

import com.eris4.benchdb.core.TestDriverException;

public interface PersonDriver {
	
	public void connect() throws TestDriverException;
	
	public void init(int numberOfObject) throws TestDriverException;
	
	public int getNumberOfPerson() throws TestDriverException;
	
	public void close() throws TestDriverException;
	
	public Person read(long personId) throws TestDriverException;
	
	public void write(Person person) throws TestDriverException;

	

	
	
	

}
