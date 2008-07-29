package com.eris4.benchdb.operation;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.util.StringGenerator;
import com.eris4.benchdb.test.person.Person;
import com.eris4.benchdb.test.person.PersonDriver;
import com.eris4.benchdb.test.person.PersonImpl;

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
	public void warmUp() {
		for (int i = 0; i < 10; i++) {
			doOperation();			
		}
	}
	
	@Override
	public void doOperation() {
		Person person = newRandomPerson();
		person.setId(id + numberOfObject);		
		try {
			personDriver.write(person);
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println(person.getId()+" - "+person.getName());
			throw new RuntimeException();
		}
		id ++;
	}	

	@Override
	public void tearDown() {
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
