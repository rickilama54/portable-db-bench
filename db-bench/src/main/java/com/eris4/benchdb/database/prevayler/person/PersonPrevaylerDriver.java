package com.eris4.benchdb.database.prevayler.person;

import java.io.IOException;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.prevayler.PrevaylerDatabase;
import com.eris4.benchdb.database.prevayler.PrevaylerSingleton;
import com.eris4.benchdb.test.person.domain.Person;
import com.eris4.benchdb.test.person.domain.PersonDriver;
import com.eris4.benchdb.test.person.domain.PersonUtil;

public class PersonPrevaylerDriver implements PersonDriver {

	private static final String DB_NAME = "/person";
	private String directory = new PrevaylerDatabase().getFileName() + DB_NAME;
	private PrevaylerSingleton prevayler;
	private PersonList persons;
	private PersonUtil personUtil = PersonUtil.getInstance();

	
	public void connect() throws TestDriverException {
		try {			
			prevayler = PrevaylerSingleton.createPrevayler(new PersonList(),directory);
			persons = (PersonList) prevayler.prevalentSystem();			
		} catch (Exception e) {
			throw new TestDriverException(e);
		} 
	}	
	
	
	public void init(int numberOfObject) throws TestDriverException {
		try {				
			prevayler.execute(new InitPersonListTransaction(numberOfObject));
			prevayler.takeSnapshot();
		} catch (Exception e) {
			throw new TestDriverException(e);
		} 
	}

	
	public void close() throws TestDriverException {
		try {
			prevayler.close();
		} catch (IOException e) {
			throw new TestDriverException(e);
		}
	}
	
	
	public Person read(long id) {
		persons = (PersonList) prevayler.prevalentSystem();
		return persons.get(new Long(id));
	}
	
	
	public void write(Person person) {
		PersonPrevayler personPrevayler = new PersonPrevayler();
		personUtil.copy(person, personPrevayler);
		prevayler.execute(new WritePersonTransaction(personPrevayler));
	}

	
	public int getNumberOfPerson() {
		return ((PersonList) prevayler.prevalentSystem()).size();
	}

}
