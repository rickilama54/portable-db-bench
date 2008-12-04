package com.eris4.benchdb.test.account.operation;

import java.util.Random;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.OperationException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.account.domain.AccountDriver;

public class StartupAccountOperation extends Operation {
	
	private Random random = new Random();
	private AccountDriver accountDriver;
	private int id;
	private Logger logger = Logger.getLogger(ReadAccountOperation.class);

	
	public void doOperation() throws OperationException, TestDriverException {
		// TODO Auto-generated method stub

	}

	
	public void setDatabase(Database database) throws NoSuitableDriverException {
		accountDriver = (AccountDriver) database.getSpecificDriver(AccountDriver.class);
		id = 0;
	}

	
	public void setUp() throws OperationException, TestDriverException {
		// TODO Auto-generated method stub

	}

	
	public void tearDown() throws OperationException, TestDriverException {
		// TODO Auto-generated method stub

	}

	
	public void warmUp() throws OperationException, TestDriverException {
		// TODO Auto-generated method stub

	}

}
