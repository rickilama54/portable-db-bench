package com.eris4.benchdb.test.account.initializator;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.DbInitializator;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.account.domain.AccountDriver;

public class AccountInitializator extends DbInitializator {
	
	private Logger logger = Logger.getLogger(DbInitializator.class);

	
	public void init(Database database, int numberOfObjects) throws NoSuitableDriverException, TestDriverException {
		AccountDriver accountDriver = (AccountDriver) database.getSpecificDriver(AccountDriver.class);
		logger.debug("account specific driver acquired");
		accountDriver.connect();
		logger.debug("connected to the database");
		accountDriver.init(numberOfObjects);
		logger.debug("init done of number of objects = "+numberOfObjects);
		accountDriver.close();	
		logger.debug("closed the connection");
	}
	
	
	public String getDescription() {
		return "Number of Account initialized: " + getNumberOfObjects();
	}

	
	
}
