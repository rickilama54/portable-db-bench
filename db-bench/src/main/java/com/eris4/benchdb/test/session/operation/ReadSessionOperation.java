package com.eris4.benchdb.test.session.operation;

import java.util.Random;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.OperationException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.session.domain.Session;
import com.eris4.benchdb.test.session.domain.SessionDriver;

public class ReadSessionOperation extends Operation {

	private Random random = new Random();
	private SessionDriver sessionDriver;
	private int numberOfObject;
	private Logger logger = Logger.getLogger(ReadSessionOperation.class);
	
	
	public void setDatabase(Database database) throws NoSuitableDriverException{
		sessionDriver = (SessionDriver) database.getSpecificDriver(SessionDriver.class);

	}
	
	
	public void setUp() throws TestDriverException, OperationException {
		sessionDriver.connect();
		numberOfObject = sessionDriver.getNumberOfSession();
		if (numberOfObject == 0)
			throw new OperationException("The number of objects initializated in the database must not be zero");
	}
		
	
	public void warmUp() throws TestDriverException, OperationException {
		for (int i = 0; i < 10; i++) {
			doOperation();			
		}
	}
	
	
	public void doOperation() throws TestDriverException, OperationException {
		int randomId = random.nextInt(numberOfObject);
		Session session = sessionDriver.read(randomId);
		if (session== null){
			logger.debug("Session == null for the msisdnId = " + randomId);
			throw new OperationException("Null session in ReadSessionOperation: is the database correctly initialized?");
		}
	}	

	
	public void tearDown() throws TestDriverException {	
		sessionDriver.close();			
	}
	
	
	/*
	 *  ###############  private methods here ############
	 */
	
	


}
