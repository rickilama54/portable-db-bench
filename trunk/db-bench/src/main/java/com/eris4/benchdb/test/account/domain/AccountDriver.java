package com.eris4.benchdb.test.account.domain;

import com.eris4.benchdb.core.TestDriverException;

public interface AccountDriver {
	
	public void connect() throws TestDriverException;
	
	public void init(int numberOfObject) throws TestDriverException;
	//getNumberOfObjects if you want to generalize this 4 methods in a base interface
	public int getNumberOfAccount() throws TestDriverException;
	
	public void close() throws TestDriverException;
	
	
	
	
	public Account read(int accountId) throws TestDriverException;
	
	public void write(Account account) throws TestDriverException;

}
