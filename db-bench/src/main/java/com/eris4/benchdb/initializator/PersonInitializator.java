package com.eris4.benchdb.initializator;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.DbInitializator;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.person.PersonDriver;

public class PersonInitializator extends DbInitializator {

	@Override
	public void init(Database database, int numberOfObjects) throws NoSuitableDriverException, TestDriverException {
		PersonDriver personDriver = (PersonDriver) database.getSpecificDriver(PersonDriver.class);
		personDriver.connect();
		personDriver.init(numberOfObjects);		
		personDriver.close();		
	}

	
	
}
