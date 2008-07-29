package com.eris4.benchdb.domain;

import com.eris4.benchdb.core.TestDriverException;

public interface PersonDriver {
	
	public void connect() throws TestDriverException;
	
	public void init(int numberOfObject) throws TestDriverException;
	
	public int getNumberOfPerson();
	
	public void close();
	
	public Person read(long personId);
	
	public void write(Person person);

	

	
	
	

}
