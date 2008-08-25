package com.eris4.benchdb.test.session.initializator;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.DbInitializator;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.session.domain.SessionDriver;

public class SessionInitializator extends DbInitializator {
	
	private Logger logger = Logger.getLogger(DbInitializator.class);

	@Override
	public void init(Database database, int numberOfObjects) throws NoSuitableDriverException, TestDriverException {
		SessionDriver msisdnDriver = (SessionDriver) database.getSpecificDriver(SessionDriver.class);
		logger.debug("specific driver acquired");
		msisdnDriver.connect();
		logger.debug("connected to the database");
		msisdnDriver.init(numberOfObjects);
		logger.debug("init done of number of objects = "+numberOfObjects);
		msisdnDriver.close();	
		logger.debug("closed the connection");
	}
	
	@Override
	public String getDescription() {
		return "Number of Session initialized: " + getNumberOfObjects();
	}

	
	
}
