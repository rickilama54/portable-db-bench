package com.eris4.benchdb.test.session.domain;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.test.account.domain.Account;

public interface SessionDriver {
	
public void connect() throws TestDriverException;
	
	public void init(int numberOfObject) throws TestDriverException;
	//getNumberOfObjects if you want to generalize this 4 methods in a base interface
	public int getNumberOfSession() throws TestDriverException;
	
	public void close() throws TestDriverException;
	
	
	
	
	public Session read(long accountId) throws TestDriverException;
	
	public void write(Session session) throws TestDriverException;

}
