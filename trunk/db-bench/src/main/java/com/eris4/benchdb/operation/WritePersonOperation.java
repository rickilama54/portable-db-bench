package com.eris4.benchdb.operation;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.util.StringGenerator;
import com.eris4.benchdb.domain.Person;
import com.eris4.benchdb.domain.PersonDriver;
import com.eris4.benchdb.domain.PersonImpl;

public class WritePersonOperation extends Operation {

	private StringGenerator stringGenerator = new StringGenerator();
	private int id;
	private PersonDriver personDriver;
	private int numberOfObject;
	
	@Override
	public void setDatabase(Database database) throws NoSuitableDriverException{
		personDriver = (PersonDriver) database.getSpecificDriver(PersonDriver.class);
		id = 0;

	}
	
	@Override
	public void setUp() throws TestDriverException {
		personDriver.connect();
		numberOfObject = personDriver.getNumberOfPerson();		
	}
		
	@Override
	public void warmUp() throws TestDriverException {
		for (int i = 0; i < 10; i++) {
			doOperation();			
		}
	}
	
	@Override
	public void doOperation() throws TestDriverException {
		Person person = newRandomPerson();
		person.setId(id + numberOfObject);	
		personDriver.write(person);
		id ++;
	}	

	@Override
	public void tearDown() throws TestDriverException {	
		personDriver.close();			
	}
	
	
	/*
	 *  ###############  private methods here ############
	 */
	
	
	private Person newRandomPerson() {
		Person person = new PersonImpl();
		initPerson(person);
		return person;
	}
	
	private void initPerson(Person person) {
		person.setName(stringGenerator.getRandomString());		
	}

	


}
