package com.eris4.benchdb.database.prevayler.person;

import java.io.IOException;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.prevayler.PrevaylerDatabase;
import com.eris4.benchdb.domain.Person;
import com.eris4.benchdb.domain.PersonDriver;
import com.eris4.benchdb.domain.PersonUtil;

public class PersonPrevaylerDriver implements PersonDriver {

	private String directory = new PrevaylerDatabase().getFileName();
	private Prevayler prevayler;
	private PersonList persons;
	private PersonUtil personUtil = PersonUtil.getInstance();

	@Override
	public void connect() throws TestDriverException {
		try {			
			prevayler = PrevaylerFactory.createPrevayler(new PersonList(),directory);
			persons = (PersonList) prevayler.prevalentSystem();			
		} catch (Exception e) {
			throw new TestDriverException(e);
		} 
	}	
	
	@Override
	public void init(int numberOfObject) throws TestDriverException {
		try {				
			prevayler.execute(new InitPersonListTransaction(numberOfObject));
			prevayler.takeSnapshot();
		} catch (Exception e) {
			throw new TestDriverException(e);
		} 
	}

	@Override
	public void close() {
		try {
			prevayler.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Person read(long id) {
		persons = (PersonList) prevayler.prevalentSystem();
		return persons.get(new Long(id));
	}
	
	@Override
	public void write(Person person) {
		PersonPrevayler personPrevayler = new PersonPrevayler();
		personUtil.copy(person, personPrevayler);
		prevayler.execute(new WritePersonTransaction(personPrevayler));
	}

	@Override
	public int getNumberOfPerson() {
		return ((PersonList) prevayler.prevalentSystem()).size();
	}

}
