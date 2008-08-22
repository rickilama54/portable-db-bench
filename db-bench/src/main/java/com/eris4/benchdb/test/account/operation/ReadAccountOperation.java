package com.eris4.benchdb.test.account.operation;

import java.util.Random;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.OperationException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.account.domain.Account;
import com.eris4.benchdb.test.account.domain.AccountDriver;

public class ReadAccountOperation extends Operation {

	private Random random = new Random();
	private AccountDriver accountDriver;
	private int numberOfObject;
	private Logger logger = Logger.getLogger(ReadAccountOperation.class);
	
	@Override
	public void setDatabase(Database database) throws NoSuitableDriverException{
		accountDriver = (AccountDriver) database.getSpecificDriver(AccountDriver.class);

	}
	
	@Override
	public void setUp() throws TestDriverException, OperationException {
		accountDriver.connect();
		numberOfObject = accountDriver.getNumberOfAccount();
		if (numberOfObject == 0)
			throw new OperationException("The number of objects initializated in the database must not be zero");
	}
		
	@Override
	public void warmUp() throws TestDriverException, OperationException {
		for (int i = 0; i < 10; i++) {
			doOperation();			
		}
	}
	
	@Override
	public void doOperation() throws TestDriverException, OperationException {
		int randomId = random.nextInt(numberOfObject);
		Account account = accountDriver.read(randomId);
		if (account == null){
			logger.debug("Account == null for the accountId = " + randomId);
			throw new OperationException("Null person in ReadAccountOperation: is the database initialized?");
		}
	}	

	@Override
	public void tearDown() throws TestDriverException {	
		accountDriver.close();			
	}
	
	
	/*
	 *  ###############  private methods here ############
	 */
	
	


}
