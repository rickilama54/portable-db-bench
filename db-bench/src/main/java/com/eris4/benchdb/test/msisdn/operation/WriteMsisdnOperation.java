package com.eris4.benchdb.test.msisdn.operation;

import java.util.Random;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.account.domain.Account;
import com.eris4.benchdb.test.account.domain.AccountDriver;
import com.eris4.benchdb.test.account.domain.AccountImpl;
import com.eris4.benchdb.test.msisdn.domain.Msisdn;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;
import com.eris4.benchdb.test.msisdn.domain.MsisdnImpl;

public class WriteMsisdnOperation extends Operation {

	private Random random = new Random();
	private int id;
	private MsisdnDriver msisdnDriver;
	private int numberOfObject;
	private Logger logger = Logger.getLogger(WriteMsisdnOperation.class);
	
	@Override
	public void setDatabase(Database database) throws NoSuitableDriverException{
		msisdnDriver = (MsisdnDriver) database.getSpecificDriver(MsisdnDriver.class);
		id = 0;

	}
	
	@Override
	public void setUp() throws TestDriverException {
		msisdnDriver.connect();
		numberOfObject = msisdnDriver.getNumberOfMsisdn();		
	}
		
	@Override
	public void warmUp() throws TestDriverException {
		for (int i = 0; i < 10; i++) {
			doOperation();			
		}
	}
	
	@Override
	public void doOperation() throws TestDriverException {
		Msisdn msisdn = new MsisdnImpl();
		msisdn.setMsisdnId(id + numberOfObject);
		msisdn.setAccountId(random.nextInt());
		msisdnDriver.write(msisdn);
		id ++;
	}	

	@Override
	public void tearDown() throws TestDriverException {	
		msisdnDriver.close();	
	}
	
	
	/*
	 *  ###############  private methods here ############
	 */
	

	


}
