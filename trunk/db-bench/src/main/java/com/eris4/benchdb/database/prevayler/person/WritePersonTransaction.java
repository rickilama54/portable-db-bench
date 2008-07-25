package com.eris4.benchdb.database.prevayler.person;

import java.util.Date;

import org.prevayler.Transaction;


public class WritePersonTransaction implements Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 163948172303456084L;
	private PersonPrevayler personPrevayler;

	public WritePersonTransaction(PersonPrevayler personPrevayler) {
		this.personPrevayler = personPrevayler;
	}

	@Override
	public void executeOn(Object arg0, Date arg1) {
		PersonList person = (PersonList) arg0;
		person.add(personPrevayler);
	}

}
