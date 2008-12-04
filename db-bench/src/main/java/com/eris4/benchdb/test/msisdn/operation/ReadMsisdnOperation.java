package com.eris4.benchdb.test.msisdn.operation;

import java.util.Random;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.OperationException;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.msisdn.domain.Msisdn;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;

public class ReadMsisdnOperation extends Operation {

	private Random random = new Random();
	private MsisdnDriver msisdnDriver;
	private int numberOfObject;
	private Logger logger = Logger.getLogger(ReadMsisdnOperation.class);
	
	
	public void setDatabase(Database database) throws NoSuitableDriverException{
		msisdnDriver = (MsisdnDriver) database.getSpecificDriver(MsisdnDriver.class);

	}
	
	
	public void setUp() throws TestDriverException, OperationException {
		msisdnDriver.connect();
		numberOfObject = msisdnDriver.getNumberOfMsisdn();
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
		Msisdn msisdn = msisdnDriver.read(randomId);
		if (msisdn == null){
			logger.debug("Msisdn == null for the msisdnId = " + randomId);
			throw new OperationException("Null msisdn in ReadAccountOperation: is the database correctly initialized?");
		}
	}	

	
	public void tearDown() throws TestDriverException {	
		msisdnDriver.close();			
	}
	
	
	/*
	 *  ###############  private methods here ############
	 */
	
	


}
