package com.eris4.benchdb.database.pico4.session;

import java.util.Random;

import com.eris4.benchdb.core.TestDriverException;
import com.eris4.benchdb.database.pico4.PersistentMapSingleton;
import com.eris4.benchdb.test.session.domain.Session;
import com.eris4.benchdb.test.session.domain.SessionDriver;

public class SessionPico4Driver implements SessionDriver {
	
	private String mapName = "SessionPico4";
	private PersistentMapSingleton map;
	private Random random = new Random();

	
	public void close() throws TestDriverException {
		map.close();
	}

	
	public void connect() throws TestDriverException {
		map = PersistentMapSingleton.getInstance(mapName);
		map.load();
	}

	
	public int getNumberOfSession() throws TestDriverException {
		return map.size();
	}

	
	public void init(int numberOfObject) throws TestDriverException {
		for (int i = 0; i < numberOfObject; i++) {
			SessionPico4Impl session = new SessionPico4Impl();
			session.setSessionId(i);
			session.setAccountId(random.nextInt());
			session.setStartTime(System.currentTimeMillis());
			map.put(String.valueOf(i), session);
		}
	}

	
	public Session read(int sessionId) throws TestDriverException {
		return (Session) map.get(String.valueOf(sessionId));
	}

	
	public void write(Session session) throws TestDriverException {
		SessionPico4Impl sessionPico4 = new SessionPico4Impl();
		sessionPico4.setAccountId(session.getAccountId());
		sessionPico4.setSessionId(session.getSessionId());
		sessionPico4.setStartTime(session.getStartTime());
		map.put(String.valueOf(sessionPico4.getSessionId()), sessionPico4);
	}

}
