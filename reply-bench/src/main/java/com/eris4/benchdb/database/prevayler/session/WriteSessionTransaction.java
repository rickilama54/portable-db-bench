package com.eris4.benchdb.database.prevayler.session;

import java.util.Date;
import java.util.Map;

import org.prevayler.Transaction;

import com.eris4.benchdb.test.session.domain.Session;

public class WriteSessionTransaction implements Transaction {

	private SessionPrevayler sessionPrevayler;

	public WriteSessionTransaction(SessionPrevayler sessionPrevayler) {
		this.sessionPrevayler = sessionPrevayler;
	}

	
	public void executeOn(Object arg0, Date arg1) {
		Map<Integer,Session> sessionMap = (Map<Integer, Session>) arg0;
		sessionMap.put(new Integer(sessionPrevayler.getSessionId()), sessionPrevayler);
	}

}
