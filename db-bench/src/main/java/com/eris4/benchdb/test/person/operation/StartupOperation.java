package com.eris4.benchdb.test.person.operation;

import java.util.Random;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.OperationException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.person.domain.PersonDriver;

public class StartupOperation extends Operation {
	
	private PersonDriver personDriver;
	private int numberOfObject;
	private Random random = new Random();
	private Logger logger = Logger.getLogger(ReadPersonOperation.class);

	@Override
	public void doOperation() throws OperationException, TestDriverException {
		personDriver.connect();
	}

	@Override
	public void setDatabase(Database database) throws NoSuitableDriverException {
		personDriver = (PersonDriver) database.getSpecificDriver(PersonDriver.class);
	}

	@Override
	public void setUp() throws OperationException, TestDriverException {
		// TODO Auto-generated method stub

	}

	@Override
	public void tearDown() throws OperationException, TestDriverException {
		// TODO Auto-generated method stub

	}

	@Override
	public void warmUp() throws OperationException, TestDriverException {
		// TODO Auto-generated method stub

	}

}
