package com.eris4.benchdb.database.prevayler.msisdn;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.prevayler.Transaction;

import com.eris4.benchdb.test.msisdn.domain.Msisdn;

public class InitMsisdnMapTransaction implements Transaction {

	private int numberOfObject;

	public InitMsisdnMapTransaction(int numberOfObject) {
		this.numberOfObject = numberOfObject;
	}

	@Override
	public void executeOn(Object arg0, Date arg1) {
		Map<Integer,Msisdn> msisdnMap = (Map<Integer, Msisdn>) arg0;
		Random random = new Random();
		for (int i = 0; i < numberOfObject; i++) {
			MsisdnPrevayler msisdn = new MsisdnPrevayler();
			msisdn.setAccountId(random.nextInt());
			msisdn.setMsisdnId(i);
			msisdnMap.put(new Integer(i),msisdn);
		}
	}

}
