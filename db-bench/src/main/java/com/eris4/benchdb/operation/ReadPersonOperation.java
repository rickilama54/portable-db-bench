package com.eris4.benchdb.operation;

import java.util.Random;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.core.util.StringGenerator;
import com.eris4.benchdb.domain.Person;
import com.eris4.benchdb.domain.PersonDriver;
import com.eris4.benchdb.domain.PersonImpl;

public class ReadPersonOperation extends Operation {

	private StringGenerator stringGenerator = new StringGenerator();
	private PersonDriver personDriver;
	private int numberOfObject;
	private Random random = new Random();;
	
	@Override
	public void setDatabase(Database database) throws NoSuitableDriverException{
		personDriver = (PersonDriver) database.getSpecificDriver(PersonDriver.class);
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
		Person person = personDriver.read(random.nextInt(numberOfObject));
		if (person == null){
//			System.err.println("ERROR: missing person!!");
		}
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
