package com.eris4.benchdb.database.prevayler.session;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.prevayler.Transaction;

import com.eris4.benchdb.test.session.domain.Session;

public class InitSessionTransaction implements Transaction {

	private int numberOfObject;

	public InitSessionTransaction(int numberOfObject) {
		this.numberOfObject = numberOfObject;
	}

	
	public void executeOn(Object arg0, Date arg1) {
		Map<Integer,Session> sessionMap = (Map<Integer,Session>) arg0;
		Random random = new Random();
		for (int i = 0; i < numberOfObject; i++) {
			SessionPrevayler session = new SessionPrevayler();	
			session.setAccountId(random.nextInt());
			session.setSessionId(i);
			session.setStartTime(System.currentTimeMillis());
			sessionMap.put(new Integer(i),session);
		}
	}

}
