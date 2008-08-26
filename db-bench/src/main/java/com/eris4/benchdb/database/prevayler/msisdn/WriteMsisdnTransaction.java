package com.eris4.benchdb.database.prevayler.msisdn;

import java.util.Date;
import java.util.Map;

import org.prevayler.Transaction;

import com.eris4.benchdb.test.msisdn.domain.Msisdn;

public class WriteMsisdnTransaction implements Transaction {

	private MsisdnPrevayler msisdnPrevayler;

	public WriteMsisdnTransaction(MsisdnPrevayler msisdnPrevayler) {
		this.msisdnPrevayler = msisdnPrevayler;
	}

	@Override
	public void executeOn(Object arg0, Date arg1) {
		Map<Integer,Msisdn> msisdnMap = (Map<Integer, Msisdn>) arg0;
		msisdnMap.put(new Integer(msisdnPrevayler.getMsisdnId()),msisdnPrevayler);
	}

}
