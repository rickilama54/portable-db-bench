package com.eris4.benchdb.database.prevayler.person;

import java.util.Date;

import org.prevayler.Transaction;

import com.eris4.benchdb.domain.PersonUtil;

public class InitPersonListTransaction implements Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5034357941274285908L;
	private int numberOfObject;

	public InitPersonListTransaction(int numberOfObject) {
		this.numberOfObject = numberOfObject;
	}

	@Override
	public void executeOn(Object arg0, Date arg1) {
		PersonList persons = (PersonList) arg0;
		PersonUtil personUtil = PersonUtil.getInstance();
		for (int i = 0; i < numberOfObject; i++) {
			PersonPrevayler person = new PersonPrevayler();
			personUtil.newRandomPerson(person);
			person.setId(i);
			persons.add(person);
		}
	}

}
