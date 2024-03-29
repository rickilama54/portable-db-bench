package com.eris4.benchdb.database.prevayler.msisdn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.prevayler.PrevaylerDatabase;
import com.eris4.benchdb.database.prevayler.PrevaylerSingleton;
import com.eris4.benchdb.test.msisdn.domain.Msisdn;
import com.eris4.benchdb.test.msisdn.domain.MsisdnDriver;

public class MsisdnPrevaylerDriver implements MsisdnDriver {
	
	private PrevaylerSingleton prevayler;
	private static final String DB_NAME = "/msisdn";
	private Map<Integer,Msisdn> msisdnMap;
	private String directory = new PrevaylerDatabase().getFileName() + DB_NAME;

	
	public void close() throws TestDriverException {
		try {
			prevayler.close();
		} catch (IOException e) {
			throw new TestDriverException(e);
		}
	}

	
	public void connect() throws TestDriverException {
		try {
			prevayler = PrevaylerSingleton.createPrevayler(new ConcurrentHashMap<Integer,Msisdn>(),directory);
			msisdnMap = (Map<Integer, Msisdn>) prevayler.prevalentSystem();
		} catch (Exception e) {
			throw new TestDriverException(e);
		}
	}

	
	public int getNumberOfMsisdn() throws TestDriverException {
		return msisdnMap.size();
	}

	
	public void init(int numberOfObject) throws TestDriverException {
		try {
			prevayler.execute(new InitMsisdnMapTransaction(numberOfObject));
			prevayler.takeSnapshot();
		} catch (Exception e) {
			throw new TestDriverException(e);
		}
	}

	
	public Msisdn read(int msisdnId) throws TestDriverException {
		msisdnMap = (Map<Integer, Msisdn>) prevayler.prevalentSystem();
		return msisdnMap.get(new Integer(msisdnId));
	}

	
	public void write(Msisdn msisdn) throws TestDriverException {
		MsisdnPrevayler msisdnPrevayler = new MsisdnPrevayler();
		msisdnPrevayler.setAccountId(msisdn.getAccountId());
		msisdnPrevayler.setMsisdnId(msisdn.getMsisdnId());
		prevayler.execute(new WriteMsisdnTransaction(msisdnPrevayler));
	}

}
