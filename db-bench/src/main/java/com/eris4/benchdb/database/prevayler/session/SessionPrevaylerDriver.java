package com.eris4.benchdb.database.prevayler.session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.prevayler.PrevaylerDatabase;
import com.eris4.benchdb.database.prevayler.PrevaylerSingleton;
import com.eris4.benchdb.test.session.domain.Session;
import com.eris4.benchdb.test.session.domain.SessionDriver;

public class SessionPrevaylerDriver implements SessionDriver {
	
	private static final String DB_NAME = "/session";
	private PrevaylerSingleton prevayler;
	private Map<Integer,Session> sessionMap;
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
			prevayler = PrevaylerSingleton.createPrevayler(new ConcurrentHashMap<Integer,Session>(), directory);
			sessionMap = (Map<Integer, Session>) prevayler.prevalentSystem();
		} catch (Exception e) {
			throw new TestDriverException(e);
		}
	}

	
	public int getNumberOfSession() throws TestDriverException {
		return sessionMap.size();
	}

	
	public void init(int numberOfObject) throws TestDriverException {
		try {
			prevayler.execute(new InitSessionTransaction(numberOfObject));
			prevayler.takeSnapshot();
		} catch (Exception e) {
			throw new TestDriverException(e);
		}
	}

	
	public Session read(int sessionId) throws TestDriverException {
		sessionMap = (Map<Integer, Session>) prevayler.prevalentSystem();
		return sessionMap.get(new Integer(sessionId));
	}

	
	public void write(Session session) throws TestDriverException {
		SessionPrevayler sessionPrevayler = new SessionPrevayler();
		sessionPrevayler.setSessionId(session.getSessionId());
		sessionPrevayler.setAccountId(session.getAccountId());
		sessionPrevayler.setStartTime(session.getStartTime());
		prevayler.execute(new WriteSessionTransaction(sessionPrevayler));
	}

}
