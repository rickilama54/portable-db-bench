package com.eris4.benchdb.test.person.domain;

import com.eris4.benchdb.core.TestDriverException;

public interface PersonDriver {
	
	public void connect() throws TestDriverException;
	
	public void init(int numberOfObject) throws TestDriverException;
	//getNumberOfObjects if you want to generalize this 4 methods in a base interface
	public int getNumberOfPerson() throws TestDriverException;
	
	public void close() throws TestDriverException;
	
	
	
	
	public Person read(long personId) throws TestDriverException;
	
	public void write(Person person) throws TestDriverException;

	

	
	
	

}
