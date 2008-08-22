package com.eris4.benchdb.test.account.operation;

import java.util.Random;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.account.domain.Account;
import com.eris4.benchdb.test.account.domain.AccountDriver;
import com.eris4.benchdb.test.account.domain.AccountImpl;

public class WriteAccountOperation extends Operation {

	private Random random = new Random();
	private int id;
	private AccountDriver accountDriver;
	private int numberOfObject;
	private Logger logger = Logger.getLogger(WriteAccountOperation.class);
	
	@Override
	public void setDatabase(Database database) throws NoSuitableDriverException{
		accountDriver = (AccountDriver) database.getSpecificDriver(AccountDriver.class);
		id = 0;

	}
	
	@Override
	public void setUp() throws TestDriverException {
		accountDriver.connect();
		numberOfObject = accountDriver.getNumberOfAccount();		
	}
		
	@Override
	public void warmUp() throws TestDriverException {
		for (int i = 0; i < 10; i++) {
			doOperation();			
		}
	}
	
	@Override
	public void doOperation() throws TestDriverException {
		Account account = newRandomAccount();		
		account.setAccountId(id + numberOfObject);	
		accountDriver.write(account);
		id ++;
	}	

	@Override
	public void tearDown() throws TestDriverException {	
		accountDriver.close();			
	}
	
	
	/*
	 *  ###############  private methods here ############
	 */
	
	private Account newRandomAccount() {
		Account account = new AccountImpl();
		account.setBalance(random.nextInt());
		return account;
	}

	


}
