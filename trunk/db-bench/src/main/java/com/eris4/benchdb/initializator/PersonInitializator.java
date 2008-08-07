package com.eris4.benchdb.initializator;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.DbInitializator;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.domain.PersonDriver;

public class PersonInitializator extends DbInitializator {
	
	private Logger logger = Logger.getLogger(DbInitializator.class);

	@Override
	public void init(Database database, int numberOfObjects) throws NoSuitableDriverException, TestDriverException {
		PersonDriver personDriver = (PersonDriver) database.getSpecificDriver(PersonDriver.class);
		logger.debug("specific driver acquired");
		personDriver.connect();
		logger.debug("connected to the database");
		personDriver.init(numberOfObjects);
		logger.debug("init done of number of objects = "+numberOfObjects);
		personDriver.close();	
		logger.debug("closed the connection");
	}
	
	@Override
	public String getDescription() {
		return "Number of Person initialized: " + getNumberOfObjects();
	}

	
	
}
