package com.eris4.benchdb.operation;

import java.util.Random;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.OperationException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.domain.Person;
import com.eris4.benchdb.domain.PersonDriver;

public class ReadPersonOperation extends Operation {

	private PersonDriver personDriver;
	private int numberOfObject;
	private Random random = new Random();;

	@Override
	public void setDatabase(Database database) throws NoSuitableDriverException {
		personDriver = (PersonDriver) database
				.getSpecificDriver(PersonDriver.class);
	}

	@Override
	public void setUp() throws TestDriverException, OperationException {
		personDriver.connect();
		numberOfObject = personDriver.getNumberOfPerson();
		if (numberOfObject == 0)
			throw new OperationException("The number of objects initializated in the database must not be zero");
	}

	@Override
	public void warmUp() throws OperationException, TestDriverException {
		for (int i = 0; i < 10; i++) {
			doOperation();
		}
	}

	@Override
	public void doOperation() throws OperationException, TestDriverException {
		Person person = personDriver.read(random.nextInt(numberOfObject));
		if (person == null) {
			throw new OperationException("Null person in ReadPersonOperation: is the database initialized?");
		}
	}

	@Override
	public void tearDown() throws TestDriverException {
		personDriver.close();	
	}

	/*
	 * ############### private methods here ############
	 */

}
