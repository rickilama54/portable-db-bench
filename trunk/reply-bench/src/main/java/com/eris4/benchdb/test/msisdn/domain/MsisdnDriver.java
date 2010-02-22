package com.eris4.benchdb.test.msisdn.domain;

import com.eris4.benchdb.core.TestDriverException;

public interface MsisdnDriver {
	
	public void connect() throws TestDriverException;
	
	public void init(int numberOfObject) throws TestDriverException;
	//getNumberOfObjects if you want to generalize this 4 methods in a base interface
	public int getNumberOfMsisdn() throws TestDriverException;
	
	public void close() throws TestDriverException;
	
	
	
	
	public Msisdn read(int msisdnId) throws TestDriverException;
	
	public void write(Msisdn msisdn) throws TestDriverException;

}
