package com.eris4.benchdb.test.session.operation;

import java.util.Random;

import org.apache.log4j.Logger;

import com.eris4.benchdb.core.Database;
import com.eris4.benchdb.core.NoSuitableDriverException;
import com.eris4.benchdb.core.Operation;
import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.session.domain.Session;
import com.eris4.benchdb.test.session.domain.SessionDriver;
import com.eris4.benchdb.test.session.domain.SessionImpl;

public class WriteSessionOperation extends Operation {

	private Random random = new Random();
	private int id;
	private SessionDriver sessionDriver;
	private int numberOfObject;
	private Logger logger = Logger.getLogger(WriteSessionOperation.class);
	
	@Override
	public void setDatabase(Database database) throws NoSuitableDriverException{
		sessionDriver = (SessionDriver) database.getSpecificDriver(SessionDriver.class);
		id = 0;

	}
	
	@Override
	public void setUp() throws TestDriverException {
		sessionDriver.connect();
		numberOfObject = sessionDriver.getNumberOfSession();		
	}
		
	@Override
	public void warmUp() throws TestDriverException {
		for (int i = 0; i < 10; i++) {
			doOperation();			
		}
	}
	
	@Override
	public void doOperation() throws TestDriverException {
		Session session = new SessionImpl();
		session.setSessionId(id + numberOfObject);
		session.setAccountId(random.nextInt());
		session.setStartTime(System.currentTimeMillis());
		sessionDriver.write(session);
		id ++;
	}	

	@Override
	public void tearDown() throws TestDriverException {	
		sessionDriver.close();	
	}
	
	
	/*
	 *  ###############  private methods here ############
	 */
	

	


}
